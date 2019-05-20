package com.techradge.fabler.ui.draft;

import androidx.lifecycle.LifecycleOwner;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techradge.fabler.R;
import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.BaseViewHolder;
import com.techradge.fabler.utils.AppConstants;
import com.techradge.fabler.utils.CommonUtils;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DraftAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<Story> mDraftList;
    private DraftClickListener mDraftClickListener;
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

    void setCallback(DraftClickListener draftClickListener) {
        mDraftClickListener = draftClickListener;
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

            if (story.getStoryTitle() != null)
                titleTv.setText(story.getStoryTitle());
            timeStampTv.setText(CommonUtils.getFormattedDateTime(story.getCreatedOn(), mContext));
            if (story.getStoryBrief() != null)
                storyTv.setText(story.getStoryBrief());

            itemView.setOnClickListener(v -> mDraftClickListener.onDraftClick(position, story));

            deleteButton.setOnClickListener(v -> new AlertDialog.Builder(mContext)
                    .setMessage(R.string.dialog_delete_message)
                    .setTitle(R.string.dialog_delete_title)
                    .setPositiveButton(R.string.delete, (dialog, id) -> {
                        mMainViewModel.deleteStory(story);
                        mMainViewModel.getDeletionStatus()
                                .observe((LifecycleOwner) mContext, integer -> {
                                    if (integer != null &&
                                            integer == AppConstants.RoomDeletion
                                                    .DELETION_STATUS_DELETED.getType())
                                        mDraftClickListener.onDraftDeleted(position, story);
                                });
                    })
                    .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss()).create().show());
        }

        @Override
        protected void clear() {
            titleTv.setText("");
            timeStampTv.setText("");
            storyTv.setText("");
        }
    }
}