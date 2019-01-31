package ca.leomoraes.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.viewModel.RecipeItemViewModel;

public class RecipeItemActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "extra_recipe";
    private String TAG = "LOG_ITEM";

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;
    private RecipeItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);
        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        Recipe recipe = null;
        if(bundle!=null) {
            recipe = bundle.getParcelable(EXTRA_RECIPE);
        }

        mTwoPane = findViewById(R.id.master_detail_fragment)!=null;

        setupViewModel(recipe);
        setupFragments();
    }

    private void setupFragments() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.master_list_fragment, new MasterListFragment())
                .commit();

        if(mTwoPane){
            fm.beginTransaction()
                    .add(R.id.master_detail_fragment, new MasterDetailFragment())
                    .commit();
        }
    }

    private void setupViewModel(Recipe recipe) {
        viewModel = ViewModelProviders.of(this).get(RecipeItemViewModel.class);
        viewModel.init( recipe );
    }
}
