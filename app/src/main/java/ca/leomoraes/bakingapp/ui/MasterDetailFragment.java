package ca.leomoraes.bakingapp.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import ca.leomoraes.bakingapp.model.Step;
import ca.leomoraes.bakingapp.viewModel.RecipeItemViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterDetailFragment extends Fragment {

    private String TAG = "LOG_FRAGMENT_DETAIL";

    @Nullable @BindView(R.id.detail_step_title)
    TextView title;

    @Nullable @BindView(R.id.detail_step_description)
    TextView description;

    @BindView(R.id.detail_media_player)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.detail_media_player_image)
    ImageView mImageView;

    private SimpleExoPlayer mExoPlayer;
    private RecipeItemViewModel recipeItemViewModel;
    private Context mContext;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void setupViewModel() {
        recipeItemViewModel = ViewModelProviders.of(getActivity()).get(RecipeItemViewModel.class);
        recipeItemViewModel.getStepId().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer stepId) {
                Log.d(TAG, "Updating recipe object from LiveData in ViewModel");
                updateLayout(stepId);
            }
        });
    }

    private void updateLayout(Integer stepId){
        Step step = recipeItemViewModel.getRecipe().getValue().getSteps().get(stepId);

        if(title!=null)
            title.setText( step.getShortDescription() );
        if(description!=null)
            description.setText( step.getDescription() );

        // Initialize the player.
        if(step.getVideoURL()!=null && !step.getVideoURL().isEmpty()) {
            mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(step.getVideoURL()));
            mImageView.setVisibility(View.GONE);
        }else {
            releasePlayer();
            mPlayerView.setVisibility(View.INVISIBLE);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
        }

        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(mContext, "BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                mContext, userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(false);

        // mExoPlayer.stop();
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if(mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
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