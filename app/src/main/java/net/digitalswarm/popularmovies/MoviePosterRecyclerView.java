package net.digitalswarm.popularmovies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by us3r on 5/5/2018.
 * Implements a recycler view & defines how content will be displayed
 */

public class MoviePosterRecyclerView extends RecyclerView.Adapter<MoviePosterRecyclerView.ViewHolder> {

    //create array of movie poster urls to feed to recycler view
    private String[] mMoviePosterUrls = new String[0];
    private LayoutInflater mInflater;
    private ItemClickListener mMovieClickListener;


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


    //view holder for recycled views
    public class ViewHolder extends RecyclerView.ViewHolder implements View.onClickListener {

        TextView movieTextView;

        ViewHolder(View itemView) {
            super(itemView);
            movieTextView = (TextView) itemView.findViewById(R.id.movie_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mMovieClickListener) != null) {
                mMovieClickListener.onItemClick(view, getAdapterPosition());
            }
        }


    }

    String getItem(int id) {
        return mMoviePosterUrls[id];
    }

    void setmMovieClickListener(ItemClickListener itemClickListener){
        this.mMovieClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
