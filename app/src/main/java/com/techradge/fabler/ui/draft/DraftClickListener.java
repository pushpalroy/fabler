package com.techradge.fabler.ui.draft;

import com.techradge.fabler.data.model.Story;

public interface DraftClickListener {
    void onDraftDeleted(int position, Story story);

    void onDraftClick(int position, Story story);
}