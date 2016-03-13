package com.cube26.cube26.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cube26.cube26.R;
import com.cube26.cube26.adapters.PaymentAdapter;
import com.cube26.cube26.models.PaymentGateway;
import com.cube26.cube26.models.PaymentResponse;
import com.cube26.cube26.services.PaymentService;
import com.cube26.cube26.utils.RxUtils;
import com.cube26.cube26.utils.Utils;
import com.cube26.cube26.utils.ui.SimpleDividerItemDecoration;

import java.util.ArrayList;
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
        if(paymentResponse==null)
            refreshContent();
        return rootView;
    }

    public void initViews()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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

            paymentGateways.addAll(paymentResponse.getPaymentGateways());
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
}
