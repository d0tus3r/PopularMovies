package net.digitalswarm.popularmovies.data;


import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

public interface FavoriteDao {

    @Query("SELECT * FROM favMovies ORDER BY movieTitle")
    List<FavoriteEntry> loadAllFavorites();

    @Insert
    void insertFavorite(FavoriteEntry favoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(FavoriteEntry favoriteEntry);

    @Delete
    void deleteFavorite(FavoriteEntry favoriteEntry);
}
