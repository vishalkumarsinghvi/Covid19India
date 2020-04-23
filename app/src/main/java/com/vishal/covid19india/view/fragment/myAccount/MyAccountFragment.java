package com.vishal.covid19india.view.fragment.myAccount;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.vishal.covid19india.R;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public class MyAccountFragment extends Fragment implements OnClickListener {

  private static final String TAG = "MyAccountFragment";
  private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

  private static final int STATE_INITIALIZED = 1;
  private static final int STATE_CODE_SENT = 2;
  private static final int STATE_VERIFY_FAILED = 3;
  private static final int STATE_VERIFY_SUCCESS = 4;
  private static final int STATE_SIGNIN_FAILED = 5;
  private static final int STATE_SIGNIN_SUCCESS = 6;

  private FirebaseAuth mAuth;

  private boolean mVerificationInProgress = false;
  private String mVerificationId;
  private PhoneAuthProvider.ForceResendingToken mResendToken;
  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

  private ViewGroup mPhoneNumberViews;
  private ViewGroup mSignedInViews;

  private TextView mTvMobileNumber;

  private TextInputEditText mPhoneNumberField;
  private TextInputEditText mVerificationField;

  private TextInputLayout mPhoneNumberFieldTextInput;
  private TextInputLayout mVerificationFieldTextInput;

  private CircularProgressButton mStartButton;
  private CircularProgressButton mVerifyButton;
  private TextView mResendButton;
  private CircularProgressButton mSignOutButton, mAboutButton;
  private TextWatcher textWatcher;

  @Override
  public void onSaveInstanceState(@NotNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (savedInstanceState != null) {
      mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }
  }

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_my_account, container, false);
    initUI(root, savedInstanceState);
    return root;
  }

  private void initUI(View view, Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      onViewStateRestored(savedInstanceState);
    }
    mPhoneNumberViews = view.findViewById(R.id.phoneAuthFields);
    mSignedInViews = view.findViewById(R.id.signedInButtons);

    mTvMobileNumber = view.findViewById(R.id.tv_phone_number);
    mTvMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_correct, 0);
//    mDetailText = view.findViewById(R.id.detail);

    mPhoneNumberField = view.findViewById(R.id.fieldPhoneNumber);
    mVerificationField = view.findViewById(R.id.fieldVerificationCode);

    mPhoneNumberFieldTextInput = view.findViewById(R.id.fieldPhoneNumber_text_input);
    mVerificationFieldTextInput = view.findViewById(R.id.fieldVerificationCode_text_input);

    mStartButton = view.findViewById(R.id.buttonStartVerification);
    mVerifyButton = view.findViewById(R.id.buttonVerifyPhone);
    mResendButton = view.findViewById(R.id.buttonResend);
    mSignOutButton = view.findViewById(R.id.signOutButton);
    mAboutButton = view.findViewById(R.id.aboutButton);

    mStartButton.setOnClickListener(this);
    mVerifyButton.setOnClickListener(this);
    mResendButton.setOnClickListener(this);
    mSignOutButton.setOnClickListener(this);
    mAboutButton.setOnClickListener(this);

    mAuth = FirebaseAuth.getInstance();

    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

      @Override
      public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {
        Log.d(TAG, "onVerificationCompleted:" + credential);
        mVerificationInProgress = false;
        updateUI(STATE_VERIFY_SUCCESS, credential);
        signInWithPhoneAuthCredential(credential);
      }

      @Override
      public void onVerificationFailed(@NotNull FirebaseException e) {
        Log.w(TAG, "onVerificationFailed", e);
        mVerificationInProgress = false;

        if (e instanceof FirebaseAuthInvalidCredentialsException) {
//          mPhoneNumberField.setError("Invalid phone number.");
          mPhoneNumberField.setError(null);
          mPhoneNumberFieldTextInput.setError("Invalid phone number.");
        } else if (e instanceof FirebaseTooManyRequestsException) {
          showSnackBar("Quota exceeded.");
        }
        updateUI(STATE_VERIFY_FAILED);
      }

      @Override
      public void onCodeSent(@NonNull String verificationId,
          @NonNull PhoneAuthProvider.ForceResendingToken token) {
        Log.d(TAG, "onCodeSent:" + verificationId);
        mVerificationId = verificationId;
        mResendToken = token;
        updateUI(STATE_CODE_SENT);
      }
    };
    addTextWatchers();
    mPhoneNumberField.addTextChangedListener(textWatcher);
    mVerificationField.addTextChangedListener(textWatcher);
  }

  private void addTextWatchers() {
    textWatcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mPhoneNumberFieldTextInput.setError(null);
        mVerificationFieldTextInput.setError(null);
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    };
  }

  private void showSnackBar(String text) {
    Snackbar.make(requireActivity().findViewById(android.R.id.content), text,
        Snackbar.LENGTH_SHORT).show();
  }


  @Override
  public void onStart() {
    super.onStart();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    updateUI(currentUser);
    if (mVerificationInProgress && validatePhoneNumber()) {
      startPhoneNumberVerification(
          "+91".concat(Objects.requireNonNull(mPhoneNumberField.getText()).toString()));
    }
  }

  private void startPhoneNumberVerification(String phoneNumber) {
    if (getActivity() != null) {
      PhoneAuthProvider.getInstance().verifyPhoneNumber(
          phoneNumber,
          60,
          TimeUnit.SECONDS,
          getActivity(),
          mCallbacks);
      mVerificationInProgress = true;
    }
  }

  private void verifyPhoneNumberWithCode(String verificationId, String code) {
    try {
      PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
      signInWithPhoneAuthCredential(credential);
    } catch (Exception e) {
      Toast toast = Toast.makeText(getActivity(), "Verification Code is wrong", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }


  }

  private void resendVerificationCode(String phoneNumber,
      PhoneAuthProvider.ForceResendingToken token) {
    if (getActivity() != null) {
      PhoneAuthProvider.getInstance().verifyPhoneNumber(
          phoneNumber,
          60,
          TimeUnit.SECONDS,
          getActivity(),
          mCallbacks,
          token);
    }
  }

  private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(requireActivity(), task -> {
          if (task.isSuccessful()) {
            Log.d(TAG, "signInWithCredential:success");
            FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
            updateUI(STATE_SIGNIN_SUCCESS, user);
          } else {
            Log.w(TAG, "signInWithCredential:failure", task.getException());
            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
              mVerificationField.setError(null);
              mVerificationFieldTextInput.setError("Invalid code.");
            }
            updateUI(STATE_SIGNIN_FAILED);
          }
        });
  }

  private void signOut() {
    mAuth.signOut();
    updateUI(STATE_INITIALIZED);
  }

  private void updateUI(int uiState) {
    updateUI(uiState, mAuth.getCurrentUser(), null);
  }

  private void updateUI(FirebaseUser user) {
    if (user != null) {
      updateUI(STATE_SIGNIN_SUCCESS, user);
    } else {
      updateUI(STATE_INITIALIZED);
    }
  }

  private void updateUI(int uiState, FirebaseUser user) {
    updateUI(uiState, user, null);
  }

  private void updateUI(int uiState, PhoneAuthCredential cred) {
    updateUI(uiState, null, cred);
  }

  private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
    switch (uiState) {
      case STATE_INITIALIZED:
        // Initialized state, show only the phone number field and start button
        enableViews(mStartButton, mPhoneNumberField, mPhoneNumberFieldTextInput);
        disableViews(mVerifyButton, mResendButton, mVerificationField, mVerificationFieldTextInput);
        break;
      case STATE_CODE_SENT:
        // Code sent state, show the verification field, the
        enableViews(mVerifyButton, mResendButton, mVerificationField, mVerificationFieldTextInput);
        disableViews(mStartButton, mPhoneNumberField, mPhoneNumberFieldTextInput);
//        mDetailText.setText(R.string.status_code_sent);
        showSnackBar(getString(R.string.status_code_sent));
        break;
      case STATE_VERIFY_FAILED:
        // Verification has failed, show all options
        enableViews(mStartButton, mPhoneNumberField,
            mPhoneNumberFieldTextInput,
            mVerificationField);
        disableViews(mVerifyButton, mResendButton, mVerificationField, mVerificationFieldTextInput);
//        mDetailText.setText(R.string.status_verification_failed);
        showSnackBar(getString(R.string.status_verification_failed));
        break;
      case STATE_VERIFY_SUCCESS:
        // Verification has succeeded, proceed to firebase sign in
        disableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
            mPhoneNumberFieldTextInput,
            mVerificationField, mVerificationFieldTextInput);
//        mDetailText.setText(R.string.status_verification_succeeded);
        showSnackBar(getString(R.string.status_verification_succeeded));

        // Set the verification text based on the credential
        if (cred != null) {
          if (cred.getSmsCode() != null) {
            mVerificationField.setText(cred.getSmsCode());
          } else {
            mVerificationField.setText(R.string.instant_validation);
          }
        }

        break;
      case STATE_SIGNIN_FAILED:
        // No-op, handled by sign-in check
//        mDetailText.setText(R.string.status_sign_in_failed);
        showSnackBar(getString(R.string.status_sign_in_failed));
        break;
      case STATE_SIGNIN_SUCCESS:
        // Np-op, handled by sign-in check
        break;
    }

    if (user == null) {
      // Signed out
      mPhoneNumberViews.setVisibility(View.VISIBLE);
      mSignedInViews.setVisibility(View.GONE);

//      mStatusText.setText(R.string.signed_out);
    } else {
      // Signed in
      mPhoneNumberViews.setVisibility(View.GONE);
      mSignedInViews.setVisibility(View.VISIBLE);

      enableViews(mPhoneNumberField, mVerificationField);
      mPhoneNumberField.setText(null);
      mVerificationField.setText(null);
      mTvMobileNumber.setText(user.getPhoneNumber());
//      mStatusText.setText(R.string.signed_in);
//      mDetailText.setText(getString(R.string.firebase_status_fmt, user.getUid()));
    }
  }

  private boolean validatePhoneNumber() {
    String phoneNumber = Objects.requireNonNull(mPhoneNumberField.getText()).toString();
    if (TextUtils.isEmpty(phoneNumber)) {
      mPhoneNumberField.setError(null);
      mPhoneNumberFieldTextInput.setError("Invalid phone number.");
      return false;
    }

    return true;
  }

  private void enableViews(View... views) {
    for (View v : views) {
      v.setEnabled(true);
      v.setVisibility(View.VISIBLE);
    }
  }

  private void disableViews(View... views) {
    for (View v : views) {
      v.setEnabled(false);
      v.setVisibility(View.GONE);
    }
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.buttonStartVerification:
        if (!validatePhoneNumber()) {
          return;
        }
        startPhoneNumberVerification("+91".concat(
            Objects.requireNonNull(mPhoneNumberField.getText()).toString()));
        break;
      case R.id.buttonVerifyPhone:
        String code = Objects.requireNonNull(mVerificationField.getText()).toString();
        if (TextUtils.isEmpty(code)) {
          mVerificationField.setError(null);
          mVerificationFieldTextInput.setError("Cannot be empty.");
          return;
        }

        verifyPhoneNumberWithCode(mVerificationId, code);
        break;
      case R.id.buttonResend:
        resendVerificationCode(
            "+91".concat(Objects.requireNonNull(mPhoneNumberField.getText()).toString()),
            mResendToken);
        break;
      case R.id.signOutButton:
        signOut();
        break;
      case R.id.aboutButton:
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.linkedin.com/in/vishalkumarsinghvi"));
        view.getContext().startActivity(i);
        break;
    }
  }

}
