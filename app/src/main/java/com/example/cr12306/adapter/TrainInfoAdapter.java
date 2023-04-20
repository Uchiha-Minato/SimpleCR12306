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

public class TrainInfoAdapter extends RecyclerView.Adapter<TrainInfoAdapter.ViewHolder> {

    private final ArrayList<TrainInfo> infoList;

    public TrainInfoAdapter(ArrayList<TrainInfo> infoList) {
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_query_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainInfo infoItem = infoList.get(position);
        holder.txt_station_name.setText(infoItem.getStation_name());
        holder.txt_train_code.setText(infoItem.getStation_train_code());
        holder.txt_arrive_time.setText(infoItem.getArrive_time());
        holder.txt_start_time.setText(infoItem.getStart_time());
        holder.txt_lishi.setText(infoItem.getRunning_time());
        holder.txt_arrive_day_str.setText(infoItem.getArrive_day_str());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_station_name, txt_train_code, txt_arrive_time,
                txt_start_time, txt_lishi, txt_arrive_day_str;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_station_name = itemView.findViewById(R.id.info_station_name);
            txt_train_code = itemView.findViewById(R.id.info_station_train_code);
            txt_arrive_time = itemView.findViewById(R.id.info_arrive_time);
            txt_start_time = itemView.findViewById(R.id.info_start_time);
            txt_lishi = itemView.findViewById(R.id.info_lishi);
            txt_arrive_day_str = itemView.findViewById(R.id.info_arrive_day_str);
        }
    }
}
