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
import com.techradge.fabler.ui.base.BaseViewHolder;
import com.techradge.fabler.ui.story.StoryClickListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DraftAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<Story> mDraftList;
    private StoryClickListener mStoryClickListener;
    private final String TAG = DraftAdapter.class.getSimpleName();
    private MainViewModel mMainViewModel;

    public DraftAdapter(List<Story> draftsList, Context context) {
        this.mContext = context;
        this.mDraftList = draftsList;
    }

    @NonNull
    @Override
    public DraftsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_draft, parent, false);
        return new DraftsViewHolder(layoutView);
    }

    void setCallback(StoryClickListener storyClickListener) {
        mStoryClickListener = storyClickListener;
    }

    void setMainViewModel(MainViewModel mainViewModel) {
        mMainViewModel = mainViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mDraftList.size();
    }

    public void addItems(List<Story> drafts) {
        mDraftList.addAll(drafts);
        notifyDataSetChanged();
    }

    void flushAndAddItems(List<Story> drafts) {
        mDraftList.clear();
        Collections.reverse(drafts);
        mDraftList.addAll(drafts);
        notifyDataSetChanged();
    }

    class DraftsViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_time)
        TextView timeStampTv;
        @BindView(R.id.tv_title)
        TextView titleTv;
        @BindView(R.id.tv_story)
        TextView storyTv;
        @BindView(R.id.icon_delete)
        ImageView deleteButton;

        DraftsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);

            final Story story = mDraftList.get(position);

            if (story.getTitle() != null)
                titleTv.setText(story.getTitle());
            if (story.getTime() != null)
                timeStampTv.setText(story.getTime());
            if (story.getStory() != null)
                storyTv.setText(story.getStory());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStoryClickListener.onStoryClick(position, story);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setMessage(R.string.dialog_delete_message)
                            .setTitle(R.string.dialog_delete_title)
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mMainViewModel.deleteStory(story);
                                    mMainViewModel.getDeletionStatus().observe((LifecycleOwner) mContext, new Observer<Integer>() {
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
        protected void clear() {
            titleTv.setText("");
            timeStampTv.setText("");
            storyTv.setText("");
        }
    }
}