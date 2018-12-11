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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class LiveScoreActivity extends AppCompatActivity implements LiveScoreContract.MvpView{

    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    private Button p1_inc_point;
    private Button p1_dec_point;
    private TextView p1_points;

    private Button p2_inc_point;
    private Button p2_dec_point;
    private TextView p2_points;

    private TextView p1_set1_points;
    private TextView p1_set2_points;
    private TextView p1_set3_points;

    private TextView p2_set1_points;
    private TextView p2_set2_points;
    private TextView p2_set3_points;

    private TextView p1_set_bilans;
    private TextView p2_set_bilans;

    private int current_set; // describes number representation of current set

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        LiveScorePresenter presenter=new LiveScorePresenter(this);

        p1_inc_point=findViewById(R.id.p1_inc_point_btn);
        p1_dec_point=findViewById(R.id.p1_dec_point_btn);
        p1_points=findViewById(R.id.p1_points);

        p2_inc_point=findViewById(R.id.p2_inc_point_btn);
        p2_dec_point=findViewById(R.id.p2_dec_point_btn);
        p2_points=findViewById(R.id.p2_points);

        p1_set1_points=findViewById(R.id.p1_set1_points);
        p1_set2_points=findViewById(R.id.p1_set2_points);
        p1_set3_points=findViewById(R.id.p1_set3_points);

        p2_set1_points=findViewById(R.id.p2_set1_points);
        p2_set2_points=findViewById(R.id.p2_set2_points);
        p2_set3_points=findViewById(R.id.p2_set3_points);

        p1_set_bilans=findViewById(R.id.p1_set_bilans);
        p2_set_bilans=findViewById(R.id.p2_set_bilans);

        current_set=1;

        p1_inc_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p1_points.getText().toString()); // get current points
                points+=1; // increase points by 1
                p1_points.setText(String.valueOf(points));
                check_set_winner();
            }
        });
        p1_dec_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p1_points.getText().toString()); // get current points
                if(points>0)
                    points-=1; // decrease points by 1
                p1_points.setText(String.valueOf(points));
                check_set_winner();
            }
        });

        p2_inc_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p2_points.getText().toString()); // get current points
                points+=1; // increase points by 1
                p2_points.setText(String.valueOf(points));
                check_set_winner();
            }
        });
        p2_dec_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p2_points.getText().toString()); // get current points
                if(points>0)
                    points-=1; // decrease points by 1
                p2_points.setText(String.valueOf(points));
                check_set_winner();
            }
        });
    }
    private void confirmSetScore(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        AlertDialog.Builder builder = builder
                .setMessage("Czy chcesz zatwierdzić wynik tego seta?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //yes code
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no code
                    }
                });
    }

    private boolean confirmMatchScore(){ //get match document and update
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder
                .setMessage("Czy chcesz zatwierdzić wynik tego seta?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //yes code
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no code
                    }
                })
    }

    private void check_set_winner(){
        int p1_pts=Integer.parseInt(p1_points.getText().toString());
        int p2_pts=Integer.parseInt(p2_points.getText().toString());

        TextView p1_current_set_points;
        TextView p2_current_set_points;

        switch (current_set){
            case 1:
                p1_current_set_points=p1_set1_points;
                p2_current_set_points=p2_set1_points;
                break;

            case 2:
                p1_current_set_points=p1_set2_points;
                p2_current_set_points=p2_set2_points;
                break;

            case 3:
                p1_current_set_points=p1_set3_points;
                p2_current_set_points=p2_set3_points;
                break;

            default:
                p1_current_set_points=p1_set1_points;
                p2_current_set_points=p2_set1_points;

        }

        if(p1_pts==30 && p2_pts==29){ //p1 wins
            confirmSetScore();
            p1_current_set_points.setText(String.valueOf(p1_pts));
            p1_set_bilans.setText(String.valueOf(Integer.parseInt(p1_set_bilans.getText().toString())+1));
            p1_points.setText("0");

            p2_current_set_points.setText(String.valueOf(p2_pts));
            p2_points.setText("0");
            current_set+=1;

        }
        else if(p1_pts>=21 && p1_pts<30 && p1_pts-p2_pts>=2){ //p1 wins
            p1_current_set_points.setText(String.valueOf(p1_pts));
            p1_set_bilans.setText(String.valueOf(Integer.parseInt(p1_set_bilans.getText().toString())+1)); // get current p1_set_bilans and increase by 1
            p1_points.setText("0");

            p2_current_set_points.setText(String.valueOf(p2_pts));
            p2_points.setText("0");
            current_set+=1;
        }
        else if(p2_pts==30 && p1_pts==29){ //p2 wins
            p2_current_set_points.setText(String.valueOf(p2_pts));
            p2_set_bilans.setText(String.valueOf(Integer.parseInt(p2_set_bilans.getText().toString())+1));
            p2_points.setText("0");

            p1_current_set_points.setText(String.valueOf(p1_pts));
            p1_points.setText("0");
            current_set+=1;
        }
        else if(p2_pts>=21 && p2_pts<30 && p2_pts-p1_pts>=2){ //p2 wins
            p2_current_set_points.setText(String.valueOf(p2_pts));
            p2_set_bilans.setText(String.valueOf(Integer.parseInt(p2_set_bilans.getText().toString())+1));
            p2_points.setText("0");

            p1_current_set_points.setText(String.valueOf(p1_pts));
            p1_points.setText("0");
            current_set+=1;
        }
    }
}

