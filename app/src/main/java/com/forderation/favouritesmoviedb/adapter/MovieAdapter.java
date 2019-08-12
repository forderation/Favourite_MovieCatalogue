package com.forderation.favouritesmoviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.forderation.favouritesmoviedb.MovieDetailActivity;
import com.forderation.favouritesmoviedb.R;
import com.forderation.favouritesmoviedb.model.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Movie movie = getListMovie().get(position);
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvReleaseDate.setText(movie.getReleaseDate());
        viewHolder.tvOverview.setText(movie.getOverview());
        Glide.with(context)
                .load(Movie.PATH_IMG + movie.getPosterPath())
                .apply(new RequestOptions().fitCenter())
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(25, 3)))
                .into(viewHolder.imgPoster);
        Glide.with(context)
                .load(Movie.SMALL_IMG + movie.getPosterPath())
                .apply(new RequestOptions().centerCrop())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,3)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if(movie.getPosterPath().isEmpty()){
                            viewHolder.rvLayout.setBackground(context.getResources().getDrawable(android.R.color.black));
                        }else{
                            viewHolder.rvLayout.setBackground(resource);
                        }
                    }
                });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.TAG_DETAIL_MOVIE, movie);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie.clear();
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvReleaseDate, tvOverview;
        ImageView imgPoster;
        RelativeLayout rvLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvLayout = itemView.findViewById(R.id.rl_layout);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_date);
            tvOverview = itemView.findViewById(R.id.tv_item_overview);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
