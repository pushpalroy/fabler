package com.techradge.fabler.ui.story;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.BaseViewHolder;
import com.techradge.fabler.utils.CommonUtils;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;

    private List<Story> mStoryList;
    private Context mContext;
    private StoryClickListener mStoryClickListener;

    public StoryAdapter(List<Story> storyList, Context context) {
        this.mStoryList = storyList;
        mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new StoryViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_story_feed, parent, false));
            default:
                return null;
        }
    }

    public void setCallback(StoryClickListener storyClickListener) {
        mStoryClickListener = storyClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void addItems(List<Story> stories) {
        if (stories.size() > 0) {
            int insertIndex = mStoryList.size();
            Collections.reverse(stories);
            mStoryList.addAll(stories);

            if (insertIndex == 0)
                notifyDataSetChanged();
            else
                notifyItemRangeInserted(insertIndex, stories.size());
        }
    }

    public void addSingleItem(Story story) {
        mStoryList.add(story);
        notifyDataSetChanged();
    }

    public String getLastStoryId() {
        return mStoryList.get(mStoryList.size() - 1).getStoryId();
    }

    @Override
    public int getItemCount() {
        return mStoryList == null ? 0 : mStoryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        mStoryList.clear();
    }

    class StoryViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_time)
        TextView timeStampTv;
        @BindView(R.id.tv_title)
        TextView titleTv;
        @BindView(R.id.tv_author)
        TextView authorTv;
        @BindView(R.id.tv_story_brief)
        TextView storyBriefTv;
        @BindView(R.id.tv_category)
        TextView categoryTv;
        @BindView(R.id.tv_likes_count)
        TextView likesCountTv;
        @BindView(R.id.tv_comments_count)
        TextView commentsCountTv;
        @BindView(R.id.tv_dot_1)
        TextView dotTv01;
        @BindView(R.id.tv_dot_2)
        TextView dotTv02;
        @BindView(R.id.btn_bookmark)
        LikeButton bookmarkBtn;
        @BindView(R.id.iv_story_thumbnail)
        ImageView storyThumbnailIv;

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

            if (story.getAuthorName() != null)
                authorTv.setText(story.getAuthorName());

            if (story.getStoryBrief() != null)
                storyBriefTv.setText(story.getStoryBrief());

            if (story.getCategory() != null)
                categoryTv.setText(story.getCategory());

            if (story.getPublishedOn() != 0)
                timeStampTv.setText(CommonUtils.getFormattedRelativeDateTime(story.getPublishedOn()));

            if (story.getPhotoUrl() != null && !story.getPhotoUrl().equals(""))
                Glide.with(mContext)
                        .load(story.getPhotoUrl())
                        .centerCrop()
                        .into(storyThumbnailIv);

            if (story.getTotalLikes() > 0) {
                String likes = story.getTotalLikes() + " likes";
                likesCountTv.setText(likes);
            } else {
                dotTv01.setVisibility(View.GONE);
                likesCountTv.setVisibility(View.GONE);
            }

            if (story.getTotalComments() > 0) {
                String comments = story.getTotalComments() + " comments";
                commentsCountTv.setText(comments);
            } else {
                dotTv02.setVisibility(View.GONE);
                commentsCountTv.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> mStoryClickListener.onStoryClick(position, story));

            bookmarkBtn.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                }

                @Override
                public void unLiked(LikeButton likeButton) {

                }
            });
        }

        @Override
        protected void clear() {
            titleTv.setText("");
            timeStampTv.setText("");
            authorTv.setText("");
            storyBriefTv.setText("");
            likesCountTv.setText("");
            commentsCountTv.setText("");
            dotTv01.setVisibility(View.VISIBLE);
            likesCountTv.setVisibility(View.VISIBLE);
            dotTv02.setVisibility(View.VISIBLE);
            commentsCountTv.setVisibility(View.VISIBLE);
        }
    }

    public class LoaderViewHolder extends BaseViewHolder {

        @BindView(R.id.pb_loader)
        ProgressBar mProgressBar;

        LoaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
        }

        @Override
        protected void clear() {

        }
    }
}