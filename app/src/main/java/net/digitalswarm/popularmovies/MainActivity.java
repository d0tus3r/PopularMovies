package net.digitalswarm.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.digitalswarm.popularmovies.models.Movie;

import java.util.List;

//TODO: Implement json movie poster query
//TODO: parse movie poster json into a list of Movie Objects

public class MainActivity extends AppCompatActivity {

    //init grid layout manager for main layout
    private GridLayoutManager gridLayout;
    private List<Movie> moviePosterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set gridlayout to have 2 columns
        gridLayout = new GridLayoutManager(MainActivity.this, 2);

        //init movie poster recycler view to recycler view in activity_main
        RecyclerView mpRV = (RecyclerView) findViewById(R.id.recycler_view);

        //set layout manager for recycler view to grid layout with fixed size
        mpRV.setHasFixedSize(true);
        mpRV.setLayoutManager(gridLayout);

        //create new recycler view and set adapter to movieposterrecyclerview
        MoviePosterRecyclerView mprvAdapter = new MoviePosterRecyclerView(MainActivity.this, moviePosterList);
        mpRV.setAdapter(mprvAdapter);



    }
}
