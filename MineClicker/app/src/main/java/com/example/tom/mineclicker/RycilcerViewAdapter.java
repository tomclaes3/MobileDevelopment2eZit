package com.example.tom.mineclicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RycilcerViewAdapter extends RecyclerView.Adapter<RycilcerViewAdapter.ViewHolder>{
    private static final String TAG = "RycilcerViewAdapter";
    private ArrayList<String> ranks ;
    private ArrayList<String> usernames;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
    void onItemClick(int possision, String username);
    }
    public void setOnClickLisener(OnItemClickListener lisener){
        mListener = lisener;
    }

    public RycilcerViewAdapter(ArrayList<String> ranks, ArrayList<String> usernames, Context context) {
        this.ranks = ranks;
        this.usernames = usernames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_highscoreitem, viewGroup , false);
        ViewHolder viewholder = new ViewHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: ");
        viewHolder.rank.setText(ranks.get(i));
       viewHolder.name.setText((usernames.get(i)));
      //viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
      //    @Override
      //    public void onClick(View view) {

      //    }
     // });

    }

    @Override
    public int getItemCount() {
        return ranks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView rank;
        TextView name;
        RelativeLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            name = itemView.findViewById(R.id.usernameLabel);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position, usernames.get(position));
                        }
                    }
                }
            });
        }
    }
}
