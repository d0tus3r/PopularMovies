package net.digitalswarm.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import net.digitalswarm.popularmovies.models.Movie;
import net.digitalswarm.popularmovies.utils.MovieJsonUtil;
import net.digitalswarm.popularmovies.utils.NetUtils;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviePosterRVAdapter.MoviePosterRVAdapterClickListener {

    private ArrayList<Movie> moviePosterList;
    //private List<Movie> moviePosterList;
    private MoviePosterRVAdapter mprvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set gridlayout to have 2 columns
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        //init movie poster recycler view to recycler view in activity_main
        RecyclerView mpRV = findViewById(R.id.recycler_view);
        moviePosterList = new ArrayList<>();
        //set layout manager for recycler view to grid layout with fixed size
        mpRV.setHasFixedSize(true);
        mpRV.setLayoutManager(gridLayout);
        //create new recycler view and set adapter to movieposterrecyclerview
        mprvAdapter = new MoviePosterRVAdapter(this, moviePosterList, this);
        mpRV.setAdapter(mprvAdapter);
        //generate url with sortPref popular by default : v2 maybe save state
        URL tmdb = NetUtils.genMovieUrl("popular");
        //populate screen with get movies async task
        new GetMovies().execute(tmdb);
    }

    @Override
    public void onClick(int position){
        //assign context and activity class for scope
        Context context = this;
        Class destinationClass = DetailActivity.class;
        //create a new intent to launch detail activity, using current moviePosterList position
        Intent detailActivityIntent = new Intent(context, destinationClass);
        detailActivityIntent.putExtra("Movie", moviePosterList.get(position));
        startActivity(detailActivityIntent);
    }

    private class GetMovies extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            //pass movie list to adapter
            mprvAdapter.setmMovieList(moviePosterList);
            mprvAdapter.notifyDataSetChanged();
        }
    }

    /**
     * sort menu -- Popular / Top Rated as choices
     * when selecting a new sort parameter update movie poster list with
     * new movies
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create menu inflater
        MenuInflater inflater = getMenuInflater();
        //inflate menu
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String sortPref = "popular";
        if (item.getItemId() == R.id.sort_popular) {
            sortPref = "popular";
            URL tmdb = NetUtils.genMovieUrl(sortPref);
            new GetMovies().execute(tmdb);
            return true;
        }
        if (item.getItemId() == R.id.sort_top_rated) {
            sortPref = "top_rated";
            URL tmdb = NetUtils.genMovieUrl(sortPref);
            new GetMovies().execute(tmdb);
        }
        return super.onOptionsItemSelected(item);
    }
}
