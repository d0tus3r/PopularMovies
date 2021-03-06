package net.digitalswarm.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import net.digitalswarm.popularmovies.adapters.MoviePosterRVAdapter;
import net.digitalswarm.popularmovies.data.AppDatabase;
import net.digitalswarm.popularmovies.data.FavoriteMovie;
import net.digitalswarm.popularmovies.models.FavoritesViewModel;
import net.digitalswarm.popularmovies.models.Movie;
import net.digitalswarm.popularmovies.utils.MovieJsonUtil;
import net.digitalswarm.popularmovies.utils.NetUtils;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static net.digitalswarm.popularmovies.utils.NetUtils.internetAccess;

public class MainActivity extends AppCompatActivity implements MoviePosterRVAdapter.MoviePosterRVAdapterClickListener {

    private ArrayList<Movie> moviePosterList;
    private MoviePosterRVAdapter mprvAdapter;
    private GridLayoutManager gridLayout;
    public static AppDatabase favDb;
    //keys for saved instance state bundle
    private static final String SORT_PREF_KEY = "sortPref";
    private static final String GRID_STATE = "gridState";
    //values for saved instance state
    private String sortPref;
    private Parcelable gridState;
    private ArrayList<FavoriteMovie> favMovies;
    private FavoritesViewModel favoritesViewModel;

    /**
     * todo: finish implementing fav button state testing
     * todo: finish implementing fav view
     * todo: finish implementing saved instance states to handle rotation
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //grab db and assign to favDb
        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        favDb = AppDatabase.getInstance(getApplicationContext());
        //set gridlayout to have 2 columns
        gridLayout = new GridLayoutManager(this, 2);
        //init movie poster recycler view to recycler view in activity_main
        RecyclerView mpRV = findViewById(R.id.recycler_view);
        moviePosterList = new ArrayList<>();
        //set layout manager for recycler view to grid layout with fixed size
        mpRV.setHasFixedSize(true);
        mpRV.setLayoutManager(gridLayout);
        //create new recycler view and set adapter to movieposterrecyclerview
        mprvAdapter = new MoviePosterRVAdapter(this, moviePosterList, this);
        mpRV.setAdapter(mprvAdapter);
        //generate url with sortPref popular by default, check instancestate
        if (savedInstanceState == null) {
            sortByPopular();
        } else {
            sortPref = savedInstanceState.getString(SORT_PREF_KEY);

            switch (sortPref) {
                case "favorites":
                    setupViewModel();
                    break;

                case "popular":
                    sortByPopular();
                    break;

                case "top_rated":
                    sortByTopRated();
                    break;
            }


        }



    }

    //implement saving of states
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SORT_PREF_KEY, sortPref);
        gridState = gridLayout.onSaveInstanceState();
        outState.putParcelable(GRID_STATE, gridState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        sortPref = savedInstanceState.getString(SORT_PREF_KEY);
        gridState = savedInstanceState.getParcelable(GRID_STATE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        //setup view model if sorting by favorites
        if (sortPref == "favorites") {
            setupViewModel();
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        //resume grid state if saved state available
        if (gridState != null) {
            gridLayout.onRestoreInstanceState(gridState);
        }
        super.onResume();
    }

    @Override
    public void onClick(int position){
        //assign context and activity class for scope
        Context context = this;
        Class destinationClass = DetailActivity.class;
        //create a new intent to launch detail activity, using current moviePosterList position
        Intent detailActivityIntent = new Intent(context, destinationClass);
        if (sortPref == "favorites") {
            moviePosterList = mprvAdapter.getmFavMovieList();
        }
        detailActivityIntent.putExtra("Movie", moviePosterList.get(position));
        startActivity(detailActivityIntent);
    }

    private void setupViewModel() {
        favoritesViewModel.getFavMovies().observe(this, new Observer<FavoriteMovie[]>() {
            @Override
            public void onChanged(@Nullable FavoriteMovie[] favoriteMovies) {
                if (favoriteMovies != null) {
                    mprvAdapter.setmFavMovies(favoriteMovies);
                    mprvAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    private class GetMovies extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //fetch movie data
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
        if (item.getItemId() == R.id.sort_popular) {
            sortByPopular();
            return true;
        }
        if (item.getItemId() == R.id.sort_top_rated) {
            sortByTopRated();
        }
        if (item.getItemId() == R.id.favorite_movies_list) {
            sortPref = "favorites";
            setupViewModel();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortByPopular() {
        sortPref = "popular";
        URL tmdb = NetUtils.genMovieUrl(sortPref);
        if(internetAccess(this)) {
            new GetMovies().execute(tmdb);
        } else {
            Toast.makeText(this, "Please connect to internet and try again!", Toast.LENGTH_LONG).show();
        }
    }

    private void sortByTopRated() {
        sortPref = "top_rated";
        URL tmdb = NetUtils.genMovieUrl(sortPref);
        if(internetAccess(this)) {
            new GetMovies().execute(tmdb);
        } else {
            Toast.makeText(this, "Please connect to internet and try again!", Toast.LENGTH_LONG).show();
        }
    }
}
