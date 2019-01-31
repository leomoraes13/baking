package ca.leomoraes.bakingapp.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.viewModel.RecipeItemViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterDetailFragment extends Fragment {

    private String TAG = "LOG_FRAGMENT_DETAIL";

    @BindView(R.id.master_detail_title)
    TextView title;

    public MasterDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
    }

    private void setupViewModel() {
        ViewModelProviders.of(getActivity()).get(RecipeItemViewModel.class).getRecipe().observe(getActivity(), new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                Log.d(TAG, "Updating recipe object from LiveData in ViewModel");
                updateLayout(recipe);
            }
        });
    }

    private void updateLayout(Recipe recipe){
        title.setText( "Detail: " + recipe.getName() + " / " + recipe.getIngredients().get(0).getIngredient() );
    }
}