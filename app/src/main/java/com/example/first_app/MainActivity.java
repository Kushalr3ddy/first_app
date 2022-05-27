package com.example.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String verificationid;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationinprogress =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        TextView phoneno = (TextView) findViewById(R.id.phoneno);
        TextView otp = (TextView) findViewById(R.id.otp);
        Button loginbtn = (Button) findViewById(R.id.loginbtn);
        Button otpbtn = (Button) findViewById(R.id.otpbtn);

        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!verificationinprogress){
                   if(phoneno.getText().toString().length() ==10){
                       String phonenumber = "+91"+phoneno.getText().toString();
                       Toast.makeText(MainActivity.this,"sending otp",Toast.LENGTH_SHORT).show();
                       requestOTP(phonenumber);


                   }else{
                       phoneno.setError("Invalid Phone no");
                   }

               }else{
                   String userOTP = otp.getText().toString();
                   if(!userOTP.isEmpty()&&userOTP.length() ==6){
                       PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid,userOTP);
                       verifyAuth(credential);
                   }else{
                       otp.setError("Invalid OTP");
                   }
               }
            }
        });

    }
    @Override
    protected void onStart() {

        super.onStart();
        if(fauth.getCurrentUser() !=null){
            Toast.makeText(MainActivity.this,"already logged in",Toast.LENGTH_SHORT).show();
            checkuserprofile();
        }
    }

    private void verifyAuth(PhoneAuthCredential credential){
        fauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(MainActivity.this,"otp success",Toast.LENGTH_SHORT).show();
                    checkuserprofile();
                }else{Toast.makeText(MainActivity.this,"verification failed",Toast.LENGTH_SHORT).show();}
            }
        });
    }

    private void checkuserprofile(){
        DocumentReference docref = fstore.collection("users").document(fauth.getCurrentUser().getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    startActivity(new Intent(getApplicationContext(),activity2.class));
                    finish();
                }else{
                    startActivity(new Intent(getApplicationContext(),register.class));
                    finish();
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
                Button otpbtn = (Button) findViewById(R.id.otpbtn);
                otpbtn.setText("Verify");
                verificationinprogress = true;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(MainActivity.this,"otp auth failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}