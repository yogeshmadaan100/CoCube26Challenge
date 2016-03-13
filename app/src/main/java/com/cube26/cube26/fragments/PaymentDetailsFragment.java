package com.cube26.cube26.fragments;


import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cube26.cube26.R;
import com.cube26.cube26.models.PaymentGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.BindBool;
import butterknife.BindInt;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public class PaymentDetailsFragment extends Fragment {
    private static final String ARG_PAYMENT = "payment";
    private static final String ARG_TWO_PANE ="twoPane";
    private boolean twoPane;

    @Bind(R.id.scroll_view)
    NestedScrollView nestedScrollView;
    @Bind(R.id.scroll_view_layout)
    ViewGroup scrollViewLayout;
    @Bind(R.id.poster)              ImageView poster;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rating)              TextView rating;
    @Bind(R.id.rating_container)    ViewGroup ratingContainer;
    @Bind(R.id.setup_fee)              TextView setupFee;
    @Bind(R.id.setup_fee_container)    ViewGroup setupFeeContainer;
    @Bind(R.id.transaction)              TextView transaction;
    @Bind(R.id.transaction_container)    ViewGroup transactionContainer;
    @Bind(R.id.currency)              TextView currency;
    @Bind(R.id.currency_container)    ViewGroup currencyContainer;
    @Bind(R.id.desciption)            TextView description;
    @Bind(R.id.document)                TextView document;
    @BindInt(R.integer.anim_short_duration)         int animShortDuration;
    @BindInt(R.integer.anim_stagger_delay)          int animStaggerDelay;
    @BindInt(R.integer.anim_activity_start_delay)   int animActivityStartDelay;
    @BindBool(R.bool.anim_backdrop_animate_alpha)   boolean anibackdropAnimateAlpha;

    ViewGroup rootView;
    private PaymentGateway paymentGateway;
    private List<View> enterAnimationViews;
    private List<View> exitAnimationViews;
    private boolean mInitialMovieLoaded = false;
    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private boolean isReviewRequestComplete=false,isTrailerRequestComplete=false;

    public PaymentDetailsFragment() {
        // Required empty public constructor
    }

    public static PaymentDetailsFragment newInstance(PaymentGateway paymentGateway, boolean twoPane) {
        PaymentDetailsFragment fragment = new PaymentDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PAYMENT,paymentGateway);
        args.putBoolean(ARG_TWO_PANE,twoPane);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paymentGateway = getArguments().getParcelable(ARG_PAYMENT);
            twoPane = getArguments().getBoolean(ARG_TWO_PANE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
        {
            Log.d("savedInstance","fetching values");
            paymentGateway = savedInstanceState.getParcelable(ARG_PAYMENT);
            twoPane = savedInstanceState.getBoolean(ARG_TWO_PANE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_payment_details, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        initViews();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_PAYMENT,getPaymentGateway());
        outState.putBoolean(ARG_TWO_PANE,twoPane);
    }

    public void initViews()
    {

        enterAnimationViews = Arrays.asList(
                title,  ratingContainer, setupFeeContainer,transactionContainer,currencyContainer,description,document);
        exitAnimationViews = new ArrayList<>();
        exitAnimationViews.add(poster);
        exitAnimationViews.addAll(enterAnimationViews);

        // credits for onPreDraw technique: http://frogermcs.github.io/Instagram-with-Material-Design-concept-part-2-Comments-transition/
        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (scrollViewLayout.getHeight() < nestedScrollView.getHeight()) {
                    ViewGroup.LayoutParams lp = scrollViewLayout.getLayoutParams();
                    lp.height = nestedScrollView.getHeight();
                    scrollViewLayout.setLayoutParams(lp);
                }
                updateMovieDetails();
                startEnterAnimation(animActivityStartDelay);
                return true;
            }
        });

    }
    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }
    private void startEnterAnimation(int startDelay) {
        Interpolator interpolator = new DecelerateInterpolator();
        if (anibackdropAnimateAlpha) {
            View[] mFadeInViews = new View[] { poster };
            for (View v : mFadeInViews) {
                v.setAlpha(0f);
                v.animate()
                        .withLayer()
                        .alpha(1f)
                        .setInterpolator(interpolator)
                        .setDuration(animShortDuration)
                        .setListener(null)
                        .start();
            }
        }
        for (int i = 0; i < enterAnimationViews.size(); ++i) {
            final View v = enterAnimationViews.get(i);
            v.setAlpha(0f);
            v.setTranslationY(75);
            v.animate()
                    .withLayer()
                    .alpha(1.0f)
                    .translationY(0)
                    .setInterpolator(interpolator)
                    .setStartDelay(startDelay + animStaggerDelay * i)
                    .setDuration(animShortDuration)
                    .setListener(null)      // http://stackoverflow.com/a/22934588/504611
                    .start();
        }
    }

    private void startExitAnimation(final Runnable onAnimationNearlyEnded) {
        Interpolator interpolator = new AccelerateInterpolator();
        final View viewForAnimationNearlyEnded = exitAnimationViews.get(5);
        for (int i = 0; i < exitAnimationViews.size(); ++i) {
            final View v = exitAnimationViews.get(i);
            v.setAlpha(1f);
            v.setTranslationY(0);
            ViewPropertyAnimator animator = v.animate();
            if (v == viewForAnimationNearlyEnded) {
                animator.setListener(new AnimatorEndWithoutCancelListener() {
                    @Override
                    public void onAnimationEndWithoutCancel() {
                        onAnimationNearlyEnded.run();
                    }
                });
            }
            animator
                    .withLayer()
                    .alpha(0.0f)
                    .translationY(-75)
                    .setInterpolator(interpolator)
                    .setStartDelay(animStaggerDelay * i)
                    .setDuration(animShortDuration)
                    .start();
        }

    }
    private static abstract class AnimatorEndWithoutCancelListener implements Animator.AnimatorListener {
        boolean cancelled = false;

        public abstract void onAnimationEndWithoutCancel();

        @Override
        public final void onAnimationEnd(Animator animation) {
            if (! cancelled) {
                onAnimationEndWithoutCancel();
            }
            cancelled = false;  // reset the flag
        }

        @Override
        public final void onAnimationCancel(Animator animation) {
            cancelled = true;
        }

        @Override public final void onAnimationStart(Animator animation) {}
        @Override public final void onAnimationRepeat(Animator animation) {}
    }
    private void updateMovieDetails() {




        poster.setTranslationY(0);
        Glide.with(getActivity())
                .load(paymentGateway.getImage())
                .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.ic_launcher).
                into(poster);


        title.setText(paymentGateway.getName());
        rating.setText(paymentGateway.getRating());
        setupFee.setText(paymentGateway.getSetupFee());
        transaction.setText(paymentGateway.getTransactionFees());
        currency.setText(paymentGateway.getCurrencies());
        document.setText(paymentGateway.getHowToDocument());
        description.setText(paymentGateway.getDescription());
        document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = document.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

}
