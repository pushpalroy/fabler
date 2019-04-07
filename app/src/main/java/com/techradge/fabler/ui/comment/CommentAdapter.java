package com.techradge.fabler.ui.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    protected Context context;
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        viewHolder = new CommentViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, final int position) {

        final Comment comment = commentList.get(position);

        if (comment.getComment() != null)
            commentViewHolder.comment.setText(comment.getComment());
        if (comment.getAuthor() != null)
            commentViewHolder.author.setText(comment.getAuthor());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_comment)
        TextView comment;
        @BindView(R.id.tv_author)
        TextView author;

        CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}