package com.example.cr12306.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.domain.LeftTicket;


import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final ArrayList<LeftTicket> ticketItem;

    public TicketAdapter(ArrayList<LeftTicket> item) {
        this.ticketItem = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.train_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeftTicket ticket = ticketItem.get(position);
        holder.station_train_code.setText(ticket.getStation_train_code());
        holder.from_to.setText(ticket.getStart_station_name() + "-" + ticket.getEnd_station_name());
        holder.from_station_name.setText(ticket.getFrom_station_name());
        holder.start_time.setText(ticket.getStart_time());
        holder.to_station_name.setText(ticket.getTo_station_name());
        holder.arrive_time.setText(ticket.getArrive_time());
        holder.lishi.setText(ticket.getLishi());
        //holder.msg.setText();
        //判断票价信息然后显示
        StringBuilder builder = new StringBuilder();
        if(ticket.isSwz_num())
            builder.append("商务座:￥").append(ticket.getSwz_price()).append("  ");
        if(ticket.isZy_num())
            builder.append("一等座:￥").append(ticket.getZy_price()).append("  ");
        if(ticket.isZe_num())
            builder.append("二等座:￥").append(ticket.getZe_price()).append("  ");
        if(ticket.isSrrb_num())
            builder.append("动卧:￥").append(ticket.getSrrb_price()).append("  ");
        if(ticket.isGr_num())
            builder.append("高级软卧:￥").append(ticket.getGr_price()).append("  ");
        if(ticket.isRw_num())
            builder.append("软卧:￥").append(ticket.getRw_price()).append("  ");
        if(ticket.isYw_num())
            builder.append("硬卧:￥").append(ticket.getYw_price()).append("  ");
        if(ticket.isYz_num())
            builder.append("硬座:￥").append(ticket.getYz_price()).append("  ");
        if(ticket.isWz_num())
            builder.append("无座:￥").append(ticket.getWz_price()).append("  ");
        holder.msg.setText(builder);


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
