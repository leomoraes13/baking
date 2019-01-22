package ca.leomoraes.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.adaper.RecipeAdapter;
import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.viewModel.RecipeViewModel;

public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener {

    private String TAG = "LOG_Main";

    @BindView(R.id.recipe_recycler)
    public RecyclerView mRecycler;

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
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecipeAdapter(this, this);
        mRecycler.setAdapter(mAdapter);
    }

    private void setupViewModel() {
        RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.d(TAG, "Updating list of recipes from LiveData in ViewModel");
                mAdapter.setTasks(recipes);
            }
        });
    }


    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
/*        Intent intent = new Intent(RecipeActivity.this, AddTaskActivity.class);
        intent.putExtra(AddTaskActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);*/

        Toast.makeText(this, "item: " + itemId, Toast.LENGTH_LONG).show();
    }
}
