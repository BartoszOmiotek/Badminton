package com.example.bartek.badminton.Players;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bartek.badminton.DataModels.Match;
import com.example.bartek.badminton.DataModels.Player;
import com.example.bartek.badminton.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class PlayersAdapter extends FirestoreRecyclerAdapter<Player, PlayersAdapter.PlayerHolder> {
    private OnItemClickListener listener;
    private Vector<Match> form_matches=new Vector<>(5); //vector class for thread synchronization safety

    public PlayersAdapter(@NonNull FirestoreRecyclerOptions<Player> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PlayerHolder holder, int position, @NonNull Player model) {
        Date date=model.getBirthDate();
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");

        holder.name.setText((model.getFirstName()+" "+model.getLastName()).toUpperCase());
        holder.age.setText(formatter.format(date));
        holder.height.setText(model.getHeight()+" cm");
        holder.weight.setText(model.getWeight()+" kg");
        if(model.getProfilePicture().isEmpty()){
            holder.picture.setBackgroundResource(R.drawable.empty_user);
        }


        // read doc references and map them into Match class object inside form_matches
        List<DocumentReference> form=model.getForm();
        for(DocumentReference current_match_doc: form){
            if(current_match_doc!=null){
                current_match_doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            form_matches.add(0,document.toObject(Match.class));
                        }
                        else{
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
            }
        }

        /*for(Match match: form_matches){
            if(match.getMatchWinner().getId().equals(//rozpatrywany player doc ref))

        }
        holder.player_card_form_list[i].setBackgroundResource(R.drawable.player_card_empty_icon);*/
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_card_item,parent,false);
        return new PlayerHolder(v);
    }

    class PlayerHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView age;
        private TextView weight;
        private TextView height;
        private ImageView picture;
        private List<View> player_card_form_list;

        public PlayerHolder(@NonNull final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.player_card_name);
            age=itemView.findViewById(R.id.player_card_age);
            weight=itemView.findViewById(R.id.player_card_weight);
            height=itemView.findViewById(R.id.player_card_height);
            picture=itemView.findViewById(R.id.player_card_picture);

            //populate array of player_card_form icons
            player_card_form_list= Arrays.asList(
                    itemView.findViewById(R.id.player_card_form1),
                    itemView.findViewById(R.id.player_card_form2),
                    itemView.findViewById(R.id.player_card_form3),
                    itemView.findViewById(R.id.player_card_form4),
                    itemView.findViewById(R.id.player_card_form5)
            );

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
