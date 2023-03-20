package com.example.indevelUpChallenge.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.indevelUpChallenge.dataSource.MovieVideosRepository;
import com.example.indevelUpChallenge.model.MovieVideosResponse;

public class MovieVideosViewModel extends AndroidViewModel {

    public MutableLiveData<MovieVideosResponse> data;
    public MutableLiveData<Boolean> error;
    public MutableLiveData<Boolean> loading;
    private MovieVideosRepository movieVideosRepository = new MovieVideosRepository();

    public MovieVideosViewModel(@NonNull Application application) {
        super(application);
    }

    public void getVideos(int id){
        movieVideosRepository.refreshGetVideos(id);
        data= movieVideosRepository.getData();
        error= movieVideosRepository.getError();
        loading= movieVideosRepository.getLoading();

    }
}
