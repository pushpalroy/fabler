package com.techradge.fabler.ui.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.techradge.fabler.R;
import com.techradge.fabler.database.firebase.Database;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        ButterKnife.bind(this);
        setUpActionBar();

        storyDataOp = new StoryDataOp(Database.getFirebaseDatabase(), ComposeActivity.this);
        prefManager = new PrefManager(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(getString(R.string.key_title));
            String story = extras.getString(getString(R.string.key_story));

            titleEditor.setText(title);
            storyEditor.setText(story);
        }
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
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
                    storyEditor.getText().toString().isEmpty())) {
                new AlertDialog.Builder(this)
                        .setMessage(R.string.dialog_publish_message)
                        .setTitle(R.string.dialog_publish_title)
                        .setPositiveButton(R.string.publish, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                storyDataOp.insertStoryData(createStory(), getApplicationContext());
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
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
                storyEditor.getText().toString().isEmpty())) {
            saveStoryOffline(createStory());
        }
    }

    private void saveStoryOffline(final Story story) {
        new SaveStory().execute(story);
    }

    @SuppressLint("StaticFieldLeak")
    private class SaveStory extends AsyncTask<Story, Void, Void> {

        @Override
        protected Void doInBackground(Story... params) {
            try {
                StoryDatabase.getInstance(ComposeActivity.this)
                        .storyDao()
                        .insertStory(params[0]);
            } catch (SQLiteConstraintException e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_message_draft), Toast.LENGTH_LONG).show();
        }
    }
}