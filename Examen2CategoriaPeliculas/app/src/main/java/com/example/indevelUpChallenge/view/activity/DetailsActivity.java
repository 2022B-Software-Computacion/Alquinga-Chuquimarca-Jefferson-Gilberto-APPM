package com.example.indevelUpChallenge.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.example.indevelUpChallenge.R;
import com.example.indevelUpChallenge.model.Movie;
import com.example.indevelUpChallenge.view.fragment.DetailsFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.indevelUpChallenge.utils.Constants.KEY_MOVIE;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbarDetails)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Movie movie = (Movie) bundle.getSerializable(KEY_MOVIE);
        DetailsFragment detailsFragment = DetailsFragment.newInstance(movie);
        setToolbar();
        setFragment(detailsFragment);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.constraintDetailsAct, fragment);
        fragmentTransaction.commit();

    }


}