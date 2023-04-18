package com.example.cr12306.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.domain.DistanceDetail;

import java.util.ArrayList;

public class DistanceAdapter extends RecyclerView.Adapter<DistanceAdapter.ViewHolder> {

    private final ArrayList<DistanceDetail> line_detail;

    public DistanceAdapter(ArrayList<DistanceDetail> line_detail) {
        this.line_detail = line_detail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.distance_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String station_name = line_detail.get(position).getStation();
        int distance = line_detail.get(position).getDistance();

        holder.station_name.setText(station_name);
        holder.distance.setText(distance + "");

        if(clickListener != null) {
            holder.itemView.setOnClickListener(view -> clickListener.onItemClick(view, position));
        }
    }

    @Override
    public int getItemCount() {
        return line_detail.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView station_name;
        TextView distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            station_name = itemView.findViewById(R.id.station_name);
            distance = itemView.findViewById(R.id.distance);
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
