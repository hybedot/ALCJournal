package com.androidrefugee.journal;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextInputLayout mEmailEditTextLayout;
    private TextInputLayout mPasswordEditTextLayoutOne;
    private TextInputLayout mPasswordEditTextLayoutTwo;

    private EditText mEmailEditText;
    private EditText mPasswordEditTextOne;
    private EditText mPasswordEditTextTwo;

    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        mEmailEditText = (EditText) findViewById(R.id.edt_sign_up_email);
        mPasswordEditTextOne = (EditText) findViewById(R.id.edt_sign_up_password_one);
        mPasswordEditTextTwo = (EditText) findViewById(R.id.edt_sign_up_password_two);


        mSignUpButton = (Button) findViewById(R.id.btn_sign_up);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
            }
        });
    }

    private void registerUser(){
        String email = mEmailEditText.getText().toString().trim().toLowerCase();
        String passwordOne = mPasswordEditTextOne.getText().toString().trim();
        String passwordTwo = mPasswordEditTextTwo.getText().toString().trim();

        Log.d("validate",passwordOne);
        Log.d("validate",passwordTwo);

        if (validateField(email,mEmailEditText) && validateEmail(email) &&
                validateField(passwordOne, mPasswordEditTextOne) &&
                        validateField(passwordTwo, mPasswordEditTextTwo)
                        && matchPassword(passwordOne, passwordTwo)) {


            mAuth.createUserWithEmailAndPassword(email,passwordTwo).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getBaseContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getBaseContext(),"You are already registered",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getBaseContext(),"Registration not failed",Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }
            );
        }else return;
    }




    private boolean validateEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmailEditText.requestFocus();
            mEmailEditText.setError(getString(R.string.email_not_correct));
            Log.d("validate","email not correct");
            return false;
        }
        Log.d("validate","email correct");
        return true;
    }

    private boolean validateField(String field, EditText editText) {
        if (!field.isEmpty()) {
            Log.d("validate","field not empty");
            return true;
        }
        editText.requestFocus();   //   set   focus   on   fields
        editText.setError(getString(R.string.please_fill_this));   //   set   error   message
        Log.d("validate","field empty");
        return false;
    }

    private boolean matchPassword(String passwordOne, String passwordTwo){
        if (!(passwordOne.contentEquals(passwordTwo))){
           mPasswordEditTextTwo.requestFocus();
           mPasswordEditTextTwo.setError(getString(R.string.password_not_matched));
            Log.d("validate","password  not matched");
           return false;
        }
            Log.d("validate","password matched");
            return true;
    }

}
