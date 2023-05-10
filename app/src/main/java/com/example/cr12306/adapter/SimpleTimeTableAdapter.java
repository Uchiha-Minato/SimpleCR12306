package com.example.cr12306.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.domain.TrainInfo;

import java.util.ArrayList;

public class SimpleTimeTableAdapter extends RecyclerView.Adapter<SimpleTimeTableAdapter.ViewHolder> {
    private final ArrayList<TrainInfo> simpleInfo;

    public SimpleTimeTableAdapter(ArrayList<TrainInfo> simpleInfo) {
        this.simpleInfo = simpleInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple_timetable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainInfo info = simpleInfo.get(position);
        holder.station_name.setText(info.getStation_name());
        holder.arrive_time.setText(info.getArrive_time());
        holder.train_code.setText(info.getStation_train_code());
        holder.arrive_day_str.setText(info.getArrive_day_str());
    }

    @Override
    public int getItemCount() {
        return simpleInfo.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView station_name;
        TextView arrive_time;
        TextView train_code;
        TextView arrive_day_str;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            station_name = itemView.findViewById(R.id.simpleTable_station_name);
            arrive_time = itemView.findViewById(R.id.simpleTable_arrive_time);
            train_code = itemView.findViewById(R.id.simpleTable_train_code);
            arrive_day_str = itemView.findViewById(R.id.simpleTable_arrive_day_str);
        }
    }
}
