package com.techradge.fabler.ui.read;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.techradge.fabler.R;
import com.techradge.fabler.ui.base.BaseActivity;
import com.techradge.fabler.widget.StoryWidgetProvider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadActivity extends BaseActivity implements ReadContract.ReadView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView titleTv;
    @BindView(R.id.tv_body)
    TextView bodyTv;
    @BindView(R.id.tv_time)
    TextView timeTv;
    @BindView(R.id.tv_author)
    TextView authorTv;
    public static final String TAG = ReadActivity.class.getSimpleName();
    @Inject
    public ReadPresenter<ReadContract.ReadView, ReadContract.ReadInteractor> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        setUp();

        if (getIntent().getExtras() != null) {
            String title = getIntent().getExtras().getString(getString(R.string.key_title));
            String story = getIntent().getExtras().getString(getString(R.string.key_story));
            String author = getIntent().getExtras().getString(getString(R.string.key_author));
            String time = getIntent().getExtras().getString(getString(R.string.key_time));

            populateStoryData(title, story, author, time);
        }
    }

    @Override
    protected void setUp() {
        mPresenter.onAttach(ReadActivity.this);
        setUpActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public void populateStoryData(String... story) {
        titleTv.setText(story[0]);
        bodyTv.setText(story[1]);
        authorTv.setText(story[2]);
        timeTv.setText(story[3]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_to_widget) {
            mPresenter.addStoryToWidget(titleTv.getText().toString(),
                    bodyTv.getText().toString(),
                    authorTv.getText().toString());
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

    @Override
    public void sendUpdateWidgetBroadcast() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ReadActivity.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, StoryWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_widget_story);
        StoryWidgetProvider.updateWidget(ReadActivity.this, appWidgetManager, appWidgetIds);
    }

    @Override
    public void showWidgetAddedMessage() {
        Toast.makeText(ReadActivity.this, getString(R.string.toast_message_widget), Toast.LENGTH_LONG).show();
    }
}
