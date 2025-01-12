package com.example.continentexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VisitedCountriesAdapter extends RecyclerView.Adapter<VisitedCountriesAdapter.ViewHolder> {

    private List<String> visitedCountries;

    public VisitedCountriesAdapter(List<String> visitedCountries) {
        this.visitedCountries = visitedCountries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_visited_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String countryWithDate = visitedCountries.get(position);
        holder.countryTextView.setText(countryWithDate);
    }

    @Override
    public int getItemCount() {
        return visitedCountries.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryTextView;

        ViewHolder(View itemView) {
            super(itemView);
            countryTextView = itemView.findViewById(R.id.countryTextView);
        }
    }
}
