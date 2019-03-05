package com.example.bartek.badminton.LeagueMenu;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bartek.badminton.DataModels.Match;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.example.bartek.badminton.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DateFormat;
import java.util.Locale;

public class LeagueMatchAdapter extends FirestoreRecyclerAdapter<Match, LeagueMatchAdapter.MatchHolder> {
    private OnItemClickListener listener;

    public LeagueMatchAdapter(@NonNull FirestoreRecyclerOptions<Match> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MatchHolder holder, int position, @NonNull Match model) {
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault());
        holder.match_date.setText(formatter.format(model.getMatchDate()));

        if(!model.getMatchCourt().isEmpty()) //check if there is any information about match court number
            holder.match_court_number.setText(model.getMatchCourt());
        else
            holder.match_court_number.setVisibility(View.INVISIBLE);

        holder.p1_name.setText(model.getP1Name());
        holder.p2_name.setText(model.getP2Name());

        switch (model.getMatchStatus()){
            case "0": //check if match isn't started yet {status 0}
                holder.match_status.setText("Nierozpoczęty");
                break;

            case "3": //check if match is postponed {status 3}
                holder.match_status.setText("Przełożony");
                break;

            default:
                holder.p1_s1_points.setText(model.getP1S1Points());
                holder.p1_s2_points.setText(model.getP1S2Points());
                holder.p1_s3_points.setText(model.getP1S3Points());
                holder.p1_set_result.setText(String.valueOf(model.getP1SetResult()));
                holder.p2_s1_points.setText(model.getP2S1Points());
                holder.p2_s2_points.setText(model.getP2S2Points());
                holder.p2_s3_points.setText(model.getP2S3Points());
                holder.p2_set_result.setText(String.valueOf(model.getP2SetResult()));

                switch (model.getMatchStatus()){
                    case "1":  //match is live
                        setScoreVisibility(holder.p1_set_result.getText().toString()+holder.p2_set_result.getText().toString(),holder);
                        holder.match_status.setText("");
                        holder.match_date.setText("");
                        holder.live_icon.setVisibility(View.VISIBLE); //set live icon visible
                        //holder.stream_icon.setVisibility(View.VISIBLE); set stream icon visible
                        break;

                    case "2": //match is over
                        setScoreVisibility(holder.p1_set_result.getText().toString()+holder.p2_set_result.getText().toString(),holder);
                        holder.match_court_number.setVisibility(View.INVISIBLE);
                        holder.live_icon.setVisibility(View.INVISIBLE);
                        holder.stream_icon.setVisibility(View.INVISIBLE);

                        if(holder.p2_set_result.getText().equals("2"))  //bold winner name
                            holder.p1_name.setTypeface(null, Typeface.NORMAL);
                        else if(holder.p1_set_result.getText().equals("2"))
                            holder.p2_name.setTypeface(null, Typeface.NORMAL);

                        holder.match_status.setText("Zakończony");
                        holder.match_status.setVisibility(View.VISIBLE);
                        holder.match_date.setText(formatter.format(model.getMatchDate()));
                        break;
                }
        }
    }

    private void setScoreVisibility(String setBilans, @NonNull MatchHolder holder){

        //concatenate set bilans of both player to check what's current set number
        switch (holder.p1_set_result.getText().toString()+holder.p2_set_result.getText().toString()){
            case "00":
                holder.p1_s2_points.setVisibility(View.INVISIBLE);
                holder.p2_s2_points.setVisibility(View.INVISIBLE);
                holder.p1_s3_points.setVisibility(View.INVISIBLE);
                holder.p2_s3_points.setVisibility(View.INVISIBLE);
                break;

            case "01": //join of two cases for second set
            case "10":
                holder.p1_s3_points.setVisibility(View.INVISIBLE);
                holder.p2_s3_points.setVisibility(View.INVISIBLE);
                break;

            case "11":
                break;

            case "20":
            case "02":
                holder.p1_s3_points.setVisibility(View.INVISIBLE);
                holder.p2_s3_points.setVisibility(View.INVISIBLE);
                break;

            case "12":
            case "21":
                break;
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
        private TextView p1_set_result;

        private TextView p2_name;
        private TextView p2_s1_points;
        private TextView p2_s2_points;
        private TextView p2_s3_points;
        private TextView p2_set_result;

        private TextView match_court_number;
        private TextView match_date;
        private TextView match_status;
        private TextView live_icon;
        private TextView stream_icon;

        public MatchHolder(@NonNull final View itemView) {
            super(itemView);
            p1_name=itemView.findViewById(R.id.p1_name);
            p1_s1_points=itemView.findViewById(R.id.p1_s1_points);
            p1_s2_points=itemView.findViewById(R.id.p1_s2_points);
            p1_s3_points=itemView.findViewById(R.id.p1_s3_points);
            p1_set_result =itemView.findViewById(R.id.p1_set_bilans);

            p2_name=itemView.findViewById(R.id.p2_name);
            p2_s1_points=itemView.findViewById(R.id.p2_s1_points);
            p2_s2_points=itemView.findViewById(R.id.p2_s2_points);
            p2_s3_points=itemView.findViewById(R.id.p2_s3_points);
            p2_set_result =itemView.findViewById(R.id.p2_set_bilans);

            match_court_number=itemView.findViewById(R.id.match_court_number);
            match_date=itemView.findViewById(R.id.match_date);
            match_status=itemView.findViewById(R.id.match_status);
            live_icon=itemView.findViewById(R.id.live_icon);
            stream_icon=itemView.findViewById(R.id.stream_icon);

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
