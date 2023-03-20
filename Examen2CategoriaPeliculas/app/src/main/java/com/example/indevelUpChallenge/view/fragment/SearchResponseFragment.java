package com.example.indevelUpChallenge.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indevelUpChallenge.model.Movie;
import com.example.indevelUpChallenge.model.MovieResponse;
import com.example.indevelUpChallenge.R;
import com.example.indevelUpChallenge.view.adapter.MovieRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.indevelUpChallenge.utils.Constants.KEY_MOVIE;

public class SearchResponseFragment extends Fragment implements MovieRecyclerAdapter.listener {


    private MovieResponse movieResponse;
    private listener listener;

    @BindView(R.id.recyclerViewFragmentSearch)
    RecyclerView recyclerView;


    public SearchResponseFragment() {
        // Required empty public constructor
    }


    public static SearchResponseFragment newInstance(MovieResponse movieResponse) {
        SearchResponseFragment fragment = new SearchResponseFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_MOVIE, movieResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieResponse = (MovieResponse) getArguments().getSerializable(KEY_MOVIE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_response, container, false);
        ButterKnife.bind(this, view);

        List<Movie> movieList = movieResponse.getMovieResult();
        initRecyclerView(movieList);

        return view;
    }

    private void initRecyclerView(List<Movie> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        MovieRecyclerAdapter recyclerAdapter = new MovieRecyclerAdapter(list, this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void movieAdapterListener(Movie movie) {
        listener.searchFragmentListener(movie);
    }

    public interface listener{
        void searchFragmentListener(Movie movie);
    }
}