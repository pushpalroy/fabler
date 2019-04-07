package com.techradge.fabler.ui.read;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.techradge.fabler.R;
import com.techradge.fabler.data.prefs.AppPreferencesHelper;
import com.techradge.fabler.widget.StoryWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView titleTv;
    @BindView(R.id.tv_body)
    TextView bodyTv;
    @BindView(R.id.tv_time)
    TextView timeTv;
    @BindView(R.id.tv_author)
    TextView authorTv;
    public static final String TAG = ReadActivity.class.getSimpleName();
    private AppPreferencesHelper appPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        ButterKnife.bind(this);
        setUpActionBar();

        appPrefsManager = new AppPreferencesHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(getString(R.string.key_title));
            String story = extras.getString(getString(R.string.key_story));
            String author = extras.getString(getString(R.string.key_author));
            String time = extras.getString(getString(R.string.key_time));

            titleTv.setText(title);
            bodyTv.setText(story);
            authorTv.setText(author);
            timeTv.setText(time);
        }
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            toolbar.setTitleTextColor(Color.WHITE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_to_widget) {
            new AddToWidget().execute();
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void sendUpdateWidgetBroadcast() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ReadActivity.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, StoryWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_widget_story);
        StoryWidgetProvider.updateWidget(ReadActivity.this, appWidgetManager, appWidgetIds);
    }

    @SuppressLint("StaticFieldLeak")
    private class AddToWidget extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            appPrefsManager.setWidgetStoryTitle(titleTv.getText().toString());
            appPrefsManager.setWidgetStoryAuthor(authorTv.getText().toString());
            appPrefsManager.setWidgetStoryBody(bodyTv.getText().toString());
        }

        @Override
        protected Void doInBackground(Void... params) {
            sendUpdateWidgetBroadcast();
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            Toast.makeText(ReadActivity.this, getString(R.string.toast_message_widget), Toast.LENGTH_LONG).show();
        }
    }
}
