package net.digitalswarm.popularmovies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by us3r on 5/5/2018.
 * Implements a recycler view & defines how content will be displayed
 */

public class MoviePosterRecyclerView extends RecyclerView.Adapter<MoviePosterRecyclerView.ViewHolder> {

    //create array of movie poster urls to feed to recycler view
    private String[] mMoviePosterUrls = new String[0];
    private LayoutInflater mInflater;


    //construct with moviePosterUrls
    MoviePosterRecyclerView(Context context, String[] moviePosterData) {
        this.mInflater = LayoutInflater.from(context);
        this.mMoviePosterUrls = moviePosterData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_grid, parent, false);
        return new RecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
