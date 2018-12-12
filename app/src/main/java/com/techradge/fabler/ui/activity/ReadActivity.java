package com.techradge.fabler.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.techradge.fabler.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        ButterKnife.bind(this);
        setUpActionBar();

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
}
