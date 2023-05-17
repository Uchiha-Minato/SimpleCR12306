package com.example.cr12306.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.domain.Station;

import java.util.ArrayList;

public class QueryStationAdapter extends RecyclerView.Adapter<QueryStationAdapter.ViewHolder> {

    public ArrayList<Station> stationList;
    public Context context;

    public QueryStationAdapter(ArrayList<Station> stationList, Context context) {
        this.stationList = stationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station station = stationList.get(position);
        String station_name = station.getStation_name();
        String telecode = station.getTelecode();

        holder.station_name.setText(station_name);
        holder.telecode.setText(telecode);

        if(clickListener != null) {
            holder.itemView.setOnClickListener(view -> clickListener.onItemClick(view, position));
        }
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TableLayout tableLayout;
        TextView station_name;
        TextView telecode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tableLayout = itemView.findViewById(R.id.table_item);
            station_name = itemView.findViewById(R.id.queryResult_station_name);
            telecode = itemView.findViewById(R.id.queryResult_telecode);
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
