package com.cube26.cube26.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cube26.cube26.R;
import com.cube26.cube26.models.PaymentGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by yogeshmadaan on 13/03/16.
 */
public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>{

    Context context = null;
    List<PaymentGateway> paymentGateways = null;
    List<PaymentGateway> filterPaymentGateways = null;
    private PaymentClickInterface paymentClickInterface;

    public PaymentAdapter(Context context, List<PaymentGateway> paymentGateways, PaymentClickInterface paymentClickInterface)
    {
        this.context = context;
        this.paymentGateways = paymentGateways;
        this.paymentClickInterface = paymentClickInterface;
        this.filterPaymentGateways = new ArrayList<>();
        this.filterPaymentGateways.addAll(paymentGateways);
        Log.e("setting filter payment size",""+filterPaymentGateways.size());
    }
    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_payment, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new PaymentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PaymentViewHolder holder, final int position) {
        Glide.with(context).load(paymentGateways.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentClickInterface.onPaymentClick(holder.itemView,paymentGateways.get(position),false);
            }
        });
        holder.paymentName.setText(paymentGateways.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return paymentGateways.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView paymentName;
        public View itemView;
        public PaymentViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_payment);
            paymentName = (TextView) itemView.findViewById(R.id.text_payment_name);
            this.itemView = itemView;
        }

    }

    public interface PaymentClickInterface
    {
        void onPaymentClick(View itemView, PaymentGateway paymentGateway, boolean isDefaultSelection);
    }

    synchronized  public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        paymentGateways.clear();
        if (charText.length() == 0) {
            paymentGateways.addAll(filterPaymentGateways);
            Log.e("char text","0");
        } else {
            Log.e("filter payment size",""+filterPaymentGateways.size());
            for (PaymentGateway wp : filterPaymentGateways) {
                Log.e("does "+wp.getName(),""+wp.getName().toLowerCase(Locale.getDefault()).contains(charText));
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    paymentGateways.add(wp);
                }
            }
        }
        Log.e("search result size",""+paymentGateways.size());
        notifyDataSetChanged();
    }

    public void setFilteredData(List<PaymentGateway> paymentGateways)
    {
        this.filterPaymentGateways = new ArrayList<>();
        this.filterPaymentGateways.addAll(paymentGateways);
        Log.e("setting filter payment size",""+filterPaymentGateways.size());
    }
}
