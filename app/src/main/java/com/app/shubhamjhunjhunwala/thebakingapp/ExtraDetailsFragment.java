package com.app.shubhamjhunjhunwala.thebakingapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Dish;
import com.app.shubhamjhunjhunwala.thebakingapp.Objects.Step;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.io.IOException;

/**
 * Created by shubham on 17/03/18.
 */

public class ExtraDetailsFragment extends Fragment {

    public ExtraDetailsFragment() {}

    public boolean twoPaneLayout;

    public Dish dish;
    public int stepID;

    public String videoURL;

    public SimpleExoPlayerView playerView;
    public SimpleExoPlayer player;
    public static long playerPosition;
    public static int playerPositionForStepID;
    public static boolean playWhenReady;
    public static int currentWindow;

    public TextView textView;

    public static boolean hasShownVideoInFullScreen = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View rootView;

        rootView = inflater.inflate(R.layout.extra_details_fragment, container, false);

        if (savedInstanceState != null) {
            dish =  Parcels.unwrap(savedInstanceState.getParcelable("Dish"));
            stepID = savedInstanceState.getInt("ID");
            twoPaneLayout = savedInstanceState.getBoolean("TwoPane");

            playerPosition = savedInstanceState.getLong("PlayerPosition", 0);
            playWhenReady = savedInstanceState.getBoolean("PlayWhenReady", true);
            currentWindow = savedInstanceState.getInt("CurrentWindow", 0);
            playerPositionForStepID = savedInstanceState.getInt("PlayerPositionForStepID");

            Log.d("Player Position", Long.toString(playerPosition));
        } else {
            playWhenReady = true;
        }

        if (playerPositionForStepID != stepID) {
            playerPosition = 0;
        }

        Log.d("Details Fragment", dish.toString());

        playerView = rootView.findViewById(R.id.video_view);
        textView = rootView.findViewById(R.id.details_text_view);

        Step[] steps = dish.getSteps();
        textView.setText(steps[stepID].getDescription());

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("Dish", Parcels.wrap(dish));
        outState.putInt("ID", stepID);
        outState.putBoolean("TwoPane", twoPaneLayout);

        if (player != null) {
            playerPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
            currentWindow = player.getCurrentWindowIndex();
            playerPositionForStepID = stepID;
        }

        Log.d("Player Position at SIS", Long.toString(playerPosition));

        outState.putLong("PlayerPosition", playerPosition);
        outState.putBoolean("PlayWhenReady", playWhenReady);
        outState.putInt("CurrentWindow", currentWindow);
        outState.putInt("PlayerPositionForStepID", playerPositionForStepID);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !twoPaneLayout && !hasShownVideoInFullScreen) {
            hasShownVideoInFullScreen = true;
            playVideoInFullScreen();
        }*/
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    public void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        Step[] steps = dish.getSteps();
        String imageURL = "";
        videoURL = steps[stepID].getVideoURL();
        imageURL = steps[stepID].getImageURL();

        if (!videoURL.equals("")) {
            Uri uri = Uri.parse(videoURL);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource);
            Log.d("Player Position at init", Long.toString(playerPosition));
            player.seekTo(currentWindow, playerPosition);
            player.setPlayWhenReady(playWhenReady);
        } else if (videoURL.equals("") && !imageURL.equals("")) {
            final String finalImageURL = imageURL;
            new AsyncTask<Void, Void, Void>() {
                Bitmap bitmap;
                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        bitmap = Picasso.with(getContext()).load(finalImageURL).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (bitmap != null) {
                        playerView.setDefaultArtwork(bitmap);
                    } else {
                        playerView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "No Video Available", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        } else {
            playerView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No Video Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void playVideoInFullScreen() {
        Intent intent = new Intent(getContext(), FullScreenVideoActivity.class);
        intent.putExtra("Video URL", videoURL);
        startActivity(intent);
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer")).createMediaSource(uri);
    }
}
