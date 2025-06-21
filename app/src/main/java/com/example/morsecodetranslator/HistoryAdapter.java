package com.example.morsecodetranslator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final List<TranslationHistory> historyList;
    private final Context context;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public HistoryAdapter(Context context, List<TranslationHistory> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TranslationHistory history = historyList.get(position);
        String operationType = history.isEncoding() ? "Kodowanie" : "Dekodowanie";
        holder.inputText.setText(operationType + ": " + history.getInput());
        holder.outputText.setText(history.getOutput());
        
        holder.itemView.setSelected(position == selectedPosition);
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && listener != null) {
                setSelectedPosition(adapterPosition);
                listener.onItemClick(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void setSelectedPosition(int position) {
        int oldPosition = selectedPosition;
        selectedPosition = position;
        if (oldPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(oldPosition);
        }
        if (position != RecyclerView.NO_POSITION) {
            notifyItemChanged(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView inputText;
        TextView outputText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inputText = itemView.findViewById(R.id.inputText);
            outputText = itemView.findViewById(R.id.outputText);
        }
    }
} 