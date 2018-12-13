package com.techradge.fabler.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techradge.fabler.R;
import com.techradge.fabler.model.Story;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftsRecyclerViewAdapter extends RecyclerView.Adapter<DraftsRecyclerViewAdapter.DraftsViewHolder> {

    protected Context context;
    private List<Story> draftsList;
    private StoryClickListener storyClickListener;

    public DraftsRecyclerViewAdapter(List<Story> draftsList, Context context, StoryClickListener storyClickListener) {
        this.context = context;
        this.draftsList = draftsList;
        this.storyClickListener = storyClickListener;
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
        if (story.getAuthor() != null)
            storyViewHolder.author.setText(story.getAuthor());
        if (story.getStory() != null)
            storyViewHolder.story.setText(story.getStory());
        if (story.getLikes() != null)
            storyViewHolder.likes.setText(story.getLikes());
        else
            storyViewHolder.likes.setText("0");
        if (story.getComments() != null)
            storyViewHolder.comments.setText(story.getComments());
        else
            storyViewHolder.comments.setText("0");

        storyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyClickListener.onStoryClick(position, story);
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
        @BindView(R.id.tv_author)
        TextView author;
        @BindView(R.id.tv_story)
        TextView story;
        @BindView(R.id.tv_likes)
        TextView likes;
        @BindView(R.id.tv_comments)
        TextView comments;

        DraftsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}