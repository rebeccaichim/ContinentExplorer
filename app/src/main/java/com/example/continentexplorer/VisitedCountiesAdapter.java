package com.example.continentexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VisitedCountiesAdapter extends RecyclerView.Adapter<VisitedCountiesAdapter.ViewHolder> {

    private List<String> visitedCounties;

    public VisitedCountiesAdapter(List<String> visitedCounties) {
        this.visitedCounties = visitedCounties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_visited_county, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String county = visitedCounties.get(position);
        holder.countyTextView.setText(county);
    }

    @Override
    public int getItemCount() {
        return visitedCounties.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countyTextView;

        ViewHolder(View itemView) {
            super(itemView);
            countyTextView = itemView.findViewById(R.id.countyTextView);
        }
    }
}
