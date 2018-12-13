package com.techradge.fabler.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sackcentury.shinebuttonlib.ShineButton;
import com.techradge.fabler.R;
import com.techradge.fabler.database.firebase.Database;
import com.techradge.fabler.database.operations.story.StoryDataOp;
import com.techradge.fabler.model.Story;
import com.techradge.fabler.ui.activity.CommentActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryRecyclerViewAdapter.StoryViewHolder> {

    protected Context context;
    private List<Story> storyList;
    private StoryClickListener storyClickListener;

    public StoryRecyclerViewAdapter(List<Story> storyList, Context context, StoryClickListener storyClickListener) {
        this.context = context;
        this.storyList = storyList;
        this.storyClickListener = storyClickListener;
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
    public void onBindViewHolder(@NonNull StoryViewHolder storyViewHolder, final int position) {

        final Story story = storyList.get(position);

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

        storyViewHolder.likeButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                StoryDataOp storyDataOp = new StoryDataOp(Database.getFirebaseDatabase());
                storyDataOp.postLikeUpdateStory(story);
            }
        });

        storyViewHolder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(context, CommentActivity.class);
                commentIntent.putExtra("storyId", story.getStoryId());
                context.startActivity(commentIntent);
            }
        });
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
        @BindView(R.id.btn_like)
        ShineButton likeButton;
        @BindView(R.id.icon_comment)
        ImageView commentButton;

        StoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}