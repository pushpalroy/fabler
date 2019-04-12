package com.techradge.fabler.data.local.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import com.techradge.fabler.data.local.appDatabase.UserDatabase;
import com.techradge.fabler.data.local.dao.UserDao;
import com.techradge.fabler.data.model.User;
import com.techradge.fabler.di.ApplicationContext;

import java.util.List;

import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class UserRepository {

    private UserDao userDao;
    private MutableLiveData<Boolean> userInsertionStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> userUpdatingStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> userDeletionStatus = new MutableLiveData<>();

    public UserRepository(@ApplicationContext Context context) {
        UserDatabase userDatabase = UserDatabase.getInstance(context);
        userDao = userDatabase.userDao();
    }

    public LiveData<List<User>> getUser() {
        return userDao.getUser();
    }

    public void insertUser(User user) {
        InsertUserAsyncTask insertUserAsyncTask = new InsertUserAsyncTask(userDao);
        insertUserAsyncTask.delegate = this;
        insertUserAsyncTask.execute(user);
    }

    public void updateUser(User user) {
        UpdateUserAsyncTask updateUserAsyncTask = new UpdateUserAsyncTask(userDao);
        updateUserAsyncTask.delegate = this;
        updateUserAsyncTask.execute(user);
    }

    public void deleteUser(User user) {
        DeleteUserAsyncTask deleteUserAsyncTask = new DeleteUserAsyncTask(userDao);
        deleteUserAsyncTask.delegate = this;
        deleteUserAsyncTask.execute(user);
    }

    public MutableLiveData<Boolean> getUserInsertionStatus() {
        return userInsertionStatus;
    }

    public MutableLiveData<Boolean> getUserUpdatingStatus() {
        return userUpdatingStatus;
    }

    public MutableLiveData<Boolean> getUserDeletionStatus() {
        return userDeletionStatus;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Boolean> {

        private UserDao asyncUserDao;
        private UserRepository delegate = null;

        InsertUserAsyncTask(UserDao dao) {
            asyncUserDao = dao;
        }

        @Override
        protected Boolean doInBackground(final User... params) {
            try {
                asyncUserDao.insertUser(params[0]);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
                return false;
            } catch (Exception e) {
                Timber.e(e);
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean insertionStatus) {
            super.onPostExecute(insertionStatus);
            delegate.userInsertionStatus.setValue(insertionStatus);
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Boolean> {

        private UserDao asyncUserDao;
        private UserRepository delegate = null;

        UpdateUserAsyncTask(UserDao dao) {
            asyncUserDao = dao;
        }

        @Override
        protected Boolean doInBackground(final User... params) {
            try {
                asyncUserDao.updateUser(params[0]);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
                return false;
            } catch (Exception e) {
                Timber.e(e);
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean updatingStatus) {
            super.onPostExecute(updatingStatus);
            delegate.userUpdatingStatus.setValue(updatingStatus);
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Boolean> {

        private UserDao asyncUserDao;
        private UserRepository delegate = null;

        DeleteUserAsyncTask(UserDao dao) {
            asyncUserDao = dao;
        }

        @Override
        protected Boolean doInBackground(final User... params) {
            try {
                asyncUserDao.deleteUser(params[0]);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
                return false;
            } catch (Exception e) {
                Timber.e(e);
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean deletionStatus) {
            super.onPostExecute(deletionStatus);
            delegate.userDeletionStatus.setValue(deletionStatus);
        }
    }
}