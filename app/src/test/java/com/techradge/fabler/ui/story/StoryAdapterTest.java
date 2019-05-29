package com.techradge.fabler.ui.story;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.techradge.fabler.data.model.Story;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Collections;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
public class StoryAdapterTest {

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void addStoryObjects_CheckSize() {
        ArrayList<Story> stories = new ArrayList<>();
        stories.add(new Story());
        stories.add(new Story());
        stories.add(new Story());

        StoryAdapter storyAdapter = new StoryAdapter(stories, context);
        assertThat(storyAdapter.getItemCount()).isEqualTo(3);
    }

    @Test
    public void addStoryObjects_CheckLastStoryId() {
        ArrayList<Story> stories = new ArrayList<>();
        stories.add(new Story("0", "1", "John"));
        stories.add(new Story("1", "2", "Dan"));
        stories.add(new Story("2", "3", "Paul"));
        stories.add(new Story("3", "4", "Tom"));
        stories.add(new Story("4", "5", "Marx"));

        StoryAdapter storyAdapter = new StoryAdapter(stories, context);
        assertThat(storyAdapter.getLastStoryId()).isEqualTo("4");
    }

    @Test
    public void addModifyStoryObjects_CheckLastStoryId() {
        ArrayList<Story> stories = new ArrayList<>();
        stories.add(new Story("afafasfaf", "424", "John"));
        stories.add(new Story("dar2rADFf", "512", "Dan"));
        stories.add(new Story("fasfafaff", "252", "Paul"));
        stories.add(new Story("4reafwtfa", "533", "Tom"));
        stories.add(new Story("e124ed2qg", "134", "Marx"));

        StoryAdapter storyAdapter = new StoryAdapter(stories, context);

        ArrayList<Story> newStories = new ArrayList<>();
        newStories.add(new Story("bdgbeaad", "987", "Push"));
        newStories.add(new Story("adafafaf", "258", "Call"));
        newStories.add(new Story("dswagfas", "865", "Pull"));
        newStories.add(new Story("bdsgdafh", "743", "Put"));
        newStories.add(new Story("gdgsdsej", "964", "Dig"));

        // Items will be fetched in reverse order from Firebase
        Collections.reverse(newStories);
        storyAdapter.addItems(newStories);

        assertThat(storyAdapter.getLastStoryId()).isEqualTo("gdgsdsej");

        storyAdapter.addSingleItem(new Story("jregstha", "098", "Deep"));

        assertThat(storyAdapter.getLastStoryId()).isEqualTo("jregstha");
    }
}
