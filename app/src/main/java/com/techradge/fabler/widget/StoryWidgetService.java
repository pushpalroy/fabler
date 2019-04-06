package com.techradge.fabler.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.techradge.fabler.R;
import com.techradge.fabler.data.prefs.AppPrefsManager;

import java.util.ArrayList;

public class StoryWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new StoryRemoteViewsFactory(this.getApplicationContext()));
    }
}

class StoryRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final String TAG = StoryRemoteViewsFactory.class.getSimpleName();
    private ArrayList<ListItem> listItemList = new ArrayList<>();
    private Context context;

    StoryRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onDataSetChanged() {
        populateStory();
    }

    private void populateStory() {
        listItemList.clear();
        AppPrefsManager appPrefsManager = new AppPrefsManager(context);
        ListItem listItem = new ListItem();
        listItem.storyBody = appPrefsManager.getWidgetStoryBody();
        listItemList.add(listItem);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.story_row);
        ListItem listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.tv_story_item, listItem.storyBody);
        return remoteView;
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public void onCreate() {
        /* In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        for example downloading or creating content etc, should be deferred to onDataSetChanged()
        or getViewAt(). Taking more than 20 seconds in this call will result in an ANR. */
    }

    @Override
    public void onDestroy() {
    }
}