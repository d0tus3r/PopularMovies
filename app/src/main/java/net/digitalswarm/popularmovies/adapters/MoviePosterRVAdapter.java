package net.digitalswarm.popularmovies.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import com.squareup.picasso.Picasso;

import net.digitalswarm.popularmovies.R;
import net.digitalswarm.popularmovies.data.FavoriteMovie;
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
    private final Context mContext;
    private final MoviePosterRVAdapterClickListener mClickListener;

    //define an interface that we'll later implement
    public interface MoviePosterRVAdapterClickListener {
        void onClick(int pos);
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final ImageView posterImgView;

        //view holder constructor
        PosterViewHolder(View view){
            super(view);
            //assign views
            posterImgView = view.findViewById(R.id.moviePoster_imageView);
            view.setOnClickListener(this);
        }

        //implement click handler to current movie view
        @Override
        public void onClick(View view){
            int adapterPos = getAdapterPosition();
            mClickListener.onClick(adapterPos);
        }
    }
    //Adapter Constructor
    public MoviePosterRVAdapter(Context mContext, ArrayList<Movie> mMovieList, MoviePosterRVAdapterClickListener clickListener){
        this.mContext = mContext;
        this.mMovieList = mMovieList;
        this.mClickListener = clickListener;
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
        String TMDB_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
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

    public void setmFavMovies(ArrayList<FavoriteMovie> favMovies){
        //if there is data, set mMovieList to fav movies
        if (favMovies != null) {
            for (int i = 0; i < favMovies.size(); i++) {
                Movie favMovie = new Movie(favMovies.get(i).getMovieTitle(),
                        favMovies.get(i).getMoviePoster(),
                        favMovies.get(i).getMovieRelease(),
                        favMovies.get(i).getMovieRating(),
                        favMovies.get(i).getMovieOverview(),
                        favMovies.get(i).getMovieId());
                mMovieList.add(favMovie);
            }
        }
    }


}
