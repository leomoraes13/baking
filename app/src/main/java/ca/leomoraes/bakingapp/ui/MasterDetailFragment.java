package ca.leomoraes.bakingapp.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.leomoraes.bakingapp.R;
import ca.leomoraes.bakingapp.model.Recipe;
import ca.leomoraes.bakingapp.model.Step;
import ca.leomoraes.bakingapp.viewModel.RecipeItemViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterDetailFragment extends Fragment {

    private String TAG = "LOG_FRAGMENT_DETAIL";

    @BindView(R.id.detail_media_player)
    ImageView image;

    @Nullable @BindView(R.id.detail_step_title)
    TextView title;

    @Nullable @BindView(R.id.detail_step_description)
    TextView description;

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
        int orientation = getResources().getConfiguration().orientation;
        Step step = recipe.getSteps().get(1);

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else {
            title.setText( step.getShortDescription() );
            description.setText( step.getDescription() );
        }

        if(step.getThumbnailURL()!=null && !step.getThumbnailURL().isEmpty())
            Glide
                .with(getActivity())
                .load(step.getThumbnailURL())
                .into(image);
    }
}