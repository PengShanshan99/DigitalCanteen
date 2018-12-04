package com.istd.digitalcanteen;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

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
