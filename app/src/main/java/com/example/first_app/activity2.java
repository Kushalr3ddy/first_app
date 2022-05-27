package com.example.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity2 extends AppCompatActivity {
    FirebaseAuth firebaseauth;
    FirebaseFirestore fstore;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Button self_asses = (Button)findViewById(R.id.button1);
        Button vaccinate = (Button)findViewById(R.id.button2);
        TextView terms = (TextView)findViewById(R.id.terms);
        TextView callbtn = (TextView) findViewById(R.id.btn);

        self_asses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(activity2.this,self_assessment.class);
                startActivity(next);
            }
        });
        vaccinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://selfregistration.cowin.gov.in/"));
                startActivity(browserIntent);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent show_terms = new Intent(activity2.this,terms_page.class);
                startActivity(show_terms);

            }
        });
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent location = new Intent(activity2.this,testing.class);
                startActivity(location);
            }
        });

    }




}