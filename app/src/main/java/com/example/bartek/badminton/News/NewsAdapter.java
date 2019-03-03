package com.example.bartek.badminton.News;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bartek.badminton.DataModels.News;
import com.example.bartek.badminton.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsAdapter extends FirestoreRecyclerAdapter<News, NewsAdapter.NewsHolder> {

    private OnItemClickListener listener;

    public NewsAdapter(@NonNull FirestoreRecyclerOptions<News> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NewsAdapter.NewsHolder holder, int position, @NonNull News model) {
        Date date=model.getNewsDate();
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");

        holder.news_date.setText(formatter.format(date));
        holder.news_title.setText(model.getNewsTitle());
        holder.news_text.setText(model.getNewsText());
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card_item,parent,false);
        return new NewsHolder(v);
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        private TextView news_title;
        private TextView news_text;
        private TextView news_date;

        public NewsHolder(@NonNull final View itemView) {
            super(itemView);
            news_title=itemView.findViewById(R.id.news_title);
            news_text=itemView.findViewById(R.id.news_text);
            news_date=itemView.findViewById(R.id.news_date);

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
