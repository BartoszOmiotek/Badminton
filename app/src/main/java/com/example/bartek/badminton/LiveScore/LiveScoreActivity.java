package com.example.bartek.badminton.LiveScore;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.bartek.badminton.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LiveScoreActivity extends AppCompatActivity {

    private LiveScorePresenter presenter;
    private Match match;
    private DocumentReference match_doc_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent=getIntent();
        match_doc_ref= FirebaseFirestore.getInstance().document(intent.getStringExtra("match_doc_path"));
        presenter=new LiveScorePresenter(this,match_doc_ref);

        match_doc_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    match=document.toObject(Match.class);
                    initValues(match);
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    private void initValues(Match match){
        TextView p1_name = this.findViewById(R.id.p1_name);
        p1_name.setText(match.getP1Name());
        TextView p1_court_top=this.findViewById(R.id.p1_court_top);
        p1_court_top.setText(match.getP1Name());
        TextView p1_court_down=this.findViewById(R.id.p1_court_bot);
        p1_court_down.setText(match.getP1Name());

        TextView p2_name = this.findViewById(R.id.p2_name);
        p2_name.setText(match.getP2Name());
        TextView p2_court_top=this.findViewById(R.id.p2_court_top);
        p2_court_top.setText(match.getP2Name());
        TextView p2_court_down=this.findViewById(R.id.p2_court_bot);
        p2_court_down.setText(match.getP2Name());
    }
}
