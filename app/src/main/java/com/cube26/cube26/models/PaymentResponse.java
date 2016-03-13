package com.cube26.cube26.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogeshmadaan on 13/03/16.
 */
public class PaymentResponse {

    @SerializedName("payment_gateways")
    @Expose
    private List<PaymentGateway> paymentGateways = new ArrayList<PaymentGateway>();

    /**
     *
     * @return
     * The paymentGateways
     */
    public List<PaymentGateway> getPaymentGateways() {
        return paymentGateways;
    }

    /**
     *
     * @param paymentGateways
     * The payment_gateways
     */
    public void setPaymentGateways(List<PaymentGateway> paymentGateways) {
        this.paymentGateways = paymentGateways;
    }

}
