package com.techradge.fabler.ui.draft;

import com.techradge.fabler.data.model.Story;

public interface DraftModificationListener {
    void onStoryDeleted(int position, Story story);
}