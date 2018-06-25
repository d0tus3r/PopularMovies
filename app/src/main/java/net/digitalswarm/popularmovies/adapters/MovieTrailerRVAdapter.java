package net.digitalswarm.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.digitalswarm.popularmovies.R;
import net.digitalswarm.popularmovies.models.Trailer;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MovieTrailerRVAdapter extends RecyclerView.Adapter<MovieTrailerRVAdapter.TrailerViewHolder> {

    //List of Trailer Objects to Fill Recycler View
    private ArrayList<Trailer> mTrailerList;
    private final Context mContext;
    private final MovieTrailerRVAdapterClickListener mClickListener;

    //Click Interface
    public interface MovieTrailerRVAdapterClickListener {
        void onClick(int pos);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final ImageView trailerImgView;
        final TextView trailerTV;

        //view holder constructor
        TrailerViewHolder(View view){
            super(view);
            //assign views
            trailerTV = view.findViewById(R.id.trailer_name_tv);
            trailerImgView = view.findViewById(R.id.movieTrailer_imageView);
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
    public MovieTrailerRVAdapter(Context mContext, ArrayList<Trailer> mTrailerList, MovieTrailerRVAdapterClickListener clickListener){
        this.mContext = mContext;
        this.mTrailerList = mTrailerList;
        this.mClickListener = clickListener;
    }

    //inflate trailer list layout and return view holder
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View trailerItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_trailer_list, parent, false);
        return new TrailerViewHolder(trailerItemView);
    }
    //FINISH FROM HERE
    //bind data to view holder
    @Override
    public void onBindViewHolder(final TrailerViewHolder trailerHolder, int position){
        //movie object currently binding data from
        Trailer currentTrailer = mTrailerList.get(position);
        //need to load url, and thumbnail
        Log.d(TAG, "onBindViewHolder: " + currentTrailer.gettKey());
        trailerHolder.trailerTV.setText(currentTrailer.gettName());
        //bind data from movie to views to the poster view holder
        String youtubeImgUrlStart = "https://img.youtube.com/vi/";
        String youtubeImgUrlEnd = "/0.jpg";
        Picasso.with(mContext)
                .load((youtubeImgUrlStart + currentTrailer.gettKey() + youtubeImgUrlEnd))
                .into(trailerHolder.trailerImgView);
    }

    //getItemCount override for returning movie list size
    @Override
    public int getItemCount(){
        return mTrailerList.size();
    }
    //method used to assign movieList to Adapter
    public void setmTrailerList(ArrayList<Trailer> trailerList){
        mTrailerList = trailerList;
    }





}
