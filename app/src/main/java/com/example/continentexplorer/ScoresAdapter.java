package com.example.continentexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.continentexplorer.R;
import com.example.continentexplorer.dto.Score;

import java.util.List;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoreViewHolder> {

    private final List<Score> scores;

    public ScoresAdapter(List<Score> scores) {
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = scores.get(position);

        // Setează textul pentru dată și scor
        holder.dateTextView.setText(score.getAttemptTime());
        holder.scoreTextView.setText(score.getTotalScore() + "p");

        // Alege imaginea în funcție de tabel
        if (score.isFromCountiesGame()) {
            holder.mapImageView.setImageResource(R.drawable.ic_map_romania); // Imagine România
        } else {
            holder.mapImageView.setImageResource(R.drawable.ic_map_europa); // Imagine Europa
        }
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        ImageView mapImageView;
        TextView dateTextView;
        TextView scoreTextView;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            mapImageView = itemView.findViewById(R.id.mapImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }

}
