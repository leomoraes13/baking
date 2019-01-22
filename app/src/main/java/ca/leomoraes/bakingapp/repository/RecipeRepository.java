package ca.leomoraes.bakingapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.util.List;

import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private static class SingletonHelper {
        private static final RecipeRepository INSTANCE = new RecipeRepository();
    }
    public static RecipeRepository getInstance() {
        return SingletonHelper.INSTANCE;
    }
    public RecipeRepository() {

    }
    public LiveData<List<Recipe>> getRecipes(Context context) {
        final MutableLiveData<List<Recipe>> data = new MutableLiveData<>();
        RetrofitService
            .getInstance(context)
            .getRecipe(
                new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        if (response.isSuccessful()) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        data.setValue(null);
                    }
                }
        );

        return data;
    }

}