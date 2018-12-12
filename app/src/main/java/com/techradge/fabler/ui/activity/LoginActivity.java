package com.techradge.fabler.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.techradge.fabler.R;
import com.techradge.fabler.database.firebase.Database;
import com.techradge.fabler.database.operations.user.UserDataOp;
import com.techradge.fabler.model.User;
import com.techradge.fabler.utils.PrefManager;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 1;
    private String uid;

    private UserDataOp userDataOp;
    private PrefManager prefManager;

    public LoginActivity() {
        userDataOp = new UserDataOp(Database.getFirebaseDatabase());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);

        if (prefManager.isUserLoggedIn()) {
            starHomeActivity();
            return;
        }

        // Authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

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

    public void onAuthenticated(User user) {
        LoginActivity.this.uid = user.getUid();

        try {
            userDataOp.insertUserData(user, this);

            Log.e(TAG, "Full Name: " + user.getFullName());
            Log.e(TAG, "Email: " + user.getEmail());
            Log.e(TAG, "UID: " + user.getUid());
            Log.e(TAG, "Photo: " + String.valueOf(user.getPhotoURL()));
            Log.e(TAG, "Is Email Verified: " + String.valueOf(user.isEmailVerified()));

            prefManager.setUserLoggedIn(true);
            prefManager.setUserFullName(user.getFullName());
            startWelcomeActivity(user);

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
    }

    public void startWelcomeActivity(User user) {
        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
        finish();
    }

    public void starHomeActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void onLoggedOut() {
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
}