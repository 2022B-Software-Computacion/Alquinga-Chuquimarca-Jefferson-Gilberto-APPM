package com.example.indevelUpChallenge.view.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indevelUpChallenge.R;
import com.example.indevelUpChallenge.viewModel.MovieResponseViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainFragment extends Fragment {

    private MovieResponseViewModel movieResponseViewModel;
    private MovieFragment popularMovies, topRatedMovies, upcomingMovies;

    @BindView(R.id.viewPagerMain)
    ViewPager viewPager;
    @BindView(R.id.appBarMain)
    AppBarLayout appBarLayout;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        movieResponseViewModel = new ViewModelProvider(this).get(MovieResponseViewModel.class);

        getPopularMovies();

        return view;
    }

    private void getPopularMovies() {
        movieResponseViewModel.getPopularMovies();
        getPopMoviesObserver();
    }

    private void getPopMoviesObserver() {
        movieResponseViewModel.data.observe(getViewLifecycleOwner(), movieResult -> {
            popularMovies = MovieFragment.newInstance(movieResult, 1);
            getTopRatedMovies();
        });
    }

    private void getTopRatedMovies() {
        movieResponseViewModel.getTopRatedMovies();
        getTopMoviesObserver();
    }

    private void getTopMoviesObserver() {
        movieResponseViewModel.data.observe(getViewLifecycleOwner(), movieResult -> {
            topRatedMovies = MovieFragment.newInstance(movieResult, 2);
            getUpcomingMovies();
        });
    }

    private void getUpcomingMovies() {
        movieResponseViewModel.getUpcomingMovies();
        getUpMoviesObserver();
    }

    private void getUpMoviesObserver() {
        movieResponseViewModel.data.observe(getViewLifecycleOwner(), movieResult -> {
            upcomingMovies = MovieFragment.newInstance(movieResult, 3);
            setViewPagerTabs();
        });
    }


    private void setViewPagerTabs() {
        TabLayout tabLayout = new TabLayout(Objects.requireNonNull(getContext()));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
        appBarLayout.removeAllViews();
        appBarLayout.addView(tabLayout);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            String[] titlePages = {"Popular", "Top Rated", "Upcoming"};

            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return popularMovies;
                    case 1:
                        return topRatedMovies;
                    case 2:
                        return upcomingMovies;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titlePages[position];
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }
}