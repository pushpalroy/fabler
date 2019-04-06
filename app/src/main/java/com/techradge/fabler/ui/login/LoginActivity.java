package com.techradge.fabler.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.techradge.fabler.R;
import com.techradge.fabler.ui.base.BaseActivity;
import com.techradge.fabler.data.model.User;
import com.techradge.fabler.ui.main.MainActivity;
import com.techradge.fabler.ui.welcome.WelcomeActivity;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 1;
    private String uid;
    private LoginContract.LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Called only when user logs in for the first time, that is startActivityForResult() is called
        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    String fullName = firebaseUser.getDisplayName();
                    String email = firebaseUser.getEmail();
                    boolean emailVerified = firebaseUser.isEmailVerified();
                    String photoURL = String.valueOf(firebaseUser.getPhotoUrl());
                    String uid = firebaseUser.getUid();

                    User newUser = new User(fullName, email, emailVerified, photoURL, uid);
                    onAuthenticated(newUser);
                }

                String idpToken = "";
                if (idpResponse != null) {
                    idpToken = idpResponse.getIdpToken();
                }
                Log.e(TAG, "IDP_TOKEN: " + idpToken);
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    public void startFirebaseUIAuth() {
        // Authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                // Email
                new AuthUI.IdpConfig.EmailBuilder().build(),
                // Google
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                // Facebook
                new AuthUI.IdpConfig.FacebookBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.logo)
                        .setTheme(R.style.LoginTheme)
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onAuthenticated(User user) {
        LoginActivity.this.uid = user.getUid();
        mPresenter.insertUserData(user);
    }

    @Override
    public void startWelcomeActivity(User user) {
        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
        finish();
    }

    @Override
    public void starHomeActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }
}