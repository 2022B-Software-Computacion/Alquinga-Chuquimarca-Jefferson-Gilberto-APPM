package com.example.indevelUpChallenge.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.indevelUpChallenge.R;
import com.example.indevelUpChallenge.model.Movie;
import com.example.indevelUpChallenge.model.MovieResponse;
import com.example.indevelUpChallenge.utils.MovieUtils;
import com.example.indevelUpChallenge.view.activity.MainActivity;
import com.example.indevelUpChallenge.view.adapter.MovieRecyclerAdapter;
import com.example.indevelUpChallenge.viewModel.MovieResponseViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.indevelUpChallenge.utils.Constants.KEY_MOVIE;
import static com.example.indevelUpChallenge.utils.Constants.KEY_TYPE;

public class MovieFragment extends Fragment implements MovieRecyclerAdapter.listener {


    private MovieResponse movieResponse;
    private int type;
    private listener listener;
    private MovieResponseViewModel movieResponseViewModel;

    @BindView(R.id.recyclerViewPopularFragment)
    RecyclerView recyclerView;
    @BindView(R.id.searchViewMovie)
    SearchView searchView;
    @BindView(R.id.progressBarMovie)
    ProgressBar progressBarMovie;


    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(MovieResponse movieResponse, int type) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_MOVIE, movieResponse);
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieResponse = (MovieResponse) getArguments().getSerializable(KEY_MOVIE);
            type = getArguments().getInt(KEY_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView(movieResponse.getMovieResult());

        movieResponseViewModel = new ViewModelProvider(this).get(MovieResponseViewModel.class);

        setSearchView();

        return view;
    }


    public void setSearchView(){
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_search_24));
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));
        searchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    searchMovie(query);
                }
                searchView.onActionViewCollapsed();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    public void searchMovie(String search) {
        movieResponseViewModel.searchMovie(search, type);
        searchMovieObserver();
    }

    private void searchMovieObserver() {
        progressBarMovie.setVisibility(View.VISIBLE);
        movieResponseViewModel.data.observe(this, movieResponse -> {
            List<Movie> movies = movieResponse.getMovieResult();
            if (MovieUtils.isInternetAvailable(Objects.requireNonNull(getContext()))){
                SearchResponseFragment searchResponseFragment = SearchResponseFragment.newInstance(movieResponse);
                ((MainActivity) Objects.requireNonNull(getActivity())).setFragment(searchResponseFragment);
            }else initRecyclerView(movies);
        });
        movieResponseViewModel.loading.observe(this, aBoolean -> {
            if(!aBoolean){
                progressBarMovie.setVisibility(View.GONE);
            }
        });
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (listener) context;
    }

    @Override
    public void movieAdapterListener(Movie movie) {
        listener.movieFragmentListener(movie);
    }

    public interface listener {
        void movieFragmentListener(Movie movie);
    }
}