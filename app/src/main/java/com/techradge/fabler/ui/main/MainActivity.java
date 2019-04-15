package com.techradge.fabler.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends BaseActivity
        implements MainContract.MainView, NavigationView.OnNavigationItemSelectedListener {

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
        mPresenter.loadUserData();
        setUpActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    public void populateUserDetails(String userFullName, String userEmail, String userPhotoUrl) {
        View headerView = navigationView.getHeaderView(0);
        TextView name = headerView.findViewById(R.id.tv_name);
        TextView email = headerView.findViewById(R.id.tv_email);
        ImageView profileImage = headerView.findViewById(R.id.profile_image);

        name.setText(userFullName);
        email.setText(userEmail);
        if (userPhotoUrl != null && !userPhotoUrl.equals(""))
            Glide.with(this)
                    .load(userPhotoUrl)
                    .into(profileImage);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home)
            onHomeSelected();
        else if (id == R.id.nav_compose)
            onComposeSelected();
        else if (id == R.id.nav_draft)
            onDraftSelected();
        else if (id == R.id.nav_logout)
            onLogoutSelected();
        closeNavDrawer();
        return true;
    }

    @Override
    public void onHomeSelected() {
        mToolbar.setTitle(getResources().getString(R.string.home));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new HomeFragment())
                .commit();
    }

    @Override
    public void onComposeSelected() {
        Intent composeIntent = new Intent(MainActivity.this, ComposeActivity.class);
        navigationView.getMenu().getItem(0).setChecked(true);
        startActivity(composeIntent);
    }

    @Override
    public void onDraftSelected() {
        mToolbar.setTitle(getResources().getString(R.string.drafts));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new DraftFragment())
                .commit();
    }

    @Override
    public void openLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

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