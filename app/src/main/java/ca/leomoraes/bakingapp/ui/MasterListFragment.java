package ca.leomoraes.bakingapp.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.adaper.StepAdapter;
import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.util.SharedPreferencesUtil;
import ca.leomoraes.bakingapp.viewModel.RecipeItemViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterListFragment extends Fragment implements StepAdapter.ItemClickListener  {

    private String TAG = "LOG_FRAGMENT_LIST";
    private Context mContext;

    @BindView(R.id.master_list_recycler)
    public RecyclerView mRecycler;

    @BindView(R.id.list_title)
    TextView title;

    private StepAdapter mAdapter;
    private RecipeItemViewModel recipeItemViewModel;

    public MasterListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        setupRecycler();
        setupViewModel();
    }

    private void setupRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new StepAdapter(mContext, this);
        mRecycler.setAdapter(mAdapter);
    }

    private void setupViewModel() {
        recipeItemViewModel = ViewModelProviders.of(getActivity()).get(RecipeItemViewModel.class);

        recipeItemViewModel.getRecipe().observe(getActivity(), new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                Log.d(TAG, "Updating recipe object from LiveData in ViewModel");
                updateLayout(recipe);
            }
        });
    }

    private void updateLayout(Recipe recipe){
        mAdapter.setSteps(recipe.getSteps());
        title.setText(recipe.getName());
    }

    @Override
    public void onItemClickListener(int itemId) {
        recipeItemViewModel.setStepId(itemId);
        if(!recipeItemViewModel.getTwoPanels().getValue()){
            ((RecipeItemActivity)getActivity()).goToDetails();
        }
    }

    @OnClick(R.id.list_ingredients_button)
    public void openIngredients(){
        Intent intent = new Intent(mContext, IngredientsActivity.class);
        intent.putExtra(RecipeItemActivity.EXTRA_RECIPE, recipeItemViewModel.getRecipe().getValue());
        startActivity(intent);
    }

    @OnClick(R.id.list_widget_button)
    public void setWidgetRecipe(){
        SharedPreferencesUtil.setRecipeName(mContext, recipeItemViewModel.getRecipe().getValue());
        Toast.makeText(mContext, "Widget's recipe changed.", Toast.LENGTH_SHORT).show();
    }
}
