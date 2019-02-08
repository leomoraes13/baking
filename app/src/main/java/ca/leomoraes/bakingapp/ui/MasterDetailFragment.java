package ca.leomoraes.bakingapp.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

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
    SimpleExoPlayerView mPlayerView;

    @Nullable @BindView(R.id.detail_step_title)
    TextView title;

    @Nullable @BindView(R.id.detail_step_description)
    TextView description;

    private SimpleExoPlayer mExoPlayer;

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

        // Initialize the image.
/*        if(step.getThumbnailURL()!=null && !step.getThumbnailURL().isEmpty())
            Glide
                .with(getActivity())
                .load(step.getThumbnailURL())
                .into(image);*/

        // Initialize the player.
        if(step.getVideoURL()!=null)
            initializePlayer(Uri.parse(step.getVideoURL()));
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        Context context = getContext();
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }

        // mExoPlayer.stop();
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    /**
     * Release the player when the fragment is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }
}