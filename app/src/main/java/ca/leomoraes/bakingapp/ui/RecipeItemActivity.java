package ca.leomoraes.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.viewModel.RecipeItemViewModel;
import ca.leomoraes.bakingapp.viewModel.ViewModelFactory;

public class RecipeItemActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "extra_recipe";
    private String TAG = "LOG_ITEM";

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;
    private RecipeItemViewModel viewModel;
    private FragmentManager fm;
    private boolean isHome=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if(savedInstanceState!=null)
            isHome = savedInstanceState.getBoolean(getString(R.string.boolean_param));

        setContentView(R.layout.activity_recipe_item);
        ButterKnife.bind(this);

        mTwoPane = findViewById(R.id.master_detail_fragment)!=null;

        Bundle bundle = this.getIntent().getExtras();
        Recipe recipe = null;
        if(bundle!=null) {
            recipe = bundle.getParcelable(EXTRA_RECIPE);
        }

        setupViewModel(recipe, mTwoPane);
        setupFragments();
    }

    private void setupFragments() {
        fm = getSupportFragmentManager();

        if(mTwoPane){
            fm.beginTransaction()
                    .replace(R.id.master_list_fragment, new MasterListFragment())
                    .commit();

            fm.beginTransaction()
                    .replace(R.id.master_detail_fragment, new MasterDetailFragment())
                    .commit();
        }else{
            if(isHome) {
                fm.beginTransaction()
                        .replace(R.id.master_list_fragment, new MasterListFragment())
                        .commit();
            }else{
                fm.beginTransaction()
                        .replace(R.id.master_list_fragment, new MasterDetailFragment())
                        .commit();
            }
        }
    }

    private void setupViewModel(Recipe recipe, boolean twoPanel) {
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(), recipe, twoPanel)).get(RecipeItemViewModel.class);
    }

    public void goToDetails(){
        isHome = false;
        fm.beginTransaction()
            .replace(R.id.master_list_fragment, new MasterDetailFragment())
            .commit();
    }
    public void goToList(){
        isHome = true;
        fm.beginTransaction()
                .replace(R.id.master_list_fragment, new MasterListFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(!isHome)
            goToList();
        else
            super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(getString(R.string.boolean_param), isHome);
    }
}
