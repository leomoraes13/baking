package ca.leomoraes.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.adaper.RecipeAdapter;
import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.util.SharedPreferencesUtil;
import ca.leomoraes.bakingapp.viewModel.RecipeViewModel;

public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener {

    private String TAG = "LOG_Main";

    @BindView(R.id.recipe_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.recipe_progress)
    ProgressBar progressBar;


    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        setupRecycler();
        setupViewModel();
    }

    private void setupRecycler() {
        GridLayoutManager gridLayoutManager;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            gridLayoutManager = new GridLayoutManager(this, 2);
        else
            gridLayoutManager = new GridLayoutManager(this, 1);

        mRecycler.setLayoutManager(gridLayoutManager);
        mAdapter = new RecipeAdapter(this, this);
        mRecycler.setAdapter(mAdapter);
    }

    private void setupViewModel() {
        progressBar.setVisibility(View.VISIBLE);
        RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.d(TAG, "Updating list of recipes from LiveData in ViewModel");
                mAdapter.setRecipes(recipes);
                progressBar.setVisibility(View.GONE);

                if(recipes!=null && !recipes.isEmpty() && SharedPreferencesUtil.isNull())
                    SharedPreferencesUtil.setRecipeName(RecipeActivity.this, recipes.get(0));
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the object as an extra in the intent
        Intent intent = new Intent(RecipeActivity.this, RecipeItemActivity.class);
        intent.putExtra(RecipeItemActivity.EXTRA_RECIPE, mAdapter.getItemAtPosition(itemId));
        startActivity(intent);
    }
}
