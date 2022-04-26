package com.example.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

//extra imports
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //
        TextView username=(TextView) findViewById(R.id.username);
        TextView password=(TextView) findViewById(R.id.password);
        Button loginbtn = (Button)findViewById(R.id.loginbtn);
        //default credentials
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OtpAuthActivity.class);
                String phone_no =username.getText().toString();
                if(password.getText().toString().equals("12345")){
                    Toast.makeText(MainActivity.this,"Login Sucessful",Toast.LENGTH_SHORT).show();

/////////////////////////////////
                    try {
                        URL url = new URL("https://cdn-api.co-vin.in/api/v2/auth/public/generateOTP");
                        BufferedReader reader=null;
                        String text ="";
                        // Send POST data request
                        URLConnection conn = url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestProperty("accept", "application/json");
                        conn.setRequestProperty("Content-Type", "application/json");
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                        wr.write( "{\"mobile\":\""+phone_no+"\"}" );
                        wr.flush();

                        // Get the server response

                        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;

                        // Read Server Response
                        while((line = reader.readLine()) != null)
                        {
                            // Append server response in string
                            sb.append(line + "\n");
                        }


                        text = sb.toString();

                        reader.close();
                    } catch (Exception e) {

                    }


//////////////////////////////////
                    intent.putExtra("otp", phone_no);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}