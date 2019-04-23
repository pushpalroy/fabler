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
import com.techradge.fabler.ui.feedback.FeedbackActivity;
import com.techradge.fabler.utils.CommonUtils;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Story> mStoryList;
    private StoryClickListener mStoryClickListener;

    public StoryAdapter(List<Story> storyList) {
        this.mStoryList = storyList;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(layoutView);
    }

    public void setCallback(StoryClickListener storyClickListener) {
        mStoryClickListener = storyClickListener;
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

    public void addItems(List<Story> stories) {
        mStoryList.addAll(stories);
        notifyDataSetChanged();
    }

    public void addItem(Story story) {
        mStoryList.add(0, story);
        notifyDataSetChanged();
    }

    public void flushAndAddItems(List<Story> stories) {
        mStoryList.clear();
        Collections.reverse(stories);
        mStoryList.addAll(stories);
        notifyDataSetChanged();
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

            if (story.getStoryTitle() != null)
                titleTv.setText(story.getStoryTitle());
            if (story.getPublishedOn() != 0)
                timeStampTv.setText(CommonUtils.getFormattedDateTime(story.getPublishedOn()));
            if (story.getAuthorName() != null)
                authorTv.setText(story.getAuthorName());
            if (story.getStoryBrief() != null)
                storyTv.setText(story.getStoryBrief());

            likesTv.setText(String.valueOf(story.getTotalLikes()));
            commentsTv.setText(String.valueOf(story.getTotalFeedbacks()));

            itemView.setOnClickListener(v -> mStoryClickListener.onStoryClick(position, story));

            likeButton.setOnCheckStateChangeListener((view, checked) -> {
                if (checked) {
                    StoryFireOp storyFireOp = new StoryFireOp(RemoteFireDatabase.getFirebaseDatabase(), itemView.getContext());
                    storyFireOp.postLikeUpdateStory(story);
                }
            });

            commentButton.setOnClickListener(v -> {
                Intent commentIntent = new Intent(itemView.getContext(), FeedbackActivity.class);
                commentIntent.putExtra(itemView.getContext().getString(R.string.key_story_id), story.getStoryId());
                itemView.getContext().startActivity(commentIntent);
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