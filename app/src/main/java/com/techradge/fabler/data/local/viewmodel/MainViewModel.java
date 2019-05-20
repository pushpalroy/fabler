package com.techradge.fabler.data.local.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.techradge.fabler.data.local.repository.StoryRepository;
import com.techradge.fabler.data.local.repository.UserRepository;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.model.User;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private StoryRepository storyRepository;
    private UserRepository userRepository;

    private LiveData<List<Story>> allStories;
    private MutableLiveData<List<Story>> storiesByTitleSearchResults;
    private LiveData<List<User>> users;

    public MainViewModel(Application application) {
        super(application);

        storyRepository = new StoryRepository(application);
        userRepository = new UserRepository(application);

        allStories = storyRepository.getAllStories();
        storiesByTitleSearchResults = storyRepository.getStoriesByTitleSearchResults();

        users = userRepository.getUser();
    }


    /**
     * Stories
     */
    public LiveData<List<Story>> getAllStories() {
        return allStories;
    }

    public MutableLiveData<List<Story>> getStoriesByTitleSearchResults() {
        return storiesByTitleSearchResults;
    }

    public void insertStory(Story story) {
        storyRepository.insertStory(story);
    }

    public void updateStory(Story story) {
        storyRepository.updateStory(story);
    }

    public void deleteStory(Story story) {
        storyRepository.deleteStory(story);
    }

    public MutableLiveData<Integer> getInsertionStatus() {
        return storyRepository.getInsertionStatus();
    }

    public MutableLiveData<Integer> getDeletionStatus() {
        return storyRepository.getDeletionStatus();
    }

    public MutableLiveData<Integer> getUpdatingStatus() {
        return storyRepository.getUpdatingStatus();
    }

    /**
     * Users
     */

    public LiveData<List<User>> getUser() {
        return userRepository.getUser();
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public MutableLiveData<Boolean> getUserInsertionStatus() {
        return userRepository.getUserInsertionStatus();
    }

    public MutableLiveData<Boolean> getUserUpdatingStatus() {
        return userRepository.getUserUpdatingStatus();
    }

    public MutableLiveData<Boolean> getUserDeletionStatus() {
        return userRepository.getUserDeletionStatus();
    }
}