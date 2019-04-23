package com.techradge.fabler.ui.feedback;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Feedback;
import com.techradge.fabler.ui.base.BaseViewHolder;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Feedback> mFeedbackList;

    public FeedbackAdapter(List<Feedback> feedbackList) {
        this.mFeedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mFeedbackList.size();
    }

    public void addItems(List<Feedback> feedbacks) {
        mFeedbackList.addAll(feedbacks);
        notifyDataSetChanged();
    }

    void flushAndAddItems(List<Feedback> feedbacks) {
        mFeedbackList.clear();
        Collections.reverse(feedbacks);
        mFeedbackList.addAll(feedbacks);
        notifyDataSetChanged();
    }

    class FeedbackViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_feedback)
        TextView feedbackTv;
        @BindView(R.id.tv_author)
        TextView authorTv;

        FeedbackViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            final Feedback feedback = mFeedbackList.get(position);

            if (feedback.getFeedbackBody() != null)
                feedbackTv.setText(feedback.getFeedbackBody());
            if (feedback.getAuthorName() != null)
                authorTv.setText(feedback.getAuthorName());
        }

        @Override
        protected void clear() {
            feedbackTv.setText("");
            authorTv.setText("");
        }
    }
}