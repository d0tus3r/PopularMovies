package net.digitalswarm.popularmovies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.digitalswarm.popularmovies.models.Movie;

import java.util.List;

/**
 * Created by us3r on 5/5/2018.
 * Implements a recycler view & defines how content will be displayed
 */

public class MoviePosterRecyclerView extends RecyclerView.Adapter<MoviePosterRecyclerView.MoviePosterRVHolder> {

    //create list of movie objects for movie poster thumbnail / name pulling
    private List<Movie> moviePosterList;
    private Context context;

    //constructor using movie list data
    public MoviePosterRecyclerView(Context context, List<Movie> movieList) {
        this.moviePosterList = movieList;
        this.context = context;
    }

    //customized rv view holder with our layout inflated
    @Override
    public MoviePosterRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_list, null);
        MoviePosterRVHolder mpVH = new MoviePosterRVHolder(layoutView);
        return mpVH;
    }

    //grab data from moviePosterList and bind to view
    @Override
    public void onBindViewHolder(MoviePosterRVHolder mpHolder, int position) {
        //TODO: fix imageResource call with json queried image url
        mpHolder.moviePosterThumbnailIV.setImageResource(moviePosterList.get(position).getThumbnailUrl());
        mpHolder.movieNameTV.setText(moviePosterList.get(position).getOgName());
    }

    //override getItemCount with movie poster list size
    @Override
    public int getItemCount() {
        return this.moviePosterList.size();
    }



    /**
     * MoviePosterRVHolder - references movie poster layout and adds on OnClickListener to
     *      transition to movie details activity.
     */

    public class MoviePosterRVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //create textview and imageview
        public ImageView moviePosterThumbnailIV;
        public TextView movieNameTV;

        //view holder constructor
        public MoviePosterRVHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            moviePosterThumbnailIV = (ImageView) itemView.findViewById(R.id.movie_poster_thumbnail_iv);
            movieNameTV = (TextView) itemView.findViewById(R.id.moviePosterName_textView);
        }

        //TODO: implement on click method to transition to detail_activity using intents
        @Override
        public void onClick(View view) {
            //Will send intention to detail_activity
        }


    }

}
