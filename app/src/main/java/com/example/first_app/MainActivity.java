package com.example.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //
        TextView username=(TextView) findViewById(R.id.username);
        TextView password=(TextView) findViewById(R.id.password);
        Button loginbtn = (Button)findViewById(R.id.loginbtn);
        /*TextView forgotpass = (TextView)findViewById(R.id.forgotpass);

        //click listener for new user button
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(MainActivity.this, register.class);
                startActivity(register);
            }
        });*/
        TextView register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,register.class);
                startActivity(intent);
            }
        });

        // click listener for sign in button
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OtpAuthActivity.class);
                String phone_no =username.getText().toString();
                if(phone_no.length()!=10)
                    Toast.makeText(MainActivity.this,"phone number should be 10 digits",Toast.LENGTH_SHORT).show();
                if(password.getText().toString().equals("12345")){


/////////////////////////////////
                    OkHttpClient client = new OkHttpClient();
                    String otp ="5599";
                    String url = "https://www.fast2sms.com/dev/bulkV2?authorization=graoNcAiJm1MKh0EfLWuI9kpRbnTQ3XGl6ztCOBYHqDZdSeFvUSMAnFX6fLl4okKEm8R7UeH1rT50stO&variables_values="+otp+"&route=otp&numbers="+phone_no;
                    Request request = new Request.Builder().url(url).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String myresponse = response.body().string();
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        intent.putExtra("otp", phone_no);
                                        intent.putExtra("otp1",otp);

                                        startActivity(intent);
                                        //forgotpass.setText(myresponse);
                                        //Toast.makeText(MainActivity.this,myresponse,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });

                    Toast.makeText(MainActivity.this,"Login Sucessful",Toast.LENGTH_SHORT).show();
                    //forgotpass.setText("hello");
//////////////////////////////////

                }else{
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}