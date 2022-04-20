package com.example.assignment_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_login.Model.LoginModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private TextView textView;
    private EditText userEdit, passEdit;
    private Button logBtn;
    private DBHandler dbHandler;
    LoginModel loginModel;
    ImageView google;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    LoginButton btnfb;
    CallbackManager callbackManager;
    Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEdit = (EditText) findViewById(R.id.editUser);
        passEdit = (EditText) findViewById(R.id.editPassword);
        logBtn = (Button) findViewById(R.id.BtnLog);
        textView = (TextView) findViewById(R.id.tvReg);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.log_layout);

        dbHandler = new DBHandler(this);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = userEdit.getText().toString();
                String pass = passEdit.getText().toString();
                loginModel = dbHandler.authenticate(new LoginModel(uname,pass));
                if (loginModel != null){
                    Snackbar.make(constraintLayout,"Login Successful",Snackbar.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this,HomePage.class);
                    startActivity(intent);
                }
                else {
                    Snackbar.make(constraintLayout,"Login Failed",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        google = (ImageView) findViewById(R.id.googleBtn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account =GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            navigateToHomePage();
        }

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        loginfb();
    }

    private void loginfb() {
            btnfb = findViewById(R.id.login_button);
            FacebookSdk.sdkInitialize(getApplicationContext());
            application = getApplication();
            AppEventsLogger.activateApp(application);
            callbackManager = CallbackManager.Factory.create();


            btnfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));

                }
            });

            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            navigateToHomePage();
                        }

                        @Override
                        public void onCancel() {
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            Toast.makeText(LoginActivity.this, "errorrr", Toast.LENGTH_SHORT).show();
                        }
                    });


        }


    private void SignIn() {
        Intent signinIntent = gsc.getSignInIntent();
        startActivityForResult(signinIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToHomePage();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToHomePage() {
        finish();
        Intent intent = new Intent(LoginActivity.this, HomePage.class);
        startActivity(intent);
    }
}