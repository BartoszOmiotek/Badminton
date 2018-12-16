package com.example.bartek.badminton.LiveScore;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bartek.badminton.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class LiveScoreActivity extends AppCompatActivity {

    private LiveScorePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        presenter=new LiveScorePresenter(this);
        presenter.init();
    }
}

