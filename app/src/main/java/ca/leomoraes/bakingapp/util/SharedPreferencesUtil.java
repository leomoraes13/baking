package ca.leomoraes.bakingapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.widget.IngredientsAppWidget;

public class SharedPreferencesUtil {
    private static final String preferenceKey = "RECIPE_NAME";

    public static void setRecipeName(Context context, Recipe recipe){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        IngredientsAppWidget.mRecipeSelected = recipe;

        editor.putString(preferenceKey, recipe.getName());
        editor.apply();
    }

    public static String getRecipeName(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(preferenceKey, null);
    }

    public static void removeRecipeName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        IngredientsAppWidget.mRecipeSelected = null;

        editor.remove(preferenceKey);
        editor.apply();
    }

    public static boolean isNull() {
        return IngredientsAppWidget.mRecipeSelected==null;
    }
}
