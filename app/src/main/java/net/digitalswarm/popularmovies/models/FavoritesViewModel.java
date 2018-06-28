package net.digitalswarm.popularmovies.models;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import net.digitalswarm.popularmovies.data.AppDatabase;
import net.digitalswarm.popularmovies.data.FavoriteMovie;
import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private static final String TAG = FavoritesViewModel.class.getSimpleName();

    private LiveData<FavoriteMovie[]> favMovies;
    private AppDatabase favDb;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        favDb = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving favorites from db");
        favMovies = favDb.favoriteDao().loadAllFavorites();
    }

    public LiveData<FavoriteMovie[]> getFavMovies() {
        return favMovies;
    }


}
