package com.example.assignment_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    private EditText editUser, editPass;
    private Button RegBtn;
    private DBHandler dbHandler;
    private ConstraintLayout layout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUser = (EditText) findViewById(R.id.userName);
        editPass = (EditText) findViewById(R.id.passWord);
        RegBtn = (Button) findViewById(R.id.BtnReg);
        layout = (ConstraintLayout) findViewById(R.id.reg_layout);
        textView = (TextView) findViewById(R.id.tvLog);

        dbHandler = new DBHandler(this);

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editUser.getText().toString();
                String passWord = editPass.getText().toString();

                if (validate()){
                    if (!dbHandler.isUsernameExist(userName)){
                        dbHandler.addUser(userName, passWord);
                        Snackbar.make(layout, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Snackbar.make(layout, "User already exists ", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }

    private boolean validate() {
        boolean valid = false;
        String userName = editUser.getText().toString();
        String passWord = editPass.getText().toString();

        if (userName.isEmpty() || userName.length() < 5){
            Toast.makeText(getApplicationContext(), "username is too short", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else{
            valid = true;
        }
        if (passWord.isEmpty() || passWord.length() < 5){
            Toast.makeText(getApplicationContext(),"password is too short",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
}