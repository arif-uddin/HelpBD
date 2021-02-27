package com.diu.helpbd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.diu.helpbd.Model.ModelUser;
import com.diu.helpbd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail,etName,etPassword,etContactNo,etNIDno;
    private FirebaseAuth mAuth;

    private Button signUp;
    private ProgressBar progressBar;
    private SearchableSpinner districtSpinner, upazilaSpinner, unionSpinner;
    private String district,upazila,union;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth=FirebaseAuth.getInstance();

        etNIDno=findViewById(R.id.nidno);
        etEmail=findViewById(R.id.email);
        etName=findViewById(R.id.name);
        etPassword=findViewById(R.id.password);
        signUp=findViewById(R.id.btnSignUp);
        etContactNo=findViewById(R.id.contactNo);
        progressBar=findViewById(R.id.progressBar);

        districtSpinner=findViewById(R.id.districtSpinner);
        upazilaSpinner=findViewById(R.id.upazilaSpinner);
        unionSpinner=findViewById(R.id.unionSpinner);


        List<String> districtList= Arrays.asList(getResources().getStringArray(R.array.districtList));
        districtSpinner.setAdapter(new ArrayAdapter<>(SignUpActivity.this,android.R.layout.simple_spinner_dropdown_item,districtList));
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> upazilaList= Arrays.asList(getResources().getStringArray(R.array.upazilaList));
        upazilaSpinner.setAdapter(new ArrayAdapter<>(SignUpActivity.this,android.R.layout.simple_spinner_dropdown_item,upazilaList));
        upazilaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                upazila=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> unionList= Arrays.asList(getResources().getStringArray(R.array.unionList));
        unionSpinner.setAdapter(new ArrayAdapter<>(SignUpActivity.this,android.R.layout.simple_spinner_dropdown_item,unionList));
        unionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                union=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                registerUser();
                break;

             }
         }
    private void registerUser() {

        final String Email=etEmail.getText().toString().trim();
        final String Password=etPassword.getText().toString().trim();
        final String FullName=etName.getText().toString().trim();
        final String ContactNo=etContactNo.getText().toString().trim();
        final String NIDno=etNIDno.getText().toString().trim();

        if(FullName.isEmpty()) {
            etName.setError("Full name is required");
            etName.requestFocus();
            return;
        }

        if(ContactNo.isEmpty()) {
            etContactNo.setError("E-mail is required");
            etContactNo.requestFocus();
            return;
        }

        if(NIDno.isEmpty()) {
            etNIDno.setError("NID no. is required");
            etNIDno.requestFocus();
            return;
        }

        if(Email.isEmpty()) {
            etEmail.setError("E-mail is required");
            etEmail.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            etEmail.setError("Please enter valid email");
            etEmail.requestFocus();
            return;
        }


        if(Password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if(Password.length()<6){
            etPassword.setError("Password must be at least 6 characters!");
            etPassword.requestFocus();
            return;
        }

        if (district.contains("Select District")){
            Toast.makeText(SignUpActivity.this,"Please select District",Toast.LENGTH_SHORT).show();
            return;
        }

        if (upazila.contains("Select Upazila")){
            Toast.makeText(SignUpActivity.this,"Please select Upazila",Toast.LENGTH_SHORT).show();
            return;
        }

        if (union.contains("Select Union")){
            Toast.makeText(SignUpActivity.this,"Please select Union",Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            ModelUser user= new ModelUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),FullName,Email,ContactNo,NIDno,"default","user",district,upazila,union);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this,"User has been registered!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this,"Failed to register!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }

                        else {
                            Toast.makeText(SignUpActivity.this,"Failed to register!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }
}