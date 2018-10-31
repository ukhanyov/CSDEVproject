package com.example.admin_linux.csdevproject;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.example.admin_linux.csdevproject.databinding.ActivityAuthBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AuthActivity extends AppCompatActivity{

    ActivityAuthBinding mBinding;

    private static final String TAG = "AuthActivity";

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth);

//        // Restore instance state
//        if (savedInstanceState != null) {
//            onRestoreInstanceState(savedInstanceState);
//        }
//
//        // [START initialize_auth]
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//        // [END initialize_auth]
//
//        // Initialize phone auth callbacks
//        // [START phone_auth_callbacks]
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential credential) {
//                // This callback will be invoked in two situations:
//                // 1 - Instant verification. In some cases the phone number can be instantly
//                //     verified without needing to send or enter a verification code.
//                // 2 - Auto-retrieval. On some devices Google Play services can automatically
//                //     detect the incoming verification SMS and perform verification without
//                //     user action.
//                // [START_EXCLUDE silent]
//                mVerificationInProgress = false;
//                // [END_EXCLUDE]
//
//                // [START_EXCLUDE silent]
//                // Update the UI and attempt sign in with the phone credential
//
//                // [END_EXCLUDE]
//                signInWithPhoneAuthCredential(credential);
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                // This callback is invoked in an invalid request for verification is made,
//                // for instance if the the phone number format is not valid.
//                // [START_EXCLUDE silent]
//                mVerificationInProgress = false;
//                // [END_EXCLUDE]
//
//                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                    // Invalid request
//                    // [START_EXCLUDE]
//
//                    // [END_EXCLUDE]
//                } else if (e instanceof FirebaseTooManyRequestsException) {
//                    // The SMS quota for the project has been exceeded
//                    // [START_EXCLUDE]
//
//                    // [END_EXCLUDE]
//                }
//
//                // Show a message and update the UI
//                // [START_EXCLUDE]
//
//                // [END_EXCLUDE]
//            }
//
//            @Override
//            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
//                // The SMS verification code has been sent to the provided phone number, we
//                // now need to ask the user to enter the code and then construct a credential
//                // by combining the code with a verification ID.
//
//                // Save verification ID and resending token so we can use them later
//
//                mResendToken = token;
//
//                // [START_EXCLUDE]
//                // Update UI
//                // [END_EXCLUDE]
//            }
//        };
//        // [END phone_auth_callbacks]

    }
//
//    // [START on_start_check_user]
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        // [START_EXCLUDE]
//        if (mVerificationInProgress && validatePhoneNumber()) {
//            //startPhoneNumberVerification(mPhoneNumberField.getText().toString());
//        }
//        // [END_EXCLUDE]
//    }
//    // [END on_start_check_user]
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
//    }
//
//    private void startPhoneNumberVerification(String phoneNumber) {
//        // [START start_phone_auth]
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//        // [END start_phone_auth]
//
//        mVerificationInProgress = true;
//    }
//
//    private void verifyPhoneNumberWithCode(String verificationId, String code) {
//        // [START verify_with_code]
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        // [END verify_with_code]
//        signInWithPhoneAuthCredential(credential);
//    }
//
//    // [START resend_verification]
//    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks,         // OnVerificationStateChangedCallbacks
//                token);             // ForceResendingToken from callbacks
//    }
//    // [END resend_verification]
//
//    // [START sign_in_with_phone]
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success
//                        FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
//                        // [START_EXCLUDE]
//
//                        // [END_EXCLUDE]
//                    } else {
//                        // Sign in failed
//                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                            // The verification code entered was invalid
//                            // [START_EXCLUDE silent]
//
//                            // [END_EXCLUDE]
//                        }
//                        // [START_EXCLUDE silent]
//
//                        // [END_EXCLUDE]
//                    }
//                });
//    }
//    // [END sign_in_with_phone]
//
//    // Sign out mechanics
//    private void signOut() {
//        mAuth.signOut();
//    }
//
//    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
//        switch (uiState) {
//            case STATE_INITIALIZED:
//                // Initialized state, show only the phone number field and start button
//                break;
//
//            case STATE_CODE_SENT:
//                // Code sent state, show the verification field, the
//                break;
//
//            case STATE_VERIFY_FAILED:
//                // Verification has failed, show all options
//                break;
//
//            case STATE_VERIFY_SUCCESS:
//                // Verification has succeeded, proceed to firebase sign in
//
//
//                // Set the verification text based on the credential
//                if (cred != null) {
//                    if (cred.getSmsCode() != null) {
//
//                    } else {
//
//                    }
//                }
//
//                break;
//
//            case STATE_SIGNIN_FAILED:
//                // No-op, handled by sign-in check
//                break;
//
//            case STATE_SIGNIN_SUCCESS:
//                // Np-op, handled by sign-in check
//                break;
//        }
//
//        if (user == null) {
//            // Signed out
//
//        } else {
//            // Signed in
//
//        }
//    }
//
//    private boolean validatePhoneNumber() {
//        String phoneNumber = "";
//        if (TextUtils.isEmpty(phoneNumber)) {
//
//            return false;
//        }
//
//        return true;
//    }

    public void btnUsePhoneNumberClicked(View view) {
        startActivity(new Intent(this, EnterPhoneActivity.class));
    }

    public void btnUseTokenClicked(View view) {

    }
}
