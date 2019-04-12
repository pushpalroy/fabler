package com.techradge.fabler.data.local.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import com.techradge.fabler.data.local.appDatabase.StoryDatabase;
import com.techradge.fabler.data.local.dao.StoryDao;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.network.AppExecutors;
import com.techradge.fabler.di.ApplicationContext;
import com.techradge.fabler.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    public void insertStory(Story story) {
        InsertStoryAsyncTask insertStoryAsyncTask = new InsertStoryAsyncTask(storyDao);
        insertStoryAsyncTask.delegate = this;
        insertStoryAsyncTask.execute(story);
    }

    public void updateStory(Story story) {
        UpdateStoryAsyncTask updateStoryAsyncTask = new UpdateStoryAsyncTask(storyDao);
        updateStoryAsyncTask.delegate = this;
        updateStoryAsyncTask.execute(story);
    }

    public void deleteStory(final Story story) {
        DeleteStoryAsyncTask deleteStoryAsyncTask = new DeleteStoryAsyncTask(storyDao);
        deleteStoryAsyncTask.delegate = this;
        deleteStoryAsyncTask.execute(story);
    }

    public void getStoriesByTitle(String title) {
        QueryStoryByTitleAsyncTask queryStoryByTitleAsyncTask = new QueryStoryByTitleAsyncTask(storyDao);
        queryStoryByTitleAsyncTask.delegate = this;
        queryStoryByTitleAsyncTask.execute(title);
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


    // AsyncTasks
    private static class QueryStoryByTitleAsyncTask extends
            AsyncTask<String, Void, List<Story>> {

        private StoryDao asyncTaskDao;
        private StoryRepository delegate = null;

        QueryStoryByTitleAsyncTask(StoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<Story> doInBackground(final String... params) {
            return asyncTaskDao.getStoryByTitle(params[0]);
        }

        @Override
        protected void onPostExecute(List<Story> result) {
            delegate.storyByTitleAsyncFinished(result);
        }
    }

    private static class InsertStoryAsyncTask extends AsyncTask<Story, Void, Integer> {

        private StoryDao asyncTaskDao;
        private StoryRepository delegate = null;

        InsertStoryAsyncTask(StoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Story... params) {
            try {
                asyncTaskDao.insertStory(params[0]);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
                return AppConstants.RoomInsertion.INSERTION_STATUS_ERROR.getType();
            } catch (Exception e) {
                Timber.e(e);
                return AppConstants.RoomInsertion.INSERTION_STATUS_ERROR.getType();
            }
            return AppConstants.RoomInsertion.INSERTION_STATUS_INSERTED.getType();
        }

        @Override
        protected void onPostExecute(Integer insertionStatus) {
            super.onPostExecute(insertionStatus);
            delegate.insertionStatus.setValue(insertionStatus);
        }
    }

    private static class UpdateStoryAsyncTask extends AsyncTask<Story, Void, Integer> {

        private StoryDao asyncTaskDao;
        private StoryRepository delegate = null;

        UpdateStoryAsyncTask(StoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Story... params) {
            try {
                asyncTaskDao.updateStory(params[0]);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
                return AppConstants.RoomUpdating.UPDATING_STATUS_ERROR.getType();
            } catch (Exception e) {
                Timber.e(e);
                return AppConstants.RoomUpdating.UPDATING_STATUS_ERROR.getType();
            }
            return AppConstants.RoomUpdating.UPDATING_STATUS_UPDATED.getType();
        }

        @Override
        protected void onPostExecute(Integer updatingStatus) {
            super.onPostExecute(updatingStatus);
            delegate.updatingStatus.setValue(updatingStatus);
        }
    }

    private static class DeleteStoryAsyncTask extends AsyncTask<Story, Void, Integer> {

        private StoryDao asyncTaskDao;
        private StoryRepository delegate = null;

        DeleteStoryAsyncTask(StoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Story... params) {
            try {
                asyncTaskDao.deleteStory(params[0]);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
                return AppConstants.RoomDeletion.DELETION_STATUS_ERROR.getType();
            } catch (Exception e) {
                Timber.e(e);
                return AppConstants.RoomDeletion.DELETION_STATUS_ERROR.getType();
            }
            return AppConstants.RoomDeletion.DELETION_STATUS_DELETED.getType();
        }

        @Override
        protected void onPostExecute(Integer deletionStatus) {
            super.onPostExecute(deletionStatus);
            delegate.deletionStatus.setValue(deletionStatus);
        }
    }
}
