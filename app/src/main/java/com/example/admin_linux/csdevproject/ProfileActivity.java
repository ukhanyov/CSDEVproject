package com.example.admin_linux.csdevproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void tvLogoutClicked(View view) {
        Toast.makeText(this, "lou out clicked", Toast.LENGTH_SHORT).show();
    }

    public void ivProfileExpandClicked(View view) {
        Toast.makeText(this, "Feature is under development", Toast.LENGTH_SHORT).show();
    }
}
