package com.example.admin_linux.csdevproject;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.databinding.ActivityEnterPhoneBinding;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class EnterPhoneActivity extends AppCompatActivity {

    ActivityEnterPhoneBinding mBinding;

    private static final String TAG = "Check_Auth";

    private LayoutInflater mInf;

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    // Firebase auth stuff
    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String mPhoneNumber;
    private String mVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_enter_phone);

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        // Edit text listeners
        mBinding.etActivityEnterPhoneVerification1.addTextChangedListener(new GenericTextWatcher(mBinding.etActivityEnterPhoneVerification1));
        mBinding.etActivityEnterPhoneVerification2.addTextChangedListener(new GenericTextWatcher(mBinding.etActivityEnterPhoneVerification2));
        mBinding.etActivityEnterPhoneVerification3.addTextChangedListener(new GenericTextWatcher(mBinding.etActivityEnterPhoneVerification3));
        mBinding.etActivityEnterPhoneVerification4.addTextChangedListener(new GenericTextWatcher(mBinding.etActivityEnterPhoneVerification4));
        mBinding.etActivityEnterPhoneVerification5.addTextChangedListener(new GenericTextWatcher(mBinding.etActivityEnterPhoneVerification5));
        mBinding.etActivityEnterPhoneVerification6.addTextChangedListener(new GenericTextWatcher(mBinding.etActivityEnterPhoneVerification6));

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
                //mVerificationInProgress = false;

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

                showUiForEnteringVerificationCode();
            }
        };

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        // [START_EXCLUDE]
//        if (mVerificationInProgress ) {
//            sendVerificationCode(mBinding.etActivityEnterPhoneCountryCode.getText().toString() + mBinding.etActivityEnterPhone.getText().toString());
//        }
//        // [END_EXCLUDE]
//    }

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

    // Send a verification code to the user's phone
    private void sendVerificationCode(String phone) {
        // TODO: Set flag, that verification is in progress (in case activity is destroyed) (onSaveInstanceState)

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,           // Phone number to verify
                60,                  // Timeout duration
                TimeUnit.SECONDS,       // Unit of timeout
                this,           // Activity (for callback binding)
                mCallbacks);            // OnVerificationStateChangedCallbacks

    }

    // Sign in without necessity of entering verification code
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        // Perform signing in with passed credential
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                        launchActivityWithUser(user.getUid());

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
    private void signInWithVerificationIdAndToken(String verificationId, String token) {
        // Create a PhoneAuthCredential object
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, token);
        signInWithPhoneAuthCredential(credential);
    }

    // Show UI for entering verification code
    private void showUiForEnteringVerificationCode() {
        makeInputCodeViewsVisible();
    }

    // Show UI in case of invalid verification code entered
    private void restoreAuthViews() {

    }

    // Launch activity after successful authentication
    private void launchActivityWithUser(String userUID) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_ID, userUID);
        intent.putExtra(Constants.KEY_INTENT_USER_FIREBASE_PHONE_NUMBER, mPhoneNumber);
        startActivity(intent);
    }

    private void makeInputNumberViewsVisible() {
        mBinding.etActivityEnterPhoneCountryCode.setVisibility(View.VISIBLE);
        mBinding.etActivityEnterPhone.setVisibility(View.VISIBLE);
        mBinding.tvActivityEnterPhoneTitle.setVisibility(View.VISIBLE);
        mBinding.btnActivityEnterPhoneVerifyNumber.setVisibility(View.VISIBLE);

        mBinding.tvActivityEnterPhoneLabelVerification.setVisibility(View.GONE);
        mBinding.etActivityEnterPhoneVerification1.setVisibility(View.GONE);
        mBinding.etActivityEnterPhoneVerification2.setVisibility(View.GONE);
        mBinding.etActivityEnterPhoneVerification3.setVisibility(View.GONE);
        mBinding.etActivityEnterPhoneVerification4.setVisibility(View.GONE);
        mBinding.etActivityEnterPhoneVerification5.setVisibility(View.GONE);
        mBinding.etActivityEnterPhoneVerification6.setVisibility(View.GONE);
        mBinding.btnActivityEnterPhoneVerifyCode.setVisibility(View.GONE);
    }

    private void makeInputCodeViewsVisible() {
        mBinding.etActivityEnterPhoneCountryCode.setVisibility(View.GONE);
        mBinding.etActivityEnterPhone.setVisibility(View.GONE);
        mBinding.tvActivityEnterPhoneTitle.setVisibility(View.GONE);
        mBinding.btnActivityEnterPhoneVerifyNumber.setVisibility(View.GONE);

        mBinding.tvActivityEnterPhoneLabelVerification.setVisibility(View.VISIBLE);
        mBinding.etActivityEnterPhoneVerification1.setVisibility(View.VISIBLE);
        mBinding.etActivityEnterPhoneVerification2.setVisibility(View.VISIBLE);
        mBinding.etActivityEnterPhoneVerification3.setVisibility(View.VISIBLE);
        mBinding.etActivityEnterPhoneVerification4.setVisibility(View.VISIBLE);
        mBinding.etActivityEnterPhoneVerification5.setVisibility(View.VISIBLE);
        mBinding.etActivityEnterPhoneVerification6.setVisibility(View.VISIBLE);
        mBinding.btnActivityEnterPhoneVerifyCode.setVisibility(View.VISIBLE);

        mBinding.etActivityEnterPhoneVerification1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mBinding.etActivityEnterPhoneVerification1, InputMethodManager.SHOW_IMPLICIT);
    }

    private void getSignInTokenFromViews() {
        if(!mBinding.etActivityEnterPhoneVerification1.getText().toString().equals("") &&
                !mBinding.etActivityEnterPhoneVerification2.getText().toString().equals("") &&
                !mBinding.etActivityEnterPhoneVerification3.getText().toString().equals("") &&
                !mBinding.etActivityEnterPhoneVerification4.getText().toString().equals("") &&
                !mBinding.etActivityEnterPhoneVerification5.getText().toString().equals("") &&
                !mBinding.etActivityEnterPhoneVerification6.getText().toString().equals("")) {

            mVerificationCode = mBinding.etActivityEnterPhoneVerification1.getText().toString() +
                    mBinding.etActivityEnterPhoneVerification2.getText().toString() +
                    mBinding.etActivityEnterPhoneVerification3.getText().toString() +
                    mBinding.etActivityEnterPhoneVerification4.getText().toString() +
                    mBinding.etActivityEnterPhoneVerification5.getText().toString() +
                    mBinding.etActivityEnterPhoneVerification6.getText().toString();

            signInWithVerificationIdAndToken(mVerificationId, mVerificationCode);
        } else {
            Toast.makeText(this, "Please enter verification code", Toast.LENGTH_SHORT).show();
        }

    }

    public void btnVerifyPhoneNumberClick(View view) {
        mPhoneNumber = mBinding.etActivityEnterPhoneCountryCode.getText().toString() + mBinding.etActivityEnterPhone.getText().toString();
        sendVerificationCode(mPhoneNumber);
    }

    public void btnVerifyCodeClicked(View view) {
        getSignInTokenFromViews();
    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch(view.getId())
            {
                case R.id.et_activity_enter_phone_verification_1:
                    if(text.length()==1)
                    mBinding.etActivityEnterPhoneVerification2.requestFocus();
                    break;
                case R.id.et_activity_enter_phone_verification_2:
                    if(text.length()==1)
                        mBinding.etActivityEnterPhoneVerification3.requestFocus();
                    break;
                case R.id.et_activity_enter_phone_verification_3:
                    if(text.length()==1)
                        mBinding.etActivityEnterPhoneVerification4.requestFocus();
                    break;
                case R.id.et_activity_enter_phone_verification_4:
                    if(text.length()==1)
                        mBinding.etActivityEnterPhoneVerification5.requestFocus();
                    break;
                case R.id.et_activity_enter_phone_verification_5:
                    if(text.length()==1)
                        mBinding.etActivityEnterPhoneVerification6.requestFocus();
                    break;
                case R.id.et_activity_enter_phone_verification_6:
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    }
}
