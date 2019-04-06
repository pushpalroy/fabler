package com.techradge.fabler.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.techradge.fabler.R;
import com.techradge.fabler.ui.fragment.DraftFragment;
import com.techradge.fabler.ui.fragment.HomeFragment;
import com.techradge.fabler.ui.login.LoginActivity;
import com.techradge.fabler.utils.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;
    @BindView(R.id.nav_view)
    public NavigationView navigationView;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (toolbar.getTitle().equals(getResources().getString(R.string.home)))
                super.onBackPressed();
            else {
                toolbar.setTitle(getResources().getString(R.string.home));
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.nav_home) {
            toolbar.setTitle(getResources().getString(R.string.home));
            fragment = new HomeFragment();
        } else if (id == R.id.nav_compose) {
            Intent composeIntent = new Intent(MainActivity.this, ComposeActivity.class);
            navigationView.getMenu().getItem(0).setChecked(true);
            startActivity(composeIntent);
        } else if (id == R.id.nav_draft) {
            toolbar.setTitle(getResources().getString(R.string.drafts));
            fragment = new DraftFragment();
        } else if (id == R.id.nav_logout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            PrefManager prefManager = new PrefManager(MainActivity.this);
                            prefManager.setUserLoggedIn(false);
                            prefManager.setUserFullName(getString(R.string.guest));
                            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                            finish();
                        }
                    });
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        prefManager = new PrefManager(this);
        View headerView = navigationView.getHeaderView(0);
        TextView name = headerView.findViewById(R.id.tv_name);
        TextView email = headerView.findViewById(R.id.tv_email);
        ImageView profileImage = headerView.findViewById(R.id.profile_image);
        name.setText(prefManager.getUserFullName());
        email.setText(prefManager.getUserEmail());
        if (prefManager.getUserPhotoUrl() != null && !prefManager.getUserPhotoUrl().equals(""))
            Glide.with(this)
                    .load(prefManager.getUserPhotoUrl())
                    .into(profileImage);

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }
}