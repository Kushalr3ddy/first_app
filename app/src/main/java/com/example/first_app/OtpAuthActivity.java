package com.example.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OtpAuthActivity extends AppCompatActivity {
    FirebaseAuth fauth;
    String verificationid;
    PhoneAuthProvider.ForceResendingToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_auth);

        fauth = FirebaseAuth.getInstance();
        //fauth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        TextView top = findViewById(R.id.signin);
        TextView otp=(TextView) findViewById(R.id.otp);
        Button loginbtn = (Button)findViewById(R.id.loginbtn);

        Intent otpintent = getIntent();
        String phoneno = otpintent.getStringExtra("otp");
        top.setText("otp has been sent to: "+phoneno);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneno.toString().isEmpty() && phoneno.toString().length() ==10){
                    String phonenumber = "+91"+phoneno.toString();
                    requestOTP(phonenumber);

                }else{
                   // phoneno.setError("Invalid Phone no");
                }
            }
        });
    }
    private void requestOTP(String phno){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phno, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationid = s;
                token = forceResendingToken;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OtpAuthActivity.this,"otp auth failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}