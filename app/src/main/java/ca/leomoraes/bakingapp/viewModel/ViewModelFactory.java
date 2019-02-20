package ca.leomoraes.bakingapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import ca.leomoraes.bakingapp.model.Recipe;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private Recipe recipe;
    private boolean twoPanels;

    public ViewModelFactory(Application application, Recipe recipe, boolean twoPanels) {
        mApplication = application;
        this.recipe = recipe;
        this.twoPanels = twoPanels;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RecipeItemViewModel(mApplication, recipe, twoPanels);
    }
}
