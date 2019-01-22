package ca.leomoraes.bakingapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.repository.RecipeRepository;

public class RecipeViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = RecipeViewModel.class.getSimpleName();

    // List of recipes
    private LiveData<List<Recipe>> recipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipes = RecipeRepository.getInstance().getRecipes(application.getBaseContext());
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
