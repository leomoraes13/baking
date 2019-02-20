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
    private MutableLiveData<Boolean> twoPanels;
    private MutableLiveData<Integer> stepId;

    public RecipeItemViewModel(@NonNull Application application, Recipe recipe, boolean twoPanels) {
        super(application);
        this.recipe = new MutableLiveData<>();
        this.recipe.setValue( recipe );
        this.twoPanels = new MutableLiveData<>();
        this.twoPanels.setValue( twoPanels );
    }

    public LiveData<Recipe> getRecipe() {
        return recipe;
    }

    public LiveData<Boolean> getTwoPanels() {
        return twoPanels;
    }

    public LiveData<Integer> getStepId() {
        if(stepId==null)
            this.stepId = new MutableLiveData<>();
        return stepId;
    }

    public void setStepId(Integer stepId){
        if(this.stepId==null)
            this.stepId = new MutableLiveData<>();
        this.stepId.postValue(stepId);
    }

}
