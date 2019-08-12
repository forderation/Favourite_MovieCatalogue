package com.forderation.favouritesmoviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.forderation.favouritesmoviedb.adapter.SectionsPagerAdapter;
import com.forderation.favouritesmoviedb.fragment.MovieFragment;
import com.forderation.favouritesmoviedb.fragment.TvShowFragment;
import com.forderation.favouritesmoviedb.model.Movie;
import com.forderation.favouritesmoviedb.model.TVShow;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import static com.forderation.favouritesmoviedb.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;

public class MainActivity extends AppCompatActivity {
    Fragment tvShowFragment;
    Fragment movieFragment;
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<TVShow> tvShows = new ArrayList<>();
    public static final String TAG_MOVIES = "tag_movies";
    public static final String TAG_TV_SHOWS = "tag_tv_shows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        setTheme(android.R.style.Theme_Material_Light_DarkActionBar);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        if(savedInstanceState!=null){
            movies.addAll(Objects.requireNonNull(savedInstanceState.<Movie>getParcelableArrayList(TAG_MOVIES)));
            tvShows.addAll(Objects.requireNonNull(savedInstanceState.<TVShow>getParcelableArrayList(TAG_TV_SHOWS)));
        }else{
            movies.addAll(getFavMovieFromContentProvider());
            tvShows.addAll(getFavTVShowFromContentProvider());
        }
        movieFragment = getSupportFragmentManager().findFragmentByTag(MovieFragment.class.getSimpleName());
        if(movieFragment == null){
            movieFragment = new MovieFragment();
            ((MovieFragment) movieFragment).setContext(getApplicationContext());
            ((MovieFragment) movieFragment).setList(movies);
        }
        tvShowFragment = getSupportFragmentManager().findFragmentByTag(TvShowFragment.class.getSimpleName());
        if(tvShowFragment == null){
            tvShowFragment = new TvShowFragment();
            ((TvShowFragment) tvShowFragment).setContext(getApplicationContext());
            ((TvShowFragment) tvShowFragment).setList(tvShows);
        }
        sectionsPagerAdapter.addFragment(movieFragment, getResources().getString(R.string.title_tab_movie));
        sectionsPagerAdapter.addFragment(tvShowFragment, getResources().getString(R.string.title_tab_tv));
        viewPager.setAdapter(sectionsPagerAdapter);
    }

    private ArrayList<Movie> getFavMovieFromContentProvider() {
        Cursor cursor = getContentResolver().query(
                CONTENT_URI_MOVIE
                , null, null, null, null, null);
        ArrayList<Movie> listFromContentProvider = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Movie movie;
            do {
                movie = new Movie(cursor);
                listFromContentProvider.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return listFromContentProvider;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.key_preference), Context.MODE_PRIVATE);
        boolean isFavouriteChange = sharedPref.getBoolean(getString(R.string.key_favourite), false);
        if (isFavouriteChange) {
            ((TvShowFragment) tvShowFragment).setList(getFavTVShowFromContentProvider());
            ((MovieFragment) movieFragment).setList(getFavMovieFromContentProvider());
            Snackbar.make(findViewById(android.R.id.content), R.string.update_db, Snackbar.LENGTH_SHORT)
                    .show();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.key_favourite), false);
            editor.apply();
        }
    }

    private ArrayList<TVShow> getFavTVShowFromContentProvider() {
        Cursor cursor = getContentResolver().query(
                DatabaseContract.TVShowColumns.CONTENT_URI_TV
                , null, null, null, null, null);
        ArrayList<TVShow> listFromContentProvider = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            TVShow tvShow;
            do {
                tvShow = new TVShow(cursor);
                listFromContentProvider.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return listFromContentProvider;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TAG_MOVIES,movies);
        outState.putParcelableArrayList(TAG_TV_SHOWS,tvShows);
    }
}
