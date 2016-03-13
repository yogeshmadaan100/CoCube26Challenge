package com.cube26.cube26.services;

import android.content.Context;

import com.cube26.cube26.api.PaymentApi;
import com.cube26.cube26.generators.ServiceGenerator;

/**
 * Created by yogeshmadaan on 13/03/16.
 */
public class PaymentService {
    PaymentApi paymentApi = null;

    public PaymentService(Context context)
    {
        paymentApi = ServiceGenerator.createService(PaymentApi.class,context);
    }

    public PaymentApi getPaymentApi()
    {
        return paymentApi;
    }
}
