package com.example.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    DatePickerDialog datepickerdialog;

    FirebaseAuth firebaseauth;
    FirebaseFirestore fstore;
    EditText Phoneno,codeenter;
    Button Nextbtn;
    //ProgressBar progressBar;
    String uid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Button login
        firebaseauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        uid = firebaseauth.getCurrentUser().getUid();
        DocumentReference docref = fstore.collection("users").document(uid);
        DocumentReference docref2 = fstore.collection("location").document(uid);

        EditText f_name = findViewById(R.id.firstname);
        EditText l_name = findViewById(R.id.lastname);
        Button registerbtn = findViewById(R.id.loginbtn);

        datepick();
        Button datebutton =findViewById(R.id.dob);
        Button sexbtn = findViewById(R.id.sex);
        Button dob = findViewById(R.id.dob);
        dob.setText(getTodaysDate());
        datepickerdialog.hide();
        sexbtn.setVisibility(View.GONE);
        datebutton.setVisibility(View.GONE);


        //functions for the buttons


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f_name.getText().toString().isEmpty() ){
                    f_name.setError("Required field");

                }
                if(l_name.getText().toString().isEmpty() ){
                    l_name.setError("Required field");

                }

                if(!f_name.getText().toString().isEmpty() && !f_name.getText().toString().isEmpty() ){
                    String firstname = f_name.getText().toString();
                    String lastname = l_name.getText().toString();

                    Map<String,Object> user = new HashMap<>();
                    user.put("firstname",firstname);
                    user.put("lastname",lastname);
                    docref.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),activity2.class));
                                finish();
                            }else{
                                Toast.makeText(register.this,"registration failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void datepick() {
        DatePickerDialog.OnDateSetListener datesetlistener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day,month,year);
                Button datebutton =findViewById(R.id.dob);
                datebutton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datepickerdialog = new DatePickerDialog(this,style,datesetlistener,year,month,day);
        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
    private String makeDateString(int day,int month,int year){
        return day+" "+getmonth(month)+" "+year;
    }

    private String getmonth(int month) {
        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MAR";
        if(month==4)
            return "APR";
        if(month==5)
            return "MAY";
        if(month==6)
            return "JUN";
        if(month==7)
            return "JUL";
        if(month==8)
            return "AUG";
        if(month==9)
            return "SEP";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEC";
        return "JAN";

    }

    public void openDatePicker(View view)
    {
        datepickerdialog.show();
    }
}