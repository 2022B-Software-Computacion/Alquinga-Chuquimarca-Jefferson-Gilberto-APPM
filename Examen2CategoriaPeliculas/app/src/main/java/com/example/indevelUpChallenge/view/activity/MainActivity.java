package com.example.indevelUpChallenge.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.indevelUpChallenge.R;
import com.example.indevelUpChallenge.model.Movie;
import com.example.indevelUpChallenge.view.fragment.MainFragment;
import com.example.indevelUpChallenge.view.fragment.MovieFragment;
import com.example.indevelUpChallenge.view.fragment.SearchResponseFragment;
import com.facebook.stetho.Stetho;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.indevelUpChallenge.utils.Constants.KEY_MOVIE;

public class MainActivity extends AppCompatActivity implements MovieFragment.listener, SearchResponseFragment.listener {


    private long backPressedTime;
    private Toast backToast;


    @BindView(R.id.toolbarMain)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setFragment(new MainFragment());

    }


    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.constraintMainContainer, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void movieFragmentListener(Movie movie) {
        goToDetailsActivity(movie);
    }

    @Override
    public void searchFragmentListener(Movie movie) {
        goToDetailsActivity(movie);
    }

    private void goToDetailsActivity(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_MOVIE, movie);
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();

        } else {
            backToast = Toast.makeText(getBaseContext(), "Presiona atras nuevamente para salir", Toast.LENGTH_SHORT);
            backToast.show();
            setFragment(new MainFragment());

        }
        backPressedTime = System.currentTimeMillis();
    }


}