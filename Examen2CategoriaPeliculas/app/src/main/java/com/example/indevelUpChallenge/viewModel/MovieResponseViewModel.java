package com.example.indevelUpChallenge.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.indevelUpChallenge.dataSource.MovieResponseRepository;
import com.example.indevelUpChallenge.model.MovieResponse;

public class MovieResponseViewModel extends AndroidViewModel {

    public MutableLiveData<MovieResponse> data;
    public MutableLiveData<Boolean> loading;
    public MutableLiveData<Boolean> error;
    private MovieResponseRepository movieResponseRepository;


    public MovieResponseViewModel(@NonNull Application application) {
        super(application);
        movieResponseRepository = new MovieResponseRepository(application);
    }

    public void getPopularMovies(){
        movieResponseRepository.refreshGetPopMovieResult();
        data = movieResponseRepository.getData();
        loading = movieResponseRepository.getLoading();
        error = movieResponseRepository.getError();
    }

    public void getTopRatedMovies(){
        movieResponseRepository.refreshGetTopMovieResult();
        data = movieResponseRepository.getData();
        loading = movieResponseRepository.getLoading();
        error = movieResponseRepository.getError();
    }

    public void getUpcomingMovies(){
        movieResponseRepository.refreshGetUpcomingMovieResult();
        data = movieResponseRepository.getData();
        loading = movieResponseRepository.getLoading();
        error = movieResponseRepository.getError();
    }

    public void searchMovie(String query, int id){
        movieResponseRepository.refreshSearch(query, id);
        data = movieResponseRepository.getData();
        loading = movieResponseRepository.getLoading();
        error = movieResponseRepository.getError();
    }


}


