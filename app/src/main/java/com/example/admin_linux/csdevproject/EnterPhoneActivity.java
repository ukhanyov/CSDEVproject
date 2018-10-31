package com.example.admin_linux.csdevproject;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.databinding.ActivityEnterPhoneBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterPhoneActivity extends AppCompatActivity {

    ActivityEnterPhoneBinding mBinding;

    private static final String TAG = "Check_Auth";

    private Spinner spinner;
    private LayoutInflater mInf;

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    // Firebase auth stuff
    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_enter_phone);

        // Spinner stuff
        spinner = mBinding.spinnerActivityEnterPhone;
        spinner.setAdapter(new NewAdapter());
        mInf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage(); // Language of verification sms

        // Manage callbacks of PhoneAuthProvider
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                showUiForEnteringVerificationCode(mVerificationId);
            }
        };


    }

    // Send a verification code to the user's phone
    private void sendVerificationCode(){
        // TODO: Set flag, that verification is in progress (in case activity is destroyed) (onSaveInstanceState)

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber,           // Phone number to verify
                60,                  // Timeout duration
                TimeUnit.SECONDS,       // Unit of timeout
                this,           // Activity (for callback binding)
                mCallbacks);            // OnVerificationStateChangedCallbacks

    }

    // Sign in without necessity of entering verification code
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){

        // Perform signing in with passed credential
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = task.getResult().getUser();
                        launchActivityWithUser();

                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            restoreAuthViews();
                        }
                    }
                });

    }

    // Sign in with necessity of entering verification code
    private void signInWithVerificationIdAndToken(String verificationId, String token){
        // Create a PhoneAuthCredential object
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, token);
        signInWithPhoneAuthCredential(credential);
    }

    // Show UI for entering verification code
    private void showUiForEnteringVerificationCode(String verificationId){
        // TODO: add logic to get verification token from views
        String token = "123123";
        signInWithVerificationIdAndToken(verificationId, token);
    }

    // Show UI in case of invalid verification code entered
    private void restoreAuthViews(){

    }

    // Launch activity after successful authentication
    private void launchActivityWithUser(){

    }


    public void btnVerifyPhoneNumberClick(View view) {
        mPhoneNumber = mBinding.etActivityEnterPhone.getText().toString();
        sendVerificationCode();
    }

    // TODO: Refactor it
    // Adapted to populate spinner of countries
    class NewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInf.inflate(R.layout.list_item_spinner, null);
            }
            return convertView;
        }

    }
}
