package com.diu.helpbd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diu.helpbd.Model.ModelApplication;
import com.diu.helpbd.Model.ModelUser;
import com.diu.helpbd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Menu menu;
    private Button confirm,apply,back;
    private EditText name,fatherName,motherName,birthDate,balance;
    private CheckBox accountYes,accountNo;
    private String account="none",district,upazila,union,nIDno;
    private CardView cardView;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SplashActivity.splash.finish();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Application and Status");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        confirm=findViewById(R.id.confirm);
        name=findViewById(R.id.name);
        fatherName=findViewById(R.id.fatherName);
        motherName=findViewById(R.id.motherName);
        birthDate=findViewById(R.id.birthDate);
        balance=findViewById(R.id.balance);
        accountYes=findViewById(R.id.accountYes);
        accountNo=findViewById(R.id.accountNo);
        apply=findViewById(R.id.apply);
        cardView=findViewById(R.id.cardView);
        back=findViewById(R.id.back);
        statusText=findViewById(R.id.statusText);


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
                apply.setVisibility(View.GONE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.GONE);
                apply.setVisibility(View.VISIBLE);
            }
        });


        accountYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(accountYes.isChecked()){
                    accountNo.setChecked(false);
                    account="Yes";
                    balance.setVisibility(View.VISIBLE);
                }
                else {
                    account="none";
                    balance.setVisibility(View.GONE);
                }
            }
        });

        accountNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(accountNo.isChecked()){
                    accountYes.setChecked(false);
                    account="No";
                    balance.setVisibility(View.GONE);
                    balance.setText("0");
                }
                else {
                    account="none";
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryForApplication();
            }
        });

        Query query = FirebaseDatabase.getInstance().getReference().child("applications");
        query.orderByChild("userId").equalTo(firebaseUser.getUid()).addChildEventListener(new QueryForGetApplications());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        this.menu= menu;
        updateMenuTitles();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        switch (id){
            case R.id.itemLogOut:
                firebaseAuth.signOut();
                Intent intent3= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.itemAbout:
                Intent intent= new Intent(MainActivity.this, About.class);
                startActivity(intent);
                finish();
                break;
        }

        return true;
    }

    private void updateMenuTitles() {
        final MenuItem usernameMenu = menu.findItem(R.id.itemLogOut);

        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        query.orderByKey().equalTo(firebaseAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ModelUser modelUser=dataSnapshot.getValue(ModelUser.class);
                usernameMenu.setTitle("Log Out ("+modelUser.getUsername()+")");
                district=modelUser.getDistrict();
                upazila=modelUser.getUpazila();
                union=modelUser.getUnion();
                nIDno=modelUser.getNIDno();
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

    private void QueryForApplication() {
        String Name=name.getText().toString().trim();
        String FatherName=fatherName.getText().toString().trim();
        String MotherName=motherName.getText().toString().trim();
        String BirthDate=birthDate.getText().toString().trim();
        String Balance=balance.getText().toString().trim();

        if(Name.isEmpty()) {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }

        if(FatherName.isEmpty()) {
            fatherName.setError("Father's name is required");
            fatherName.requestFocus();
            return;
        }

        if(MotherName.isEmpty()) {
            motherName.setError("Mother's name is required");
            motherName.requestFocus();
            return;
        }

        if(BirthDate.isEmpty()) {
            birthDate.setError("Date of Birth is required");
            birthDate.requestFocus();
            return;
        }


        if (account=="none"){
            Toast.makeText(MainActivity.this,"Please select Yes or No",Toast.LENGTH_SHORT).show();
            return;
        }

        if(account=="Yes"){
            if (Balance.isEmpty()){
                balance.setError("Balance is required");
                balance.requestFocus();
                return;
            }
        }


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String key = databaseReference.child("applications").push().getKey();
        ModelApplication application=new ModelApplication(firebaseUser.getUid(),Name,FatherName,MotherName,BirthDate,nIDno,account,balance.getText().toString()
                .trim(),district,upazila,union,"Not Confirmed yet. Please check later.","no",key);
        databaseReference.child("applications").child(key).setValue(application).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Application has been submitted. Please check status later!",Toast.LENGTH_LONG).show();
                    cardView.setVisibility(View.GONE);
                    apply.setVisibility(View.GONE);
                }
                else {

                    Toast.makeText(MainActivity.this,"Not Applied! Please check connection!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private class QueryForGetApplications implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            final ModelApplication modelApplication = dataSnapshot.getValue(ModelApplication.class);

            if(modelApplication!=null){
                apply.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                if(modelApplication.getStatus().equals("Confirmed")){
                    if (modelApplication.getGotFund().equals("yes")){

                        statusText.setText("You have gotten the fund. Thank you!");
                    }
                    else {
                        statusText.setText("Congratulation! Your Application has been confirmed. Please go to your Union Council and check for the fund. Thank you!");
                    }

                }

                else {
                    statusText.setText(modelApplication.getStatus());
                }
                statusText.setVisibility(View.VISIBLE);

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
    }
}