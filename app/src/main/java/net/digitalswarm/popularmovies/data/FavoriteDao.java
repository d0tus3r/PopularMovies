package net.digitalswarm.popularmovies.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favMovies ORDER BY movieTitle")
    LiveData<List<FavoriteEntry>> loadAllFavorites();

    @Insert
    void insertFavorite(FavoriteEntry favoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(FavoriteEntry favoriteEntry);

    @Delete
    void deleteFavorite(FavoriteEntry favoriteEntry);

    //on fav button selected, query to see if movie id matches
    @Query("SELECT * FROM favMovies WHERE movieId = :movieId")
    FavoriteEntry selectMovieById(String movieId);
}
