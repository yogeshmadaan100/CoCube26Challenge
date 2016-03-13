package com.cube26.cube26.api;

import com.cube26.cube26.models.PaymentResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yogeshmadaan on 13/03/16.
 */
public interface PaymentApi {
    @GET("payment_portals")
    Observable<PaymentResponse> fetchPaymentGateways(@Query("type") String type, @Query("query") String query);
}
