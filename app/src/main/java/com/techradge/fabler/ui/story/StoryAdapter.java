package com.techradge.fabler.ui.story;

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
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.remote.RemoteFireDatabase;
import com.techradge.fabler.data.remote.operations.story.StoryFireOp;
import com.techradge.fabler.ui.base.BaseViewHolder;
import com.techradge.fabler.ui.comment.CommentActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Story> mStoryList;
    private StoryClickListener storyClickListener;

    public StoryAdapter(List<Story> storyList, StoryClickListener storyClickListener) {
        this.mStoryList = storyList;
        this.storyClickListener = storyClickListener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mStoryList.size();
    }

    class StoryViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_time)
        TextView timeStampTv;
        @BindView(R.id.tv_title)
        TextView titleTv;
        @BindView(R.id.tv_author)
        TextView authorTv;
        @BindView(R.id.tv_story)
        TextView storyTv;
        @BindView(R.id.tv_likes)
        TextView likesTv;
        @BindView(R.id.tv_comments)
        TextView commentsTv;
        @BindView(R.id.btn_like)
        ShineButton likeButton;
        @BindView(R.id.icon_comment)
        ImageView commentButton;

        StoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);

            final Story story = mStoryList.get(position);

            if (story.getTitle() != null)
                titleTv.setText(story.getTitle());
            if (story.getTime() != null)
                timeStampTv.setText(story.getTime());
            if (story.getAuthor() != null)
                authorTv.setText(story.getAuthor());
            if (story.getStory() != null)
                storyTv.setText(story.getStory());
            if (story.getLikes() != null)
                likesTv.setText(story.getLikes());
            else
                likesTv.setText(itemView.getContext().getString(R.string.zero));
            if (story.getComments() != null)
                commentsTv.setText(story.getComments());
            else
                commentsTv.setText(itemView.getContext().getString(R.string.zero));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storyClickListener.onStoryClick(position, story);
                }
            });

            likeButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean checked) {
                    if (checked) {
                        StoryFireOp storyFireOp = new StoryFireOp(RemoteFireDatabase.getFirebaseDatabase(), itemView.getContext());
                        storyFireOp.postLikeUpdateStory(story);
                    }
                }
            });

            commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent commentIntent = new Intent(itemView.getContext(), CommentActivity.class);
                    commentIntent.putExtra(itemView.getContext().getString(R.string.key_story_id), story.getStoryId());
                    commentIntent.putExtra(itemView.getContext().getString(R.string.key_comments), story.getComments());
                    itemView.getContext().startActivity(commentIntent);
                }
            });
        }

        @Override
        protected void clear() {
            titleTv.setText("");
            timeStampTv.setText("");
            authorTv.setText("");
            storyTv.setText("");
            likesTv.setText("");
            commentsTv.setText("");
        }
    }
}