package com.techradge.fabler.ui.compose;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.techradge.fabler.R;
import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.model.StoryData;
import com.techradge.fabler.ui.base.BaseActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComposeActivity extends BaseActivity implements ComposeContract.ComposeView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_title)
    EditText titleEditor;
    @BindView(R.id.et_body)
    EditText storyEditor;
    boolean mIsEdited;
    private int storyId;

    @Inject
    public ComposePresenter<ComposeContract.ComposeView, ComposeInteractor> mPresenter;

    private final String TAG = ComposeActivity.class.getSimpleName();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ComposeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ComposeActivity.this);

        setUp();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(getString(R.string.key_title));
            String story = extras.getString(getString(R.string.key_story));
            boolean isEdited = extras.getBoolean(getString(R.string.key_isEdited), false);
            storyId = extras.getInt(getString(R.string.key_story_id));

            titleEditor.setText(title);
            storyEditor.setText(story);
            mIsEdited = isEdited;
        }
    }

    @Override
    protected void setUp() {
        mPresenter.setViewModel(ViewModelProviders.of(this).get(MainViewModel.class));
        setUpActionBar(mToolbar);
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
                    storyEditor.getText().toString().isEmpty())) {
                if (mIsEdited)
                    mPresenter.onModifyOptionSelected(createStoryForRoom());
                else
                    mPresenter.onSaveOptionSelected(createStoryForRoom());
            }
            return true;
        } else if (id == R.id.action_publish) {
            if (!(titleEditor.getText().toString().isEmpty() &&
                    storyEditor.getText().toString().isEmpty())) {
                new AlertDialog.Builder(this)
                        .setMessage(R.string.dialog_publish_message)
                        .setTitle(R.string.dialog_publish_title)
                        .setPositiveButton(R.string.publish, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Publish Story
                                mPresenter.onPublishOptionSelected(createStoryForFirebase(),
                                        createStoryDataForFirebase());
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

    private Story createStoryForRoom() {
        String title = titleEditor.getText().toString();
        String body = storyEditor.getText().toString();

        Story story = new Story();
        story.setStoryTitle(title);
        story.setStoryBody(body);
        story.setAuthorName(mPresenter.getInteractor().getPreferencesHelper().getUserFullName());
        story.setCreatedOn(Calendar.getInstance().getTimeInMillis());
        return story;
    }

    private Story createStoryForFirebase() {
        String title = titleEditor.getText().toString();
        String brief = storyEditor.getText().toString();

        if (brief.length() > 40)
            brief = brief.substring(0, 40);

        Story story = new Story();
        // Firebase child key will be added in StoryId

        story.setAuthorId(mPresenter.getInteractor().getPreferencesHelper().getFirebaseUid());
        story.setAuthorName(mPresenter.getInteractor().getPreferencesHelper().getUserFullName());
        story.setStoryTitle(title);
        story.setStoryBrief(brief);
        story.setCreatedOn(Calendar.getInstance().getTimeInMillis());
        story.setPublishedOn(Calendar.getInstance().getTimeInMillis());

        Map<String, String> contributors = new HashMap<>();
        contributors.put(mPresenter.getInteractor().getPreferencesHelper().getFirebaseUid(),
                mPresenter.getInteractor().getPreferencesHelper().getUserFullName());
        story.setContributors(contributors);

        return story;
    }

    private StoryData createStoryDataForFirebase() {
        String body = storyEditor.getText().toString();

        StoryData storyData = new StoryData();
        storyData.setStoryBody(body);
        return storyData;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!(titleEditor.getText().toString().isEmpty() &&
                storyEditor.getText().toString().isEmpty())) {

            // Saving in local database
            if (mIsEdited)
                // Update existing
                mPresenter.onModifyOptionSelected(createStoryForRoom());
            else
                // Insert new
                mPresenter.onSaveOptionSelected(createStoryForRoom());
        }
    }

    @Override
    public void showMessagePublished() {

    }

    @Override
    public void showMessageDraftSaved() {
        showMessage(getString(R.string.toast_message_draft));
    }
}