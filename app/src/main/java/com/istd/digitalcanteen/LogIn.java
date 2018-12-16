package com.istd.digitalcanteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//the home activity for the APP
public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button buttonStudentLogin = findViewById(R.id.buttonlogin_student);
        Button buttonStallLogin = findViewById(R.id.buttonlogin_stall);
        buttonStallLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, MainActivity.class);
                startActivity(intent);
            }
        });
        buttonStudentLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, StudentMain.class);
                startActivity(intent);
            }
        });
    }
}
