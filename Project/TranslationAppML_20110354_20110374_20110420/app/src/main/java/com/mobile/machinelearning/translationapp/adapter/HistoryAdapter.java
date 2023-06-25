package com.mobile.machinelearning.translationapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.machinelearning.translationapp.R;
import com.mobile.machinelearning.translationapp.model.HistoryItem;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<HistoryItem> historyItems;
    public void setFilteredList(List<HistoryItem> filteredList) {
        this.historyItems = filteredList;
        notifyDataSetChanged();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView keywordTextView;
        public TextView resultTextView;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            keywordTextView = itemView.findViewById(R.id.text_view_keyword);
            resultTextView = itemView.findViewById(R.id.text_view_result);
        }
    }

    public HistoryAdapter(List<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        HistoryViewHolder historyViewHolder = new HistoryViewHolder(v);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        HistoryItem currentItem = historyItems.get(position);
        holder.keywordTextView.setText(currentItem.getKeyword());
        holder.resultTextView.setText(currentItem.getResult());
    }
    @Override
    public int getItemCount() {
        return historyItems.size();
    }
}
