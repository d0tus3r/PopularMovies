package net.digitalswarm.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.digitalswarm.popularmovies.R;
import net.digitalswarm.popularmovies.models.Review;
import java.util.ArrayList;



public class MovieReviewRVAdapter extends RecyclerView.Adapter<MovieReviewRVAdapter.ReviewViewHolder> {

    //List of Review Objects to Fill Recycler View
    private ArrayList<Review> mReviewList;
    private final Context mContext;

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        final TextView reviewAuthorTextView;
        final TextView reviewContentTextView;

        //view holder constructor
        ReviewViewHolder(View view){
            super(view);
            //assign views
            reviewAuthorTextView = view.findViewById(R.id.review_author_tv);
            reviewContentTextView = view.findViewById(R.id.review_content_tv);
        }
    }

    //Adapter Constructor
    public MovieReviewRVAdapter(Context mContext, ArrayList<Review> mReviewList){
        this.mContext = mContext;
        this.mReviewList = mReviewList;
    }

    //inflate movie poster list layout and return view holder
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View reviewItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_review_list, parent, false);
        return new ReviewViewHolder(reviewItemView);
    }

    //bind data to view holder
    @Override
    public void onBindViewHolder(final ReviewViewHolder reviewHolder, int position){
        //movie object currently binding data from
        Review currentReview = mReviewList.get(position);
        //bind data from movie to views to the poster view holder
        reviewHolder.reviewAuthorTextView.setText(currentReview.getrAuthor());
        reviewHolder.reviewContentTextView.setText(currentReview.getrContent());
    }

    //getItemCount override for returning movie list size
    @Override
    public int getItemCount(){
        return mReviewList.size();
    }
    //method used to assign movieList to Adapter
    public void setmReviewList(ArrayList<Review> reviewList){
        mReviewList = reviewList;
    }


}