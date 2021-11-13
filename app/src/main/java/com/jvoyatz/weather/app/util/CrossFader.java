package com.jvoyatz.weather.app.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

public class CrossFader {
    private static final String TAG = "CrossFader";
    private final View mView1;
    private final View mView2;
    private final int mDuration;
    private float mView2alpha;
    private final Context context;

    /***
     * Instantiate a new CrossFader object.
     * @param view1 the view to fade out
     * @param view2 the view to fade in
     * @param fadeDuration the duration in milliseconds for each fade to last
     */
    public CrossFader(View view1, View view2, int fadeDuration, Activity context) {
        mView1 = view1;
        mView2 = view2;
        mDuration = fadeDuration;
        this.mView2alpha = 0f;
        this.context = context;
    }


    /***
     * Start the cross-fade animation.
     */
    public void start() {
        mView2.setAlpha(0f);
        mView2.setVisibility(View.VISIBLE);

        mView1.setVisibility(View.VISIBLE);
        mView1.animate()
                .alpha(0f)
                .setDuration(mDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mView1.setVisibility(View.GONE);
                        mView1.setAlpha(1f);
                        mView2.animate()
                                .alpha(1f)
                                .setDuration(mDuration)
                                .setListener(null);
                    }
                });
    }


    public void crossfade() {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        mView2.setAlpha(mView2alpha);
        mView2.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        mView2.animate()
                .alpha(1f)
                .setDuration(mDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        mView1.setVisibility(View.VISIBLE);
        mView1.animate()
                .alpha(0f)
                .setDuration(mDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mView1.setVisibility(View.GONE);
                        mView1.setAlpha(1f);
                        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mView2alpha = 0.6f;
                    }
                });

    }
}