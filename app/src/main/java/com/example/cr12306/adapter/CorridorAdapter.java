package com.example.cr12306.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.domain.CorridorDetail;

import java.util.ArrayList;

public class CorridorAdapter extends RecyclerView.Adapter<CorridorAdapter.ViewHolder> {

    private final ArrayList<CorridorDetail> corridorDetails;

    public CorridorAdapter(ArrayList<CorridorDetail> corridorDetails) {
        this.corridorDetails = corridorDetails;
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
        String line_name = corridorDetails.get(position).getLine_name();
        String from_to = corridorDetails.get(position).getFrom_to();

        holder.line_name.setText(line_name);
        holder.from_to.setText(from_to);
    }

    @Override
    public int getItemCount() {
        return corridorDetails.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView line_name;
        TextView from_to;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            line_name = itemView.findViewById(R.id.station_name);
            from_to = itemView.findViewById(R.id.distance);
        }
    }
}
