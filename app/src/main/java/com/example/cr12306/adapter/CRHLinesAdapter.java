package com.example.cr12306.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;

import java.util.ArrayList;

public class CRHLinesAdapter extends RecyclerView.Adapter<CRHLinesAdapter.ViewHolder> {

    private final ArrayList<String> crhLines;

    public CRHLinesAdapter(ArrayList<String> crhLines) {
        this.crhLines = crhLines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String line = crhLines.get(position);
        holder.txt_line_item.setText(line);

        if(clickListener != null){
            holder.itemView.setOnClickListener(view -> clickListener.onItemClick(view, position));
        }
    }

    @Override
    public int getItemCount() {
        return crhLines.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_line_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_line_item = itemView.findViewById(R.id.line_item);
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
