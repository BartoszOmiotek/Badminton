package com.example.bartek.badminton.Menu;

import com.example.bartek.badminton.LiveScore.LiveScoreActivity;
import com.example.bartek.badminton.LiveScore.Match;
import com.example.bartek.badminton.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference matches = db.collection("Leagues/TestLiga/Matches");

    private MatchAdapter matchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        Date today=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            today=formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Query query=matches.orderBy("match_date",Query.Direction.ASCENDING).limit(5)
                .whereGreaterThanOrEqualTo("match_date",today);

        FirestoreRecyclerOptions<Match> options = new FirestoreRecyclerOptions.Builder<Match>()
                .setQuery(query,Match.class)
                .build();

        matchAdapter = new MatchAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(matchAdapter);

        matchAdapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
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
        matchAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        matchAdapter.stopListening();
    }
}
