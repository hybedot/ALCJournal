package com.androidrefugee.journal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    public static final  String TAG = SignIn.class.getSimpleName();

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmailEditText = (EditText)findViewById(R.id.edt_sign_in_email);
        mPasswordEditText = (EditText)findViewById(R.id.edt_sign_in_password);

        mAuth = FirebaseAuth.getInstance();
    }

    public void signInWithEmail(View view){
        String email = mEmailEditText.getText().toString().trim().toLowerCase();
        String password = mPasswordEditText.getText().toString().trim();

        if (!validateUserInput(email,password)){
            return;
        }
        //Toast.makeText(this,"sign in",Toast.LENGTH_SHORT).show();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignIn.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            finish();
                            Intent intent = new Intent(SignIn.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    public void signInWithGoogle(View view){


    }

    public void openSignUpPage(View view){
        finish();
        Intent i = new Intent(this,SignUp.class);
        startActivity(i);

    }

    private  boolean validateUserInput (String email, String password){

        if (email.isEmpty()){
            mEmailEditText.setError("Email required");
            return false;
        }

        if (password.isEmpty()){
            mPasswordEditText.setError("Password required");
            return false;
        }
        return  true;
    }


}
