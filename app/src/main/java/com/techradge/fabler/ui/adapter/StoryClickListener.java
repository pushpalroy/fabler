package com.techradge.fabler.ui.adapter;

import com.techradge.fabler.data.model.Story;

public interface StoryClickListener {
    void onStoryClick(int position, Story story);
}