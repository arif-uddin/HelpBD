package com.diu.helpbd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.diu.helpbd.R;

import java.util.Objects;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Objects.requireNonNull(getSupportActionBar()).setTitle("About");
    }
}