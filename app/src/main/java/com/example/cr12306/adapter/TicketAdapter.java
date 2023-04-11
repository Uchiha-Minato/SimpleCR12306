package com.example.cr12306.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.domain.TicketTest;


import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final ArrayList<TicketTest> ticketItem;

    public TicketAdapter(ArrayList<TicketTest> item) {
        this.ticketItem = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.train_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TicketTest test = ticketItem.get(position);
        holder.station_train_code.setText(test.getStation_train_code());
        holder.from_to.setText(test.getFrom_to());
        holder.from_station_name.setText(test.getFrom_station_name());
        holder.start_time.setText(test.getStart_time());
        holder.to_station_name.setText(test.getTo_station_name());
        holder.arrive_time.setText(test.getArrive_time());
        holder.lishi.setText(test.getLishi());
        holder.msg.setText(test.getTicket_msg());

        if(clickListener != null){
            holder.itemView.setOnClickListener(view -> clickListener.onItemClick(view, position));

        }
    }


    @Override
    public int getItemCount() {
        return ticketItem.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View ticketView;
        TextView station_train_code, from_to;
        TextView from_station_name, start_time;
        TextView to_station_name, arrive_time;
        TextView lishi;
        TextView msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketView = itemView;
            station_train_code = itemView.findViewById(R.id.station_train_code);
            from_to = itemView.findViewById(R.id.from_to);
            from_station_name = itemView.findViewById(R.id.from_station_name);
            start_time = itemView.findViewById(R.id.start_time);
            to_station_name = itemView.findViewById(R.id.to_station_name);
            arrive_time = itemView.findViewById(R.id.arrive_time);
            lishi = itemView.findViewById(R.id.lishi);
            msg = itemView.findViewById(R.id.ticket_msg);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener listener){
        clickListener = listener;
    }
}
