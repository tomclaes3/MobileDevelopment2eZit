package com.example.tom.mineclicker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class HighscoreAdapter extends RecyclerView.Adapter<HighscoreAdapter.ViewHolder> {

    public static final String KEY_RANK = "rank";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_CLICKS = "clicks";
    public static final String KEY_FLOOR = "floor";
    public static final String KEY_COUNTRY = "country";

    private List<HighscoreModel> highscoreLists;
    private Context context;

    public HighscoreAdapter(List<HighscoreModel> highscoreList, Context context){
        this.highscoreLists = highscoreList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)  {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_highscoreitem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final HighscoreModel highscoreList = highscoreLists.get(position);
        System.out.println("LOGGING: " + String.valueOf(highscoreList.getRank()));
        holder.rank.setText(String.valueOf(highscoreList.getRank()));
        holder.username.setText(String.valueOf(highscoreList.getUsername()));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighscoreModel highscoreListItem = highscoreLists.get(position);
                Intent skipIntent = new Intent(v.getContext(), HighscoreActivity.class);
                skipIntent.putExtra(KEY_RANK, String.valueOf(highscoreListItem.getRank()));
                skipIntent.putExtra(KEY_USERNAME, String.valueOf(highscoreListItem.getUsername()));
                skipIntent.putExtra(KEY_CLICKS, String.valueOf(highscoreListItem.getClicks()));
                skipIntent.putExtra(KEY_FLOOR, String.valueOf(highscoreListItem.getFloor()));
                skipIntent.putExtra(KEY_COUNTRY, String.valueOf(highscoreListItem.getCountry()));
                v.getContext().startActivity(skipIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return highscoreLists.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView rank;
        public TextView username;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView){
            super(itemView);

            rank = itemView.findViewById(R.id.rank);
            username = itemView.findViewById(R.id.usernameLabel);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}