package com.techradge.fabler.ui.activity;

import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.techradge.fabler.R;
import com.techradge.fabler.database.firebase.Database;
import com.techradge.fabler.database.offline.AppExecutors;
import com.techradge.fabler.database.offline.StoryDatabase;
import com.techradge.fabler.database.operations.story.StoryDataOp;
import com.techradge.fabler.model.Story;
import com.techradge.fabler.utils.PrefManager;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComposeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_title)
    EditText titleEditor;
    @BindView(R.id.et_body)
    EditText storyEditor;

    private StoryDataOp storyDataOp;
    private PrefManager prefManager;
    private final String TAG = ComposeActivity.class.getSimpleName();

    public ComposeActivity() {
        storyDataOp = new StoryDataOp(Database.getFirebaseDatabase());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        ButterKnife.bind(this);
        setUpActionBar();

        prefManager = new PrefManager(this);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            if (!(titleEditor.getText().toString().isEmpty() &&
                    storyEditor.getText().toString().isEmpty()))
                saveStoryOffline(createStory());
            return true;
        } else if (id == R.id.action_publish) {
            if (!(titleEditor.getText().toString().isEmpty() &&
                    storyEditor.getText().toString().isEmpty()))
                storyDataOp.insertStoryData(createStory(), getApplicationContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Story createStory() {
        String title = titleEditor.getText().toString();
        String body = storyEditor.getText().toString();

        Story story = new Story();
        story.setTitle(title);
        story.setStory(body);
        story.setAuthor(prefManager.getUserFullName());
        story.setTime(Calendar.getInstance().getTime().toString());
        return story;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!(titleEditor.getText().toString().isEmpty() &&
                storyEditor.getText().toString().isEmpty()))
            saveStoryOffline(createStory());
    }

    private void saveStoryOffline(final Story story) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    StoryDatabase.getInstance(ComposeActivity.this)
                            .storyDao()
                            .insertStory(story);
                } catch (SQLiteConstraintException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }
}