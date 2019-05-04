package com.techradge.fabler.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.techradge.fabler.R;
import com.techradge.fabler.ui.base.BaseActivity;
import com.techradge.fabler.ui.compose.ComposeActivity;
import com.techradge.fabler.ui.draft.DraftFragment;
import com.techradge.fabler.ui.home.HomeFragment;
import com.techradge.fabler.ui.login.LoginActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements MainContract.MainView {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;
    @BindView(R.id.nav_view)
    public NavigationView navigationView;

    @Inject
    public MainPresenter<MainContract.MainView, MainContract.MainInteractor> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        setUp();
    }

    @Override
    public void setUp() {
        mPresenter.onAttach(MainActivity.this);
        mPresenter.loadUserDetails();
        setUpActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        onHomeSelected();
    }

    @Override
    public void populateUserDetails(String userFullName, String userEmail, String userPhotoUrl) {
        TextView name = findViewById(R.id.tv_name);
        TextView email = findViewById(R.id.tv_email);
        ImageView profileImage = findViewById(R.id.profile_image);

        name.setText(userFullName);
        email.setText(userEmail);
        if (userPhotoUrl != null && !userPhotoUrl.equals(""))
            Glide.with(this)
                    .load(userPhotoUrl)
                    .into(profileImage);
    }

    @OnClick(R.id.nav_item_home)
    @Override
    public void onHomeSelected() {
        mToolbar.setTitle(getResources().getString(R.string.home));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, HomeFragment.newInstance())
                .commit();

        closeNavDrawer();
    }

    @OnClick(R.id.nav_item_compose)
    @Override
    public void onComposeSelected() {
        Intent composeIntent = new Intent(MainActivity.this, ComposeActivity.class);
        startActivity(composeIntent);

        closeNavDrawer();
    }

    @OnClick(R.id.nav_item_drafts)
    @Override
    public void onDraftSelected() {
        mToolbar.setTitle(getResources().getString(R.string.drafts));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, DraftFragment.newInstance())
                .commit();

        closeNavDrawer();
    }

    @Override
    public void openLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @OnClick(R.id.nav_item_logout)
    @Override
    public void onLogoutSelected() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        mPresenter.setUserLoggedOut();
                        openLoginActivity();
                    }
                });
        closeNavDrawer();
    }

    @Override
    public void closeNavDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void returnToHome() {
        onHomeSelected();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            closeNavDrawer();
        else {
            if (mToolbar.getTitle().equals(getResources().getString(R.string.home)))
                super.onBackPressed();
            else returnToHome();
        }
    }
}