package com.techradge.fabler.ui.story;

import com.techradge.fabler.data.model.Story;

public interface StoryClickListener {
    void onStoryClick(int position, Story story);

    void onBookmarked(int position, Story story);

    void onBookmarkRemoved(int position, Story story);
}