package ca.leomoraes.bakingapp.service;

import java.util.List;

import ca.leomoraes.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("baking.json")
    Call<List<Recipe>> listRecipe();

}
