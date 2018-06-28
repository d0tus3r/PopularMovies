package net.digitalswarm.popularmovies.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    LiveData<FavoriteMovie[]> loadAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteMovie favoriteMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(FavoriteMovie favoriteMovie);

    @Delete
    void deleteFavorite(FavoriteMovie favoriteMovie);

    //on fav button selected, query to see if movie id matches
    @Query("SELECT * FROM favorites WHERE movieId = :movieId")
    FavoriteMovie selectMovieById(String movieId);
}
