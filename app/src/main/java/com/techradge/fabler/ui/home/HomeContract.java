package com.techradge.fabler.ui.home;

import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

import java.util.List;

public interface HomeContract {

    interface HomeView extends MvpView {
        void showStories(List<Story> storyList);

        void showStory(Story story);

        void openReadActivity(Story story);

        void showRefreshLoader();

        void hideRefreshLoader();
    }

    interface HomePresenter<V extends HomeView, I extends HomeInteractor>
            extends MvpPresenter<V, I> {

        void fetchStories(String story);

        void fetchMoreStories(String previousStoryId);

        void onStoriesPrepare();

        void onStoriesFetched(List<Story> storyList);

        void onSingleStoryFetched(Story story);
    }

    interface HomeInteractor extends MvpInteractor {

        void setUpFirebaseDatabase(String story);

        void fetchStoriesFromFirebase();

        void fetchMoreStoriesFromFirebase(String previousStoryId);

        void setPresenter(HomePresenter homePresenter);

        void removeListeners();
    }
}
