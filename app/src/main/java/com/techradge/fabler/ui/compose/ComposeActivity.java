package com.techradge.fabler.ui.compose;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.prefs.AppPreferencesHelper;
import com.techradge.fabler.ui.base.BaseActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComposeActivity extends BaseActivity implements ComposeContract.ComposeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_title)
    EditText titleEditor;
    @BindView(R.id.et_body)
    EditText storyEditor;

    private final String TAG = ComposeActivity.class.getSimpleName();
    private ComposePresenter mPresenter;
    private AppPreferencesHelper appPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_compose);
        ButterKnife.bind(this);
        setUpActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(getString(R.string.key_title));
            String story = extras.getString(getString(R.string.key_story));

            titleEditor.setText(title);
            storyEditor.setText(story);
        }

        appPrefsManager = new AppPreferencesHelper(this);
        mPresenter = new ComposePresenter(this);
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
                mPresenter.onSaveOptionSelected(createStory());
            return true;
        } else if (id == R.id.action_publish) {
            if (!(titleEditor.getText().toString().isEmpty() &&
                    storyEditor.getText().toString().isEmpty())) {
                new AlertDialog.Builder(this)
                        .setMessage(R.string.dialog_publish_message)
                        .setTitle(R.string.dialog_publish_title)
                        .setPositiveButton(R.string.publish, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mPresenter.onPublishOptionSelected(createStory(), getApplicationContext());
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
        story.setAuthor(appPrefsManager.getUserFullName());
        story.setTime(Calendar.getInstance().getTime().toString());
        return story;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!(titleEditor.getText().toString().isEmpty() &&
                storyEditor.getText().toString().isEmpty())) {
            mPresenter.onSaveOptionSelected(createStory());
        }
    }

    @Override
    public void showDraftSavedMessage() {
        showToastMessage(getString(R.string.toast_message_draft), Toast.LENGTH_LONG);
    }

    @Override
    public void setPresenter(ComposeContract.ComposePresenter presenter) {

    }

    @Override
    public Context getContext() {
        return ComposeActivity.this;
    }
}