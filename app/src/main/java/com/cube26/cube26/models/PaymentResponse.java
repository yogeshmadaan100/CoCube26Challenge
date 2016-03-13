package com.cube26.cube26.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogeshmadaan on 13/03/16.
 */
public class PaymentResponse implements Parcelable{

    @SerializedName("payment_gateways")
    @Expose
    private List<PaymentGateway> paymentGateways = new ArrayList<PaymentGateway>();

    protected PaymentResponse(Parcel in) {
        paymentGateways = in.createTypedArrayList(PaymentGateway.CREATOR);
    }

    public static final Creator<PaymentResponse> CREATOR = new Creator<PaymentResponse>() {
        @Override
        public PaymentResponse createFromParcel(Parcel in) {
            return new PaymentResponse(in);
        }

        @Override
        public PaymentResponse[] newArray(int size) {
            return new PaymentResponse[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(paymentGateways);
    }
}
