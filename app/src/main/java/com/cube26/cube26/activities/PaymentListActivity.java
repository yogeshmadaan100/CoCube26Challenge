package com.cube26.cube26.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.cube26.cube26.R;
import com.cube26.cube26.fragments.PaymentListFragment;
import com.cube26.cube26.models.PaymentGateway;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentListActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private PaymentGateway selectedGateway;
    private PaymentListFragment paymentListFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        paymentListFragment = (PaymentListFragment) getSupportFragmentManager().findFragmentById(R.id.payment_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment_list, menu);
        return true;
    }
}
