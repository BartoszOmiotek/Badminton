package com.example.bartek.badminton.LiveScore;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bartek.badminton.R;

public class LiveScorePresenter implements LiveScoreContract{

    private LiveScoreActivity activity;

    private TextView p1_set_bilans;
    private Button p1_inc_point;
    private Button p1_dec_point;
    private TextView p1_points;

    private TextView p1_set1_points;
    private TextView p1_set2_points;
    private TextView p1_set3_points;

    private TextView p2_set_bilans;
    private Button p2_inc_point;
    private Button p2_dec_point;
    private TextView p2_points;

    private TextView p2_set1_points;
    private TextView p2_set2_points;
    private TextView p2_set3_points;

    private int current_set; // describes number representation of current set (1,2,3)
    private TextView set1_colon;
    private TextView set2_colon;
    private TextView set3_colon;
    private TextView p1_current_set_points;
    private TextView p2_current_set_points;
    private TextView current_set_colon;
    private boolean dialog_confirm_match;

    public LiveScorePresenter(LiveScoreActivity activity){
        this.activity=activity;
    }

    @Override
    public void init() {
        p1_inc_point=activity.findViewById(R.id.p1_inc_point_btn);
        p1_dec_point=activity.findViewById(R.id.p1_dec_point_btn);
        p1_points=activity.findViewById(R.id.p1_points);

        p2_inc_point=activity.findViewById(R.id.p2_inc_point_btn);
        p2_dec_point=activity.findViewById(R.id.p2_dec_point_btn);
        p2_points=activity.findViewById(R.id.p2_points);

        p1_set1_points=activity.findViewById(R.id.p1_set1_points);
        p1_set2_points=activity.findViewById(R.id.p1_set2_points);
        p1_set3_points=activity.findViewById(R.id.p1_set3_points);

        p2_set1_points=activity.findViewById(R.id.p2_set1_points);
        p2_set2_points=activity.findViewById(R.id.p2_set2_points);
        p2_set3_points=activity.findViewById(R.id.p2_set3_points);

        p1_set_bilans=activity.findViewById(R.id.p1_set_bilans);
        p2_set_bilans=activity.findViewById(R.id.p2_set_bilans);

        set1_colon=activity.findViewById(R.id.set1_colon);
        set2_colon=activity.findViewById(R.id.set2_colon);
        set3_colon=activity.findViewById(R.id.set3_colon);

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
        set_current_set();
    }
    private boolean confirmSetScore(){
        boolean dialog_confirm_set;
        final int p1_pts = Integer.parseInt(p1_points.getText().toString());
        final int p2_pts = Integer.parseInt(p2_points.getText().toString());
        AlertDialog builder=new AlertDialog.Builder(activity )
                .setMessage("Czy chcesz zatwierdzić wynik tego seta?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //yes code
                        p1_current_set_points.setText(String.valueOf(p1_pts));
                        p1_set_bilans.setText(String.valueOf(Integer.parseInt(p1_set_bilans.getText().toString()) + 1));
                        p1_points.setText("0");

                        p2_current_set_points.setText(String.valueOf(p2_pts));
                        p2_points.setText("0");
                        current_set_colon.setText(":");
                        check_match_winner();
                        current_set += 1;
                        set_current_set();
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no code
                        dialog_confirm_set = false;
                    }
                }).show();
        return dialog_confirm_set;
    }

    private boolean confirmMatchScore(){ //get match document and update

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Czy chcesz zatwierdzić wynik tego seta?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //yes code
                dialog_confirm_match = true;
            }
        }).show();
        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //no code
                dialog_confirm_match = false;
            }
        }).show();
        return dialog_confirm_match;
    }

    private void check_match_winner(){
        String p1_score=p1_set_bilans.getText().toString();
        String p2_score=p2_set_bilans.getText().toString();

        String wynik=p1_score+":"+p2_score;

        switch (wynik){
            case "2:0":
                //p1 wins
            case "2:1":
                //p1 wins
            case "0:2":
                //p2 wins
            case "1:2":
                //p2 wins
        }
    }
    private void set_current_set(){
        switch (current_set){
            case 1:
                p1_current_set_points=p1_set1_points;
                p2_current_set_points=p2_set1_points;
                current_set_colon=set1_colon;
                break;

            case 2:
                p1_current_set_points=p1_set2_points;
                p2_current_set_points=p2_set2_points;
                current_set_colon=set2_colon;
                break;

            case 3:
                p1_current_set_points=p1_set3_points;
                p2_current_set_points=p2_set3_points;
                current_set_colon=set3_colon;
                break;

        }
    }

    private void check_set_winner() {
        int p1_pts = Integer.parseInt(p1_points.getText().toString());
        int p2_pts = Integer.parseInt(p2_points.getText().toString());

        if (p1_pts == 2 && p2_pts == 1) { //p1 wins
            if (confirmSetScore()) {
                p1_current_set_points.setText(String.valueOf(p1_pts));
                p1_set_bilans.setText(String.valueOf(Integer.parseInt(p1_set_bilans.getText().toString()) + 1));
                p1_points.setText("0");

                p2_current_set_points.setText(String.valueOf(p2_pts));
                p2_points.setText("0");
                current_set_colon.setText(":");
                check_match_winner();
                current_set += 1;
                set_current_set();
            } else {
                return;
            }

        } else if (p1_pts >= 21 && p1_pts < 30 && p1_pts - p2_pts >= 2) { //p1 wins
            p1_current_set_points.setText(String.valueOf(p1_pts));
            p1_set_bilans.setText(String.valueOf(Integer.parseInt(p1_set_bilans.getText().toString()) + 1)); // get current p1_set_bilans and increase by 1
            p1_points.setText("0");

            p2_current_set_points.setText(String.valueOf(p2_pts));
            p2_points.setText("0");
            current_set += 1;
        } else if (p2_pts == 30 && p1_pts == 29) { //p2 wins
            p2_current_set_points.setText(String.valueOf(p2_pts));
            p2_set_bilans.setText(String.valueOf(Integer.parseInt(p2_set_bilans.getText().toString()) + 1));
            p2_points.setText("0");

            p1_current_set_points.setText(String.valueOf(p1_pts));
            p1_points.setText("0");
            current_set += 1;
        } else if (p2_pts >= 21 && p2_pts < 30 && p2_pts - p1_pts >= 2) { //p2 wins
            p2_current_set_points.setText(String.valueOf(p2_pts));
            p2_set_bilans.setText(String.valueOf(Integer.parseInt(p2_set_bilans.getText().toString()) + 1));
            p2_points.setText("0");

            p1_current_set_points.setText(String.valueOf(p1_pts));
            p1_points.setText("0");
            current_set += 1;
        }
    }
}
