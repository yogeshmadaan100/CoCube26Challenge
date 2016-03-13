package com.cube26.cube26.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import com.cube26.cube26.R;
import com.cube26.cube26.models.PaymentGateway;
import com.cube26.cube26.provider.PaymentProvider;
import com.cube26.cube26.provider.PaymentSQLiteHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public static int toggleFavourite(Context context, PaymentGateway paymentGateway)
    {

        Uri.Builder uriBuilder = PaymentProvider.CONTENT_URI.buildUpon();

        if(isFavourite(context, paymentGateway))
            context.getContentResolver().delete(uriBuilder.build(),String.valueOf(paymentGateway.getId()),null);
        else
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PaymentSQLiteHelper.ID, paymentGateway.getId());
            contentValues.put(PaymentSQLiteHelper.TITLE, paymentGateway.getName());
            contentValues.put(PaymentSQLiteHelper.IMAGE_PATH, paymentGateway.getImage());
            contentValues.put(PaymentSQLiteHelper.DESCRIPTION, paymentGateway.getDescription());
            contentValues.put(PaymentSQLiteHelper.DOCUMENT, paymentGateway.getHowToDocument());
            contentValues.put(PaymentSQLiteHelper.SETUP_FEE, paymentGateway.getSetupFee());
            contentValues.put(PaymentSQLiteHelper.TRANSACTION_FEE, paymentGateway.getTransactionFees());
            contentValues.put(PaymentSQLiteHelper.CURRENCY, paymentGateway.getCurrencies());
            context.getContentResolver().insert(PaymentProvider.CONTENT_URI, contentValues);
        }
        return 0;
    }

    public static boolean isFavourite(Context context,PaymentGateway paymentGateway)
    {
        String URL = PaymentProvider.URL;
        Uri movies = Uri.parse(URL);
        Cursor cursor = null;
        cursor = context.getContentResolver().query(movies, null, PaymentSQLiteHelper.ID+" = "+paymentGateway.getId(), null, PaymentSQLiteHelper.ROW_ID);
        if (cursor != null&&cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }

    public static List<PaymentGateway> getFavouritePayments(Context context)
    {
        List<PaymentGateway> paymentGateways = new ArrayList<>();
        String URL = PaymentProvider.URL;
        Uri url = Uri.parse(URL);
        Cursor cursor = null;
        cursor = context.getContentResolver().query(url, null, null, null, PaymentSQLiteHelper.ROW_ID);
        if (cursor != null) {
            while (cursor.moveToNext())
            {
                PaymentGateway paymentGateway = new PaymentGateway();
                paymentGateway.setId(cursor.getString(cursor.getColumnIndex(PaymentSQLiteHelper.ID)));
                paymentGateway.setName(cursor.getString(cursor.getColumnIndex(PaymentSQLiteHelper.TITLE)));
                paymentGateway.setImage(cursor.getString(cursor.getColumnIndex(PaymentSQLiteHelper.IMAGE_PATH)));
                paymentGateway.setDescription(cursor.getString(cursor.getColumnIndex(PaymentSQLiteHelper.DESCRIPTION)));
                paymentGateway.setHowToDocument(cursor.getString(cursor.getColumnIndex(PaymentSQLiteHelper.DOCUMENT)));
                paymentGateway.setCurrencies(cursor.getString(cursor.getColumnIndex(PaymentSQLiteHelper.CURRENCY)));
                paymentGateway.setTransactionFees(cursor.getString(cursor.getColumnIndex(PaymentSQLiteHelper.TRANSACTION_FEE)));
                paymentGateway.setSetupFee(cursor.getString(cursor.getColumnIndex(PaymentSQLiteHelper.SETUP_FEE)));
                paymentGateways.add(paymentGateway);
            }
        }
        if(paymentGateways.size()==0)
            Toast.makeText(context,context.getResources().getString(R.string.text_no_favourites),Toast.LENGTH_LONG).show();
        return paymentGateways;
    }
}
