package com.example.indevelUpChallenge.dataSource;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.example.indevelUpChallenge.model.MovieVideosResponse;
import com.example.indevelUpChallenge.network.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieVideosRepository {

    private static final String TAG = "VideosRepository: ";

    public MutableLiveData<MovieVideosResponse> data;
    public MutableLiveData<Boolean> error;
    public MutableLiveData<Boolean> loading;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInstance retrofitInstance = RetrofitInstance.getInstanceMovie();

    public void getVideos(int id) {
        data = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        compositeDisposable.add(retrofitInstance.getMovieVideos(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieVideosResponse>() {
                    @Override
                    public void onSuccess(MovieVideosResponse value) {
                        data.setValue(value);
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

    public void refreshGetVideos(int id) {
            getVideos(id);
    }

    public MutableLiveData<MovieVideosResponse> getData() {
        return data;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }
}
