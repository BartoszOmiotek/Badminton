package com.example.bartek.badminton.LeagueMenu;

import com.example.bartek.badminton.DataModels.Match;
import com.example.bartek.badminton.LiveScore.LiveScoreActivity;
import com.example.bartek.badminton.News.NewsActivity;
import com.example.bartek.badminton.Players.PlayersActivity;
import com.example.bartek.badminton.Profile.ProfileActivity;
import com.example.bartek.badminton.R;
import com.example.bartek.badminton.Table.TableActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference matches = db.collection("Leagues/TestLiga/Matches");
    private MatchAdapter matchAdapter;
    private Toolbar toolbar;
    private Button table_button;
    private Button players_button;
    private Button news_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setupRecyclerView();

        toolbar=findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(matches.getParent().getId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        table_button=findViewById(R.id.menu_table_button);
        table_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), TableActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        players_button=findViewById(R.id.menu_players_button);
        players_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), PlayersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        news_button=findViewById(R.id.menu_news_button);
        news_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), NewsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        // if recycler view with matches is empty set no_matches_info

        /*RecyclerView recyclerView = findViewById(R.id.recycler_view);
        if(recyclerView.getChildCount()==0) {
            TextView no_matches_info = findViewById(R.id.upcoming_matches_info);
            no_matches_info.setText(R.string.no_upcoming_matches_message);
        }*/
    }

    private void setupRecyclerView(){
        Date today=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            today=formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Query query=matches.orderBy("matchDate",Query.Direction.ASCENDING).limit(5)
                .whereGreaterThanOrEqualTo("matchDate",today);

        FirestoreRecyclerOptions<Match> options = new FirestoreRecyclerOptions.Builder<Match>()
                .setQuery(query, Match.class)
                .build();

        matchAdapter = new MatchAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.menu_upcoming_matches_rv);
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

    public void showProfileView(){
        Intent intent=new Intent(getApplicationContext(), ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.league_menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.action_profile:
                showProfileView();

            default:
                return super.onOptionsItemSelected(item);
        }
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
