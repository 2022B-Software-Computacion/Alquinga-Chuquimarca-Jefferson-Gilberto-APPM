package com.example.indevelUpChallenge.dataSource;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.example.indevelUpChallenge.model.MovieCreditsResponse;
import com.example.indevelUpChallenge.network.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieCreditsRepository {
    private static final String TAG = "CreditsRepository: ";

    public MutableLiveData<MovieCreditsResponse> dataCredits;
    public MutableLiveData<Boolean> error;
    public MutableLiveData<Boolean> loading;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInstance retrofitInstance = RetrofitInstance.getInstanceMovie();


    private void getMovieCredits(int id) {
        dataCredits = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        compositeDisposable.add(retrofitInstance.getMovieCredits(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieCreditsResponse>() {
                    @Override
                    public void onSuccess(MovieCreditsResponse value) {
                        dataCredits.setValue(value);
                        loading.setValue(false);
                        error.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                        error.setValue(true);
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }));
    }

    public void refreshGetMovieCredits(int id) {
        getMovieCredits(id);
    }

    public MutableLiveData<MovieCreditsResponse> getDataCredits() {
        return dataCredits;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

}
