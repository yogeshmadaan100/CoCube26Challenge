package com.cube26.cube26.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cube26.cube26.R;
import com.cube26.cube26.activities.PaymentDetailsActivity;
import com.cube26.cube26.adapters.PaymentAdapter;
import com.cube26.cube26.models.PaymentGateway;
import com.cube26.cube26.models.PaymentResponse;
import com.cube26.cube26.models.SortCriteria;
import com.cube26.cube26.services.PaymentService;
import com.cube26.cube26.utils.RxUtils;
import com.cube26.cube26.utils.Utils;
import com.cube26.cube26.utils.ui.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class PaymentListFragment extends Fragment implements PaymentAdapter.PaymentClickInterface {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private static final String TAG = PaymentListFragment.class.getCanonicalName();
    public static final SortCriteria defaultSortCriteria = SortCriteria.NAME;
    public static  SortCriteria currentSortCriteria = defaultSortCriteria;
    private static final String KEY_PAYMENT = "movies";
    private static final String KEY_SORT_ORDER = SortCriteria.class.getSimpleName();
    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private PaymentAdapter paymentAdapter;
    private List<PaymentGateway> paymentGateways;
    LinearLayoutManager linearLayoutManager;
    private PaymentResponse paymentResponse;
    View rootView;
    public PaymentListFragment() {
        // Required empty public constructor
    }

    public static PaymentListFragment newInstance() {
        PaymentListFragment fragment = new PaymentListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_payment_list, container, false);
        ButterKnife.bind(this,rootView);
        initViews();
        if (savedInstanceState != null) {
            if(savedInstanceState.getParcelable(KEY_PAYMENT)!=null)
            {
                paymentResponse = savedInstanceState.getParcelable(KEY_PAYMENT);
                refreshData(paymentResponse);
            }
            if(savedInstanceState.getSerializable(KEY_SORT_ORDER)!=null)
                currentSortCriteria = (SortCriteria)savedInstanceState.getSerializable(KEY_SORT_ORDER);
        }
        if(paymentResponse==null)
            refreshContent();
        return rootView;
    }

    public void initViews()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(currentSortCriteria==SortCriteria.FAVOURITES)
                {
//                    refreshData(Utils.getFavouriteMovies(getActivity()));
                }
                else
                    refreshContent();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        paymentGateways = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        paymentAdapter = new PaymentAdapter(getActivity(), paymentGateways, this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(paymentAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }

    public void refreshContent()
    {
        startRefreshing();
        _subscriptions.add(//
                new PaymentService(getActivity()).getPaymentApi().fetchPaymentGateways(getResources().getString(R.string.string_paramter_type), getResources().getString(R.string.sting_paramter_payment_list))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<PaymentResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                if(!Utils.isConnectedToInternet(getActivity()))
                                    showSnackbar(getResources().getString(R.string.text_no_internet));
                                else
                                    showSnackbar(getResources().getString(R.string.text_default_error));
                            }

                            @Override
                            public void onNext(PaymentResponse paymentResponse) {
                                refreshData(paymentResponse);
                            }
                        }));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_PAYMENT,paymentResponse);
        outState.putSerializable(KEY_SORT_ORDER,currentSortCriteria);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);

    }

    @Override
    public void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(_subscriptions);
    }
    @Override
    public void onPaymentClick(View itemView, PaymentGateway paymentGateway, boolean isDefaultSelection) {
        Intent intent = new Intent(getActivity(), PaymentDetailsActivity.class);
        intent.putExtra("payment",paymentGateway);
        getActivity().startActivity(intent);
    }
    public void startRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }
    public void refreshData(PaymentResponse paymentResponse)
    {
        if (paymentResponse != null && paymentResponse.getPaymentGateways().size() > 0) {

                this.paymentResponse = paymentResponse;
                paymentGateways.clear();
            if(paymentAdapter!=null)
                paymentAdapter.setFilteredData(paymentResponse.getPaymentGateways());
            paymentGateways.addAll(paymentResponse.getPaymentGateways());
            Collections.sort(paymentGateways,Utils.paymentNameComparator);
            paymentAdapter.notifyDataSetChanged();
            stopRefreshing();
//            if(clearData && mResponse.getMovies().size()>0)
//                onMovieClick(null,mResponse.getMovies().get(0),true);


        }
    }
    public void refreshData(List<PaymentGateway> paymentGateways)
    {
        this.paymentGateways.clear();
        this.paymentGateways.addAll(paymentGateways);
        paymentAdapter.notifyDataSetChanged();
        stopRefreshing();
//        if(movieList.size()>0)
//            onMovieClick(null,movies.get(0),true);

    }
    public void showSnackbar(String text)
    {
        stopRefreshing();
        Snackbar.make(rootView,text, Snackbar.LENGTH_LONG)
                .setAction(getResources().getString(R.string.text_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshContent();
                    }
                }).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_sort_rating:
                sortList(SortCriteria.RATINGS);
                break;
            case R.id.action_sort_setup_fee:
                sortList(SortCriteria.SETUPFEE);
                break;
            case R.id.action_sort_favourites:
                currentSortCriteria = SortCriteria.FAVOURITES;
                refreshData(Utils.getFavouritePayments(getActivity()));
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sortList(SortCriteria sortCriteria)
    {
        currentSortCriteria = sortCriteria;
        if(currentSortCriteria == SortCriteria.RATINGS)
        {
            Collections.sort(paymentGateways,Utils.paymentRatingComparator);
        }
        else if(currentSortCriteria == SortCriteria.SETUPFEE)
        {
            Collections.sort(paymentGateways,Utils.paymentSetupFeeComparator);
        }
        paymentAdapter.notifyDataSetChanged();
    }

    public void performSearch(String query)
    {
        if(paymentAdapter!=null)
            paymentAdapter.filter(query);
    }
}
