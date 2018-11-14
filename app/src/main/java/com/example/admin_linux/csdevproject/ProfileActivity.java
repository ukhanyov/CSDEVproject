package com.example.admin_linux.csdevproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.auth.AuthActivity;
import com.example.admin_linux.csdevproject.databinding.ActivityProfileBinding;
import com.example.admin_linux.csdevproject.utils.CircleTransform;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);

        Picasso.get().load(preferences.getString(Constants.PREF_PROFILE_IMAGE_URL, null)).fit().centerInside()
                .placeholder(Objects.requireNonNull(getDrawable(R.drawable.ic_profile_default)))
                .error(Objects.requireNonNull(getDrawable(R.drawable.ic_error_red)))
                .transform(new CircleTransform())
                .into(mBinding.ivActivityProfilePersonImage);

        mBinding.tvActivityProfileUserName.setText(preferences.getString(Constants.PREF_PROFILE_FULL_NAME, null));
        mBinding.tvActivityProfileUserEmail.setText(preferences.getString(Constants.PREF_PROFILE_EMAIL, null));
        mBinding.tvActivityProfileUserPhone.setText(preferences.getString(Constants.PREF_PROFILE_PHONE_NUMBER, null));
    }

    public void tvLogoutClicked(View view) {
        SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        startActivity(new Intent(this, AuthActivity.class));
    }

    public void ivProfileExpandClicked(View view) {
        Toast.makeText(this, "Feature is under development", Toast.LENGTH_SHORT).show();
    }
}
