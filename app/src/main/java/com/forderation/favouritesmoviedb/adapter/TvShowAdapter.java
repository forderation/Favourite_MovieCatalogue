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
import com.forderation.favouritesmoviedb.R;
import com.forderation.favouritesmoviedb.TvDetailActivity;
import com.forderation.favouritesmoviedb.model.Movie;
import com.forderation.favouritesmoviedb.model.TVShow;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private ArrayList<TVShow> tvShowArrayList = new ArrayList<>();
    private Context context;

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<TVShow> getTvShowArrayList() {
        return tvShowArrayList;
    }

    public void setTvShowArrayList(ArrayList<TVShow> tvShowArrayList) {
        this.tvShowArrayList.clear();
        this.tvShowArrayList.addAll(tvShowArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_tv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final TVShow tvShow = getTvShowArrayList().get(position);
        viewHolder.tvTitleTv.setText(tvShow.getName());
        Glide.with(context)
                .load(Movie.PATH_IMG + tvShow.getPosterPath())
                .apply(new RequestOptions().fitCenter())
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(25,3)))
                .into(viewHolder.imgPosterTv);
        Glide.with(context)
                .load(Movie.SMALL_IMG + tvShow.getPosterPath())
                .apply(new RequestOptions().centerCrop())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,3)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        viewHolder.rvLayout.setBackground(resource);
                    }
                });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TvDetailActivity.class);
                intent.putExtra(TvDetailActivity.TAG_DETAIL_TV, tvShow);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleTv;
        ImageView imgPosterTv;
        RelativeLayout rvLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvLayout = itemView.findViewById(R.id.rl_layout);
            tvTitleTv = itemView.findViewById(R.id.tv_item_title);
            imgPosterTv = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
