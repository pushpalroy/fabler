package com.techradge.fabler.ui.draft;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techradge.fabler.R;
import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.story.StoryClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.DraftsViewHolder> {

    protected Context context;
    private List<Story> draftsList;
    private StoryClickListener storyClickListener;
    private final String TAG = DraftAdapter.class.getSimpleName();
    private MainViewModel mainViewModel;

    DraftAdapter(List<Story> draftsList, Context context, MainViewModel mainViewModel, StoryClickListener storyClickListener) {
        this.context = context;
        this.draftsList = draftsList;
        this.storyClickListener = storyClickListener;
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public DraftsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DraftsViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_draft, parent, false);
        viewHolder = new DraftsViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DraftsViewHolder storyViewHolder, final int position) {

        final Story story = draftsList.get(position);

        if (story.getTitle() != null)
            storyViewHolder.title.setText(story.getTitle());
        if (story.getTime() != null)
            storyViewHolder.timeStamp.setText(story.getTime());
        if (story.getStory() != null)
            storyViewHolder.story.setText(story.getStory());

        storyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyClickListener.onStoryClick(position, story);
            }
        });

        storyViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage(R.string.dialog_delete_message)
                        .setTitle(R.string.dialog_delete_title)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mainViewModel.deleteStory(story);
                                mainViewModel.getDeletionStatus().observe((LifecycleOwner) context, new Observer<Integer>() {
                                    @Override
                                    public void onChanged(@Nullable Integer integer) {
                                        if (integer != null)
                                            Timber.e("InsertionStatus: %s", integer);
                                    }
                                });
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return draftsList.size();
    }

    static class DraftsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time)
        TextView timeStamp;
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_story)
        TextView story;
        @BindView(R.id.icon_delete)
        ImageView deleteButton;

        DraftsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}