package com.cube26.cube26.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.cube26.cube26.R;
import com.cube26.cube26.fragments.PaymentDetailsFragment;
import com.cube26.cube26.models.PaymentGateway;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentDetailsActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.main_frame)
    FrameLayout mainFrame;
    @Bind(R.id.fab_fav)
    FloatingActionButton fabFavourite;
    @OnClick(R.id.fab_fav)
    public void onFavouriteCLicked()
    {
//        Utils.toggleFavourite(getApplicationContext(),movie);
//        fabFavourite.setImageResource(Utils.isFavourite(getApplicationContext(),movie)?R.drawable.ic_favorite_white_24dp:R.drawable.ic_favorite_border_white_24dp);
    }
    private static final String ARG_PAYMENT = "payment";
    private PaymentGateway paymentGateway;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        paymentGateway = bundle.getParcelable(ARG_PAYMENT);
        collapsingToolbarLayout.setTitle(paymentGateway.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, PaymentDetailsFragment.newInstance(paymentGateway,false)).commit();
//        fabFavourite.setImageResource(Utils.isFavourite(getApplicationContext(),movie)?R.drawable.ic_favorite_white_24dp:R.drawable.ic_favorite_border_white_24dp);

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(ARG_PAYMENT,paymentGateway);
    }

}
