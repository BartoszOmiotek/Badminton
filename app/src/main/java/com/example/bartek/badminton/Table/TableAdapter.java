package com.example.bartek.badminton.Table;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bartek.badminton.DataModels.Player;
import com.example.bartek.badminton.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class TableAdapter extends FirestoreRecyclerAdapter<Player, TableAdapter.PlayerHolder> {
    private OnItemClickListener listener;

    public TableAdapter(@NonNull FirestoreRecyclerOptions<Player> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PlayerHolder holder, int position, @NonNull Player model) {
        holder.position.setText((holder.getAdapterPosition()+1)+".");  // FIX ME -- when position is table has changed, it doubles position number with previous player
        holder.name.setText(model.getLastName().toUpperCase());
        holder.sets_balance.setText(model.getSetsWon()+":"+model.getSetsLost());
        holder.matches.setText(String.valueOf(model.getMatchesWon()+model.getMatchesLost()));
        holder.matches_won.setText(String.valueOf(model.getMatchesWon()));
        holder.matches_lost.setText(String.valueOf(model.getMatchesLost()));
        holder.points.setText(String.valueOf(model.getPoints()));
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_card_item,parent,false);
        return new PlayerHolder(v);
    }

    class PlayerHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView matches;
        private TextView matches_won;
        private TextView matches_lost;
        private TextView sets_balance;
        private TextView points;
        private TextView position;

        public PlayerHolder(@NonNull final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.table_player_name);
            matches=itemView.findViewById(R.id.table_matches);
            matches_won=itemView.findViewById(R.id.table_matches_won);
            matches_lost=itemView.findViewById(R.id.table_matches_lost);
            sets_balance =itemView.findViewById(R.id.table_sets_balance);
            points=itemView.findViewById(R.id.table_points);
            position=itemView.findViewById(R.id.table_position);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                    /*match_doc_id=getSnapshots().getSnapshot(position).getId();
                    Intent intent=new Intent(itemView.getContext(),LiveScoreActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("match_doc_id",match_doc_id);
                    itemView.getContext().startActivity(intent);*/
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
