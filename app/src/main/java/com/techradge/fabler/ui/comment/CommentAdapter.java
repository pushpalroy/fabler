package com.techradge.fabler.ui.comment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Comment;
import com.techradge.fabler.ui.base.BaseViewHolder;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Comment> mCommentList;

    public CommentAdapter(List<Comment> commentList) {
        this.mCommentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public void addItems(List<Comment> comments) {
        mCommentList.addAll(comments);
        notifyDataSetChanged();
    }

    void flushAndAddItems(List<Comment> comments) {
        mCommentList.clear();
        Collections.reverse(comments);
        mCommentList.addAll(comments);
        notifyDataSetChanged();
    }

    class CommentViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_comment)
        TextView commentTv;
        @BindView(R.id.tv_author)
        TextView authorTv;

        CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            final Comment comment = mCommentList.get(position);

            if (comment.getComment() != null)
                commentTv.setText(comment.getComment());
            if (comment.getAuthor() != null)
                authorTv.setText(comment.getAuthor());
        }

        @Override
        protected void clear() {
            commentTv.setText("");
            authorTv.setText("");
        }
    }
}