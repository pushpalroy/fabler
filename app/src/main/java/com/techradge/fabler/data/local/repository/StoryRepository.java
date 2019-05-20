package com.techradge.fabler.data.local.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.techradge.fabler.data.local.appDatabase.StoryDatabase;
import com.techradge.fabler.data.local.dao.StoryDao;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.network.AppExecutors;
import com.techradge.fabler.di.ApplicationContext;
import com.techradge.fabler.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class StoryRepository {

    private StoryDao storyDao;

    // An Observer within ViewModel will monitor this LiveData
    private MutableLiveData<List<Story>> searchResults = new MutableLiveData<>();
    private MutableLiveData<Integer> insertionStatus = new MutableLiveData<>();
    private MutableLiveData<Integer> updatingStatus = new MutableLiveData<>();
    private MutableLiveData<Integer> deletionStatus = new MutableLiveData<>();

    @Inject
    public StoryRepository(@ApplicationContext Context context) {
        StoryDatabase storyDatabase = StoryDatabase.getInstance(context);
        storyDao = storyDatabase.storyDao();
    }

    public LiveData<List<Story>> getAllStories() {
        return storyDao.getStories();
    }

    @SuppressLint("CheckResult")
    public void insertStory(Story story) {
        Observable.fromCallable(() -> {
            try {
                storyDao.insertStory(story);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
                return AppConstants.RoomInsertion.INSERTION_STATUS_ERROR.getType();
            } catch (Exception e) {
                Timber.e(e);
                return AppConstants.RoomInsertion.INSERTION_STATUS_ERROR.getType();
            }

            return AppConstants.RoomInsertion.INSERTION_STATUS_INSERTED.getType();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    insertionStatus.setValue(result);
                });
    }

    @SuppressLint("CheckResult")
    public void updateStory(Story story) {
        Observable.fromCallable(() -> {
            try {
                storyDao.updateStory(story);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
                return AppConstants.RoomUpdating.UPDATING_STATUS_ERROR.getType();
            } catch (Exception e) {
                Timber.e(e);
                return AppConstants.RoomUpdating.UPDATING_STATUS_ERROR.getType();
            }

            return AppConstants.RoomUpdating.UPDATING_STATUS_UPDATED.getType();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> updatingStatus.setValue(result));
    }

    @SuppressLint("CheckResult")
    public void deleteStory(final Story story) {
        Observable.fromCallable(() -> {
            try {
                storyDao.deleteStory(story);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
                return AppConstants.RoomDeletion.DELETION_STATUS_ERROR.getType();
            } catch (Exception e) {
                Timber.e(e);
                return AppConstants.RoomDeletion.DELETION_STATUS_ERROR.getType();
            }

            return AppConstants.RoomDeletion.DELETION_STATUS_DELETED.getType();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    deletionStatus.setValue(result);
                });
    }

    @SuppressLint("CheckResult")
    public void getStoriesByTitle(String title) {
        Observable.fromCallable(() -> {
            return storyDao.getStoryByTitle(title);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::storyByTitleAsyncFinished);
    }

    private void storyByTitleAsyncFinished(List<Story> results) {
    }

    public MutableLiveData<List<Story>> getStoriesByTitleSearchResults() {
        return searchResults;
    }

    public MutableLiveData<Integer> getInsertionStatus() {
        return insertionStatus;
    }

    public MutableLiveData<Integer> getUpdatingStatus() {
        return updatingStatus;
    }

    public MutableLiveData<Integer> getDeletionStatus() {
        return deletionStatus;
    }

    public void deleteStory2(final Story story) {
        // Using Executors instead of AsyncTask
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    storyDao.deleteStory(story);
                } catch (SQLiteConstraintException e) {
                    Timber.e(e);
                } catch (Exception e) {
                    Timber.e(e);
                }
            }
        });
    }
}
