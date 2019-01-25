package com.example.bartek.badminton.Menu;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bartek.badminton.LiveScore.Match;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.example.bartek.badminton.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MatchAdapter extends FirestoreRecyclerAdapter<Match, MatchAdapter.MatchHolder> {
    private OnItemClickListener listener;

    public MatchAdapter(@NonNull FirestoreRecyclerOptions<Match> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MatchHolder holder, int position, @NonNull Match model) {
        Date date=model.getMatchDate();
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        holder.match_date.setText(formatter.format(date));

        holder.p1_name.setText(model.getP1Name());
        holder.p1_s1_points.setText(model.getP1S1Points());
        holder.p1_s2_points.setText(model.getP1S2Points());
        holder.p1_s3_points.setText(model.getP1S3Points());
        holder.p1_set_bilans.setText(String.valueOf(model.getP1SetBilans()));

        holder.p2_name.setText(model.getP2Name());
        holder.p2_s1_points.setText(model.getP2S1Points());
        holder.p2_s2_points.setText(model.getP2S2Points());
        holder.p2_s3_points.setText(model.getP2S3Points());
        holder.p2_set_bilans.setText(String.valueOf(model.getP2SetBilans()));

        //if match is not started yet ;status 0
        if(model.getMatchStatus().equals("0")){
            //holder.match_status.setText("Początek o");
            setScoreInvisibility(View.INVISIBLE,holder);
        }
        //if match is live ;status 1
        else if(model.getMatchStatus().equals("1")){
            holder.match_status.setText("Na żywo");
            holder.match_date.setText("");
            //holder.match_date.setBackgroundResource(R.color.colorLive);
            setScoreInvisibility(View.VISIBLE,holder);
        }

        //if match is over ;status 2
        else if(model.getMatchStatus().equals("2")){
            holder.match_status.setText("Zakończony");
            holder.match_date.setText(formatter.format(date));
            setScoreInvisibility(View.VISIBLE,holder);

            //set looser name font to not bolded
            if(holder.p2_name.getText().equals("2"))
                holder.p2_name.setTypeface(null, Typeface.NORMAL);
            else
                holder.p1_name.setTypeface(null, Typeface.NORMAL);

        }

        //if match is postponed ;status 3
        else if(model.getMatchStatus().equals("3")){
            holder.match_status.setText("Przełożony");
            setScoreInvisibility(View.INVISIBLE,holder);
        }
    }

    private void setScoreInvisibility(int visibility, @NonNull MatchHolder holder){
        if(visibility==View.INVISIBLE){
            holder.p1_s1_points.setVisibility(View.INVISIBLE);
            holder.p1_s2_points.setVisibility(View.INVISIBLE);
            holder.p1_s3_points.setVisibility(View.INVISIBLE);
            holder.p1_set_bilans.setVisibility(View.INVISIBLE);

            holder.p2_s1_points.setVisibility(View.INVISIBLE);
            holder.p2_s2_points.setVisibility(View.INVISIBLE);
            holder.p2_s3_points.setVisibility(View.INVISIBLE);
            holder.p2_set_bilans.setVisibility(View.INVISIBLE);
        }
        else{
            holder.p1_s1_points.setVisibility(View.VISIBLE);
            holder.p1_s2_points.setVisibility(View.VISIBLE);
            holder.p1_s3_points.setVisibility(View.VISIBLE);
            holder.p1_set_bilans.setVisibility(View.VISIBLE);

            holder.p2_s1_points.setVisibility(View.VISIBLE);
            holder.p2_s2_points.setVisibility(View.VISIBLE);
            holder.p2_s3_points.setVisibility(View.VISIBLE);
            holder.p2_set_bilans.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_card_item,parent,false);
        return new MatchHolder(v);
    }

    class MatchHolder extends RecyclerView.ViewHolder{
        private TextView p1_name;
        private TextView p1_s1_points;
        private TextView p1_s2_points;
        private TextView p1_s3_points;
        private TextView p1_set_bilans;

        private TextView p2_name;
        private TextView p2_s1_points;
        private TextView p2_s2_points;
        private TextView p2_s3_points;
        private TextView p2_set_bilans;

        private TextView match_date;
        private TextView match_status;

        public MatchHolder(@NonNull final View itemView) {
            super(itemView);
            p1_name=itemView.findViewById(R.id.p1_name);
            p1_s1_points=itemView.findViewById(R.id.p1_s1_points);
            p1_s2_points=itemView.findViewById(R.id.p1_s2_points);
            p1_s3_points=itemView.findViewById(R.id.p1_s3_points);
            p1_set_bilans=itemView.findViewById(R.id.p1_set_bilans);

            p2_name=itemView.findViewById(R.id.p2_name);
            p2_s1_points=itemView.findViewById(R.id.p2_s1_points);
            p2_s2_points=itemView.findViewById(R.id.p2_s2_points);
            p2_s3_points=itemView.findViewById(R.id.p2_s3_points);
            p2_set_bilans=itemView.findViewById(R.id.p2_set_bilans);

            match_date=itemView.findViewById(R.id.match_date);
            match_status=itemView.findViewById(R.id.match_status);

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
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
