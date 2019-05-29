package com.techradge.fabler.ui.main;

import android.content.Context;

import androidx.core.view.GravityCompat;
import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.FirebaseApp;
import com.techradge.fabler.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        FirebaseApp.initializeApp(context);
        mainActivity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() {
        assertThat(mainActivity).isNotNull();
    }

    @Test
    public void shouldHaveCorrectAppName() {
        assertThat(mainActivity.getResources().getString(R.string.app_name)).isEqualTo("Fabler");
    }

    @Test
    public void showHaveTitleAsHome() {
        assertThat(mainActivity.mToolbar.getTitle()).isEqualTo("Home");
    }

    @Test
    public void showHaveDrawerClosed() {
        assertThat(mainActivity.drawer.isDrawerOpen(GravityCompat.START)).isFalse();
    }
}
