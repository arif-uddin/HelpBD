package com.diu.helpbd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.diu.helpbd.Model.ModelUser;
import com.diu.helpbd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextView createAccount,forgetPassword;
    EditText loginEmail, loginPassword;
    Button btnLogin;
    ProgressBar progressBarLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth=FirebaseAuth.getInstance();


        createAccount=findViewById(R.id.createAccount);
        forgetPassword=findViewById(R.id.forgetPassword);
        btnLogin=findViewById(R.id.btnLogin);
        progressBarLogin=findViewById(R.id.progressBar);
        loginEmail=findViewById(R.id.loginEmail);
        loginPassword=findViewById(R.id.loginPassword);

        createAccount.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createAccount:
                Intent intent =new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.forgetPassword:
                Intent intent1 =new Intent(LoginActivity.this,PasswordResetActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnLogin:
                login();
                break;
        }
    }

    private void login() {
        String emailLogin=loginEmail.getText().toString().trim();
        String passwordLogin=loginPassword.getText().toString().trim();

        if(emailLogin.isEmpty()) {
            loginEmail.setError("E-mail is required");
            loginEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailLogin).matches()) {
            loginEmail.setError("Please enter valid email");
            loginEmail.requestFocus();
            return;
        }

        if(passwordLogin.isEmpty()) {
            loginPassword.setError("Password is required");
            loginPassword.requestFocus();
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null)
                            {   progressBarLogin.setVisibility(View.GONE);
                                Query query = FirebaseDatabase.getInstance().getReference().child("users");
                                query.orderByChild("id").equalTo(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        ModelUser modelUser=dataSnapshot.getValue(ModelUser.class);
                                        if (modelUser.getType().contains("user")){

                                            Toast.makeText(LoginActivity.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            return;

                                        }

                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                            //updateUI(user);
                        } else {
                            progressBarLogin.setVisibility(View.GONE);
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed!",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }
}