package net.digitalswarm.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.digitalswarm.popularmovies.models.Movie;
import net.digitalswarm.popularmovies.utils.MovieJsonUtil;
import net.digitalswarm.popularmovies.utils.NetUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//TODO: Implement json movie poster query
//TODO: parse movie poster json into a list of Movie Objects

public class MainActivity extends AppCompatActivity {

    //init grid layout manager for main layout
    private GridLayoutManager gridLayout;
    private ArrayList<Movie> moviePosterList;
    //private List<Movie> moviePosterList;
    private MoviePosterRVAdapter mprvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set gridlayout to have 2 columns
        gridLayout = new GridLayoutManager(this, 2);

        //init movie poster recycler view to recycler view in activity_main
        RecyclerView mpRV = findViewById(R.id.recycler_view);
        moviePosterList = new ArrayList<>();

        //set layout manager for recycler view to grid layout with fixed size
        mpRV.setHasFixedSize(true);
        mpRV.setLayoutManager(gridLayout);

        //create new recycler view and set adapter to movieposterrecyclerview
        mprvAdapter = new MoviePosterRVAdapter(this, moviePosterList);
        mpRV.setAdapter(mprvAdapter);

        URL tmdb = NetUtils.genMovieUrl("popular");
        new GetMovies().execute(tmdb);



    }

    private class GetMovies extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO: add toast message
        }
        /**
         * feed url for netutil request, return json for post execute
         * @param url
         * @return
         */
        @Override
        protected String doInBackground(URL... url) {
            String movieResults = null;
            try {
                movieResults = NetUtils.getMovieData(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return movieResults;
        }
        @Override
        protected void onPostExecute(String movieResults){
            //parse json and build array of movies for mp recycler view
            try {
                moviePosterList = MovieJsonUtil.getMovieResults(movieResults);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mprvAdapter.setmMovieList(moviePosterList);
            mprvAdapter.notifyDataSetChanged();
        }
    }
}
