package com.techradge.fabler.ui.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.techradge.fabler.R;
import com.techradge.fabler.database.offline.AppExecutors;
import com.techradge.fabler.utils.PrefManager;
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
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        ButterKnife.bind(this);
        setUpActionBar();

        prefManager = new PrefManager(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            String story = extras.getString("story");
            String author = extras.getString("author");
            String time = extras.getString("time");

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
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        prefManager.setWidgetStoryTitle(titleTv.getText().toString());
                        prefManager.setWidgetStoryAuthor(authorTv.getText().toString());
                        prefManager.setWidgetStoryBody(bodyTv.getText().toString());

                        sendUpdateWidgetBroadcast();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReadActivity.this, "Story added to widget!", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (SQLiteConstraintException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
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
}
