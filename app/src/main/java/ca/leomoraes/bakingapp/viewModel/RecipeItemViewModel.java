package ca.leomoraes.bakingapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import ca.leomoraes.bakingapp.model.Recipe;

public class RecipeItemViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = RecipeItemViewModel.class.getSimpleName();

    // List of recipe
    private MutableLiveData<Recipe> recipe;

    public RecipeItemViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Recipe recipe){
        this.recipe = new MutableLiveData<>();
        this.recipe.setValue( recipe );
    }

    public LiveData<Recipe> getRecipe() {
        return recipe;
    }
}
