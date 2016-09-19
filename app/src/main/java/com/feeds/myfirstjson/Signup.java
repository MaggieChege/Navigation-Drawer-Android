package com.feeds.myfirstjson;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Signup extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button btnSignUp, btnResetPassword, btnSignIn;
    private ProgressBar progressBar;
    private EditText inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, ResetActivity.class));

            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Closing an Activity while in another activity
                Intent w = new Intent(Signup.this, LoginActivity.class);
                startActivity(w);


            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Address", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Your Password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() < 5) {
                    Toast.makeText(getApplicationContext(), "YOur Passord is Too Short", Toast.LENGTH_LONG).show();
                    return;

                }
                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
//                                    Toast.makeText(Signup.this, "Authentication failed." + task.getException(),
//                                            Toast.LENGTH_SHORT).show();
                                    Log.e("ERROR", String.valueOf(task.getException()));
                                } else {
                                    startActivity(new Intent(Signup.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }


        });


    }

    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
