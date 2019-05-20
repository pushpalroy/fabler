package com.techradge.fabler.ui.compose;

import android.Manifest;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.techradge.fabler.R;
import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.ui.base.BaseActivity;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import fabler.fablededitor.FabledEditor;
import fabler.fablededitor.FormattingBar;
import fabler.fablededitor.utilities.FilePathUtils;

import static fabler.fablededitor.styles.TextComponentStyle.NORMAL;

public class ComposeActivity extends BaseActivity implements ComposeContract.ComposeView,
        FormattingBar.EditorControlListener, FabledEditor.EditorCallback {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_title)
    EditText titleEditor;
    @BindView(R.id.iv_story_image)
    ImageView storyImageView;
    TextViewUndoRedo storyEditorHelper;
    @BindView(R.id.editor_fabled)
    FabledEditor mEditor;
    @BindView(R.id.format_bar)
    FormattingBar mFormatBar;

    @Inject
    public ComposePresenter<ComposeContract.ComposeView, ComposeInteractor> mPresenter;

    private final String TAG = ComposeActivity.class.getSimpleName();
    boolean mIsEdited;
    private final int IMAGE_PICKER_REQUEST_ID = 1;

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
            int storyId = extras.getInt(getString(R.string.key_story_id));

            titleEditor.setText(title);
            mIsEdited = isEdited;
        }
        //storyEditorHelper = new TextViewUndoRedo(storyEditor);
    }

    @Override
    protected void setUp() {
        mPresenter.setViewModel(ViewModelProviders.of(this).get(MainViewModel.class));
        setUpActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar())
                .setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setTitle("");

        mEditor.configureEditor(
                "",
                "",
                true,
                "Your story here",
                NORMAL,
                this
        );

        mEditor.startFreshEditor();
        mFormatBar.setEditorControlListener(this);
        mFormatBar.setEditor(mEditor);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
//            if (!(titleEditor.getText().toString().isEmpty() &&
//                    storyEditor.getText().toString().isEmpty())) {
//                if (mIsEdited)
//                    mPresenter.onModifyOptionSelected(createStoryForRoom());
//                else
//                    mPresenter.onSaveOptionSelected(createStoryForRoom());
//            }
            return true;
        } else if (id == R.id.action_publish) {
//            if (!(titleEditor.getText().toString().isEmpty() &&
//                    storyEditor.getText().toString().isEmpty())) {
//                new AlertDialog.Builder(this)
//                        .setMessage(R.string.dialog_publish_message)
//                        .setTitle(R.string.dialog_publish_title)
//                        .setPositiveButton(R.string.publish, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // Publish Story
//                                mPresenter.onPublishOptionSelected(createStoryForFirebase(),
//                                        createStoryDataForFirebase());
//                                dialog.dismiss();
//                                finish();
//                            }
//                        })
//                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.dismiss();
//                            }
//                        }).create().show();
//            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == IMAGE_PICKER_REQUEST_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                showMessage("Permission not granted to access images.");
            }
        }
    }

    public void openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, IMAGE_PICKER_REQUEST_ID);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_PICKER_REQUEST_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == IMAGE_PICKER_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = imageReturnedIntent.getData();
                mEditor.insertImage(FilePathUtils.getPath(this, imageUri));
            }
        }
    }

//    private Story createStoryForRoom() {
//        String title = titleEditor.getText().toString();
//        String body = storyEditor.getText().toString();
//
//        Story story = new Story();
//        story.setStoryTitle(title);
//        story.setStoryBody(body);
//        story.setAuthorName(mPresenter.getInteractor().getPreferencesHelper().getUserFullName());
//        story.setCreatedOn(Calendar.getInstance().getTimeInMillis());
//        return story;
//    }
//
//    private Story createStoryForFirebase() {
//        String title = titleEditor.getText().toString();
//        String brief = storyEditor.getText().toString();
//
//        if (brief.length() > 40)
//            brief = brief.substring(0, 40);
//
//        Story story = new Story();
//        // Firebase child key will be added in StoryId
//
//        story.setAuthorId(mPresenter.getInteractor().getPreferencesHelper().getFirebaseUid());
//        story.setAuthorName(mPresenter.getInteractor().getPreferencesHelper().getUserFullName());
//        story.setStoryTitle(title);
//        story.setStoryBrief(brief);
//        story.setCreatedOn(Calendar.getInstance().getTimeInMillis());
//        story.setPublishedOn(Calendar.getInstance().getTimeInMillis());
//
//        Map<String, String> contributors = new HashMap<>();
//        contributors.put(mPresenter.getInteractor().getPreferencesHelper().getFirebaseUid(),
//                mPresenter.getInteractor().getPreferencesHelper().getUserFullName());
//        story.setContributors(contributors);
//
//        return story;
//    }
//
//    private StoryData createStoryDataForFirebase() {
//        String body = storyEditor.getText().toString();
//
//        StoryData storyData = new StoryData();
//        storyData.setStoryBody(body);
//        return storyData;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (!(titleEditor.getText().toString().isEmpty() &&
//                storyEditor.getText().toString().isEmpty())) {
//
//            // Saving in local database
//            if (mIsEdited)
//                // Update existing
//                mPresenter.onModifyOptionSelected(createStoryForRoom());
//            else
//                // Insert new
//                mPresenter.onSaveOptionSelected(createStoryForRoom());
//        }
    }

    @Override
    public void showMessagePublished() {
    }

    @Override
    public void showMessageDraftSaved() {
        showMessage(getString(R.string.toast_message_draft));
    }

    @Override
    public void onInsertImageClicked() {
        openGallery();
    }

    @Override
    public void onInsertLinkClicked() {
        mEditor.addLink("Click Here", "https://www.fabler.com");
    }

    public void performUndo() {
        storyEditorHelper.undo();
    }

    public void performRedo() {
        storyEditorHelper.redo();
    }
}