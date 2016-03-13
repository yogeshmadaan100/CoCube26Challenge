package com.cube26.cube26.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cube26.cube26.models.PaymentGateway;

import java.util.Comparator;

/**
 * Created by yogeshmadaan on 13/03/16.
 */
public class Utils {

    public static void shareUsingApps(Context context, String subject, String text) {
//        String shareBody = "Here is the share content body";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, "Share Using"));

    }

    public static boolean isConnectedToInternet(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static Comparator<PaymentGateway> paymentRatingComparator = new Comparator<PaymentGateway>() {
        @Override
        public int compare(PaymentGateway lhs, PaymentGateway rhs) {
            Float rating1 = Float.parseFloat(lhs.getRating());
            Float rating2 = Float.parseFloat(rhs.getRating());
            return rating1.compareTo(rating2);
        }
    };

    public static Comparator<PaymentGateway> paymentSetupFeeComparator = new Comparator<PaymentGateway>() {
        @Override
        public int compare(PaymentGateway lhs, PaymentGateway rhs) {
            Float setupFee1 = Float.parseFloat(lhs.getSetupFee());
            Float setupFee2 = Float.parseFloat(rhs.getSetupFee());
            return setupFee1.compareTo(setupFee2);
        }
    };
    public static Comparator<PaymentGateway> paymentNameComparator = new Comparator<PaymentGateway>() {
        @Override
        public int compare(PaymentGateway lhs, PaymentGateway rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    };

    public static Integer getDpInPxUnits(Context ctxt, Integer dps) {
        float scale = ctxt.getResources().getDisplayMetrics().density;
        Integer px = (int) (dps * scale + 0.5f);
        return px;
    }
}
