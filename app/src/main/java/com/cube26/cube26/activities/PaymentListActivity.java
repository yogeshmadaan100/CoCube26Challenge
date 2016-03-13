package com.cube26.cube26.activities;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(paymentListFragment!=null)
                    paymentListFragment.performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("new text",""+newText);
                paymentListFragment.performSearch(newText);
                return false;
            }
        });
        return true;
    }

}
