package net.digitalswarm.popularmovies.models;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import net.digitalswarm.popularmovies.data.AppDatabase;
import net.digitalswarm.popularmovies.data.FavoriteEntry;
import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private static final String TAG = FavoritesViewModel.class.getSimpleName();

    private LiveData<List<FavoriteEntry>> favMovies;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving favorites from db");
        favMovies = database.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<FavoriteEntry>> getFavMovies() {
        return favMovies;
    }


}
