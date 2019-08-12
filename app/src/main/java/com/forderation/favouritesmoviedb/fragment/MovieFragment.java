package com.forderation.favouritesmoviedb.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forderation.favouritesmoviedb.R;
import com.forderation.favouritesmoviedb.adapter.MovieAdapter;
import com.forderation.favouritesmoviedb.model.Movie;

import java.util.ArrayList;

public class MovieFragment extends Fragment {
    private Context context;
    private MovieAdapter movieAdapter;

    public MovieFragment() {
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(movieAdapter);
    }

    public void setList(ArrayList<Movie> movies) {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(context);
        }
        movieAdapter.setListMovie(movies);
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
