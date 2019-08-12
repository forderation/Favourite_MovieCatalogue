package com.forderation.favouritesmoviedb.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forderation.favouritesmoviedb.R;
import com.forderation.favouritesmoviedb.adapter.TvShowAdapter;
import com.forderation.favouritesmoviedb.model.TVShow;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {
    private Context context;
    private TvShowAdapter tvShowAdapter;

    public TvShowFragment() {
        setRetainInstance(true);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<TVShow> tvShows) {
        if (tvShowAdapter == null) {
            tvShowAdapter = new TvShowAdapter(context);
        }
        tvShowAdapter.setTvShowArrayList(tvShows);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(tvShowAdapter);
    }
}
