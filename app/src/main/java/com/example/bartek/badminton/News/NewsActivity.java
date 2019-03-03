package com.example.bartek.badminton.News;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.bartek.badminton.DataModels.News;
import com.example.bartek.badminton.LiveScore.LiveScoreActivity;
import com.example.bartek.badminton.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class NewsActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference news = db.collection("Leagues/TestLiga/News");
    private NewsAdapter newsAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setupRecyclerView();

        toolbar=findViewById(R.id.news_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Aktualności");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setupRecyclerView(){

        Query query=news.orderBy("newsDate",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<News> options = new FirestoreRecyclerOptions.Builder<News>()
                .setQuery(query, News.class)
                .build();

        newsAdapter = new NewsAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.news_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String match_doc_path = documentSnapshot.getReference().getPath();
                Intent intent=new Intent(getApplicationContext(), LiveScoreActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("match_doc_path",match_doc_path);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        newsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        newsAdapter.stopListening();
    }
}
