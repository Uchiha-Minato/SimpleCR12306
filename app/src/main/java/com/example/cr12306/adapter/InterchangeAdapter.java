package com.example.cr12306.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.domain.Interchange;

import java.util.ArrayList;

public class InterchangeAdapter extends RecyclerView.Adapter<InterchangeAdapter.ViewHolder> {

    private final ArrayList<Interchange> list;

    public InterchangeAdapter(ArrayList<Interchange> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interchange, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Interchange plan = list.get(position);
        holder.from_station_name.setText(plan.getFrom_station_name());
        holder.start_time.setText(plan.getFirst_start_time());
        holder.all_lishi.setText(plan.getAll_lishi());
        holder.to_station_name.setText(plan.getTo_station_name());
        holder.arrive_time.setText(plan.getArrive_time());
        holder.middle_station.setText("中转站(地)：" + plan.getMiddle_station());
        holder.first_trip.setText(plan.getFirst_station_train_code()
                + " " + plan.getFirst_from_station_name() + "-" + plan.getFirst_to_station_name());
        holder.second_trip.setText(plan.getSecond_station_train_code()
                + " " + plan.getSecond_from_station_name() + "-" + plan.getSecond_to_station_name());

        if(clickListener != null){
            holder.itemView.setOnClickListener(view -> clickListener.onItemClick(view, position));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView from_station_name, start_time, all_lishi, to_station_name;
        TextView arrive_time, middle_station, first_trip, second_trip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            from_station_name = itemView.findViewById(R.id.trans_from_station_name);
            start_time = itemView.findViewById(R.id.trans_start_time);
            all_lishi = itemView.findViewById(R.id.all_lishi);
            to_station_name = itemView.findViewById(R.id.trans_to_station_name);
            arrive_time = itemView.findViewById(R.id.trans_arrive_time);
            middle_station = itemView.findViewById(R.id.middle_station);
            first_trip = itemView.findViewById(R.id.first_trip);
            second_trip = itemView.findViewById(R.id.second_trip);
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
