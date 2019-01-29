package ca.leomoraes.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.model.Recipe;

public class RecipeItemActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "extra_recipe";

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);
        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null) {
            Recipe recipe = bundle.getParcelable(EXTRA_RECIPE);
        }
    }


}
