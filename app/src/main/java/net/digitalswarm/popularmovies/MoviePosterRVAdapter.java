package net.digitalswarm.popularmovies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;

import com.squareup.picasso.Picasso;

import net.digitalswarm.popularmovies.models.Movie;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by us3r on 5/5/2018.
 * Used to bind views and movie poster data
 */
public class MoviePosterRVAdapter extends RecyclerView.Adapter<MoviePosterRVAdapter.PosterViewHolder> {

    //List of Movie Objects to Fill Recycler View
    private ArrayList<Movie> mMovieList;
    private Context mContext;
    private static String TMDB_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private MoviePosterRVAdapterOnClickHandler mClickHandler;


    public interface MoviePosterRVAdapterOnClickHandler {
        void onClick(Movie movie);
    }
    //TODO: finish fixing constructor
    public MoviePosterRVAdapter(MoviePosterRVAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public ImageView posterImgView;

        //view holder constructor
        public PosterViewHolder(View view){
            super(view);
            //assign views
            posterImgView = (ImageView) view.findViewById(R.id.moviePoster_imageView);
            view.setOnClickListener(this);
        }

        //implement click handler to current movie view
        @Override
        public void onClick(View view){
            int adapterPos = getAdapterPosition();
            Movie currentMovie = mMovieList.get(adapterPos);
            mClickHandler.onClick(currentMovie);
        }
    }
    //Adapter Constructor
    public MoviePosterRVAdapter(Context mContext, ArrayList<Movie> mMovieList){
        this.mContext = mContext;
        this.mMovieList = mMovieList;
    }

    //inflate movie poster list layout and return view holder
    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View posterItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster_list, parent, false);

        return new PosterViewHolder(posterItemView);
    }

    //bind data to view holder
    @Override
    public void onBindViewHolder(final PosterViewHolder posterHolder, int position){
        //movie object currently binding data from
        Movie currentMovie = mMovieList.get(position);
        Log.d(TAG, "onBindViewHolder: " + currentMovie.getPosterUrl());
        //bind data from movie to views to the poster view holder
        Picasso.with(mContext)
            .load((TMDB_IMAGE_URL + currentMovie.getPosterUrl()))
            .into(posterHolder.posterImgView);
    }

    //getItemCount override for returning movie list size
    @Override
    public int getItemCount(){
        return mMovieList.size();
    }
    //method used to assign movieList to Adapter
    public void setmMovieList(ArrayList<Movie> movieList){
        mMovieList = movieList;
    }


}
