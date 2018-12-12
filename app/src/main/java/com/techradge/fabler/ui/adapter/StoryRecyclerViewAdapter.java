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

public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryRecyclerViewAdapter.StoryViewHolder> {

    protected Context context;
    private List<Story> storyList;

    public StoryRecyclerViewAdapter(List<Story> storyList, Context context) {
        this.context = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StoryViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        viewHolder = new StoryViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder storyViewHolder, int position) {

        Story story = storyList.get(position);

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
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    static class StoryViewHolder extends RecyclerView.ViewHolder {

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

        StoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}