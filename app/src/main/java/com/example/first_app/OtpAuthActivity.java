package com.example.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class OtpAuthActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_auth);
        TextView top = findViewById(R.id.signin);
        TextView username=(TextView) findViewById(R.id.otp);
        Button loginbtn = (Button)findViewById(R.id.loginbtn);

        Intent otpintent = getIntent();
        String message = otpintent.getStringExtra("otp");
        top.setText("otp has been sent to: "+message);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpAuthActivity.this,activity2.class);
                String otp1 =username.getText().toString();
            }
        });
    }
}