package com.example.indevelUpChallenge.dataSource;

import android.app.Application;
import android.util.Log;


import androidx.lifecycle.MutableLiveData;

import com.example.indevelUpChallenge.persistence.MovieDao;
import com.example.indevelUpChallenge.persistence.MovieDatabase;
import com.example.indevelUpChallenge.model.Movie;
import com.example.indevelUpChallenge.model.MovieResponse;
import com.example.indevelUpChallenge.network.RetrofitInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieResponseRepository {

    private static final String TAG = "MovieRepository: ";

    public MutableLiveData<MovieResponse> data;
    public MutableLiveData<Boolean> error;
    public MutableLiveData<Boolean> loading;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInstance retrofitInstance = RetrofitInstance.getInstanceMovie();
    private MovieDao movieDao;
    private MovieResponse movieResponse;

    public MovieResponseRepository(Application application) {
        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        movieDao = movieDatabase.movieDao();
    }

    private void getPopMovieResult() {
        data = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        compositeDisposable.add(retrofitInstance.getPopularMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                    @Override
                    public void onSuccess(MovieResponse value) {
                        value.setType("popular");
                        deletePopMovies();
                        insert(value.getMovieResult());
                        data.setValue(value);
                        loading.setValue(false);
                        error.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getLocalizedMessage());
                        loading.setValue(false);
                        error.setValue(true);
                        getDBPopMovies();

                    }
                }));
    }


    public void refreshGetPopMovieResult() {
        getPopMovieResult();
    }

    private void getTopMovieResult() {
        data = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        compositeDisposable.add(retrofitInstance.getTopRatedMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                    @Override
                    public void onSuccess(MovieResponse value) {
                        value.setType("top_rated");
                        deleteTopMovies();
                        insert(value.getMovieResult());
                        data.setValue(value);
                        loading.setValue(false);
                        error.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                        error.setValue(true);
                        getDBTopMovies();
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }));
    }

    public void refreshGetTopMovieResult() {
        getTopMovieResult();
    }

    private void getUpcomingMovieResult() {
        data = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        compositeDisposable.add(retrofitInstance.getUpcomingMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                    @Override
                    public void onSuccess(MovieResponse value) {
                        value.setType("upcoming");
                        deleteUpcomingMovies();
                        insert(value.getMovieResult());
                        data.setValue(value);
                        loading.setValue(false);
                        error.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                        error.setValue(true);
                        getDBUpcomingMovies();
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }));
    }

    public void refreshGetUpcomingMovieResult() {
        getUpcomingMovieResult();

    }

    private void searchMovie(String query, int type) {
        data = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        compositeDisposable.add(retrofitInstance.searchMovie(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                    @Override
                    public void onSuccess(MovieResponse value) {
                        data.setValue(value);
                        loading.setValue(false);
                        error.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                        error.setValue(true);
                        searchDB(query, type);
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }));
    }

    public void refreshSearch(String query, int type) {
        searchMovie(query, type);
    }

    public MutableLiveData<MovieResponse> getData() {
        return data;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }


    public void insert(List<Movie> movieList) {
        movieDao.insertMoviesList(movieList);
    }

    public void deletePopMovies() {
        movieDao.deletePopMovies();
    }

    public void deleteTopMovies() {
        movieDao.deleteTopMovies();
    }

    public void deleteUpcomingMovies() {
        movieDao.deleteUpcomingMovies();
    }

    public void getDBPopMovies() {
        movieResponse = new MovieResponse(movieDao.getPopMovies());
        data.setValue(movieResponse);
    }

    public void getDBTopMovies() {
        movieResponse = new MovieResponse(movieDao.getTopMovies());
        data.setValue(movieResponse);
    }

    public void getDBUpcomingMovies() {
        movieResponse = new MovieResponse(movieDao.getUpcomingMovies());
        data.setValue(movieResponse);
    }


    public void searchDB(String search, int type) {
        search = "%" + search + "%";
        switch (type) {
            case 1:
                searchPopDB(search);
                break;
            case 2:
                searchTopDB(search);
                break;
            case 3:
                searchUpcomingDB(search);
                break;
        }
    }

    public void searchPopDB(String search) {
        movieResponse = new MovieResponse(movieDao.searchPopMovie(search));
        data.setValue(movieResponse);
    }

    public void searchTopDB(String search) {
        movieResponse = new MovieResponse(movieDao.searchTopMovie(search));
        data.setValue(movieResponse);
    }

    public void searchUpcomingDB(String search) {
        movieResponse = new MovieResponse(movieDao.searchUpcomingMovie(search));
        data.setValue(movieResponse);
    }


}




