package ca.leomoraes.bakingapp.service;

import android.content.Context;

import java.util.List;

import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;
    private RecipeService recipeService;
    private static RetrofitService service;

    public static RetrofitService getInstance(Context context){
        if (service == null) {
            service = new RetrofitService(context);
        }
        return service;
    }

    public RetrofitService(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.recipe_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recipeService = retrofit.create(RecipeService.class);
    }

    public void getRecipe(Callback<List<Recipe>> callback){
        Call<List<Recipe>> jsonCall = recipeService.listRecipe();
        jsonCall.enqueue(callback);
    }

}
