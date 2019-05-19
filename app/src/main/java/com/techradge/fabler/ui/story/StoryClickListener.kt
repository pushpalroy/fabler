package com.techradge.fabler.ui.story

import com.techradge.fabler.data.model.Story

interface StoryClickListener {
    fun onStoryClick(position: Int, story: Story)

    fun onBookmarked(position: Int, story: Story)

    fun onBookmarkRemoved(position: Int, story: Story)
}