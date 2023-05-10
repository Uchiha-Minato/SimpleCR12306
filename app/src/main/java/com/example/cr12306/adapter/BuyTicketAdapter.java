package com.example.cr12306.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.activities.tickets.ConfirmOrderActivity;
import com.example.cr12306.domain.BuyTicket;

import java.util.ArrayList;

public class BuyTicketAdapter extends RecyclerView.Adapter<BuyTicketAdapter.ViewHolder> {
    public ArrayList<BuyTicket> buyTicketArrayList;
    public Context context;

    public BuyTicketAdapter(ArrayList<BuyTicket> ticket, Context context) {
        this.buyTicketArrayList = ticket;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_buy_ticket, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuyTicket buyTicket = buyTicketArrayList.get(position);
        holder.seat_type.setText(buyTicket.getSeat_type());
        holder.price.setText("￥" + buyTicket.getPrice());

        holder.buy_ticket.setOnClickListener(view -> {
            Intent intent = new Intent(context.getApplicationContext(), ConfirmOrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("confirm", buyTicket);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return buyTicketArrayList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView seat_type;
        TextView price;
        Button buy_ticket;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seat_type = itemView.findViewById(R.id.seat_type);
            price = itemView.findViewById(R.id.price);
            buy_ticket = itemView.findViewById(R.id.buy_ticket);
        }
    }
}
