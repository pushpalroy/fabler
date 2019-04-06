package com.techradge.fabler.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.techradge.fabler.R;
import com.techradge.fabler.ui.main.MainActivity;
import com.techradge.fabler.data.prefs.AppPrefsManager;

public class StoryWidgetProvider extends AppWidgetProvider {

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_story);
            Intent intent = new Intent(context, StoryWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            AppPrefsManager appPrefsManager = new AppPrefsManager(context);
            remoteViews.setTextViewText(R.id.tv_widget_title, appPrefsManager.getWidgetStoryTitle());
            remoteViews.setTextViewText(R.id.tv_widget_author, "By " + appPrefsManager.getWidgetStoryAuthor());
            remoteViews.setRemoteAdapter(R.id.lv_widget_story, intent);
            remoteViews.setEmptyView(R.id.lv_widget_story, R.id.tv_widget_story);

            // Set pending intent for onClick event on widget
            Intent storyIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, storyIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.tv_widget_title, pendingIntent);

            // Calling update app widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        updateWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
