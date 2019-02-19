package ca.leomoraes.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.adaper.IngredientsAdapter;
import ca.leomoraes.bakingapp.model.Recipe;

public class IngredientsActivity extends AppCompatActivity {

    @BindView(R.id.ingredients_title)
    TextView title;

    @BindView(R.id.ingredients_recycler)
    RecyclerView mRecycler;

    private IngredientsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        Recipe recipe = null;
        if(bundle!=null) {
            recipe = bundle.getParcelable(RecipeItemActivity.EXTRA_RECIPE);
        }

        title.setText(recipe.getName());
        setupRecycler(recipe);
    }

    private void setupRecycler(Recipe recipe) {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new IngredientsAdapter(this, recipe.getIngredients());
        mRecycler.setAdapter(mAdapter);
    }
}
