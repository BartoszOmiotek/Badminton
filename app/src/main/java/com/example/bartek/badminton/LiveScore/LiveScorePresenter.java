package com.example.bartek.badminton.LiveScore;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.bartek.badminton.Language.LanguageInterface;
import com.example.bartek.badminton.Language.LanguageEnglish;
import com.example.bartek.badminton.R;
import com.google.firebase.firestore.DocumentReference;

import java.util.Locale;
import android.content.res.Resources;

public class LiveScorePresenter implements LiveScoreContract{

    private LiveScoreActivity activity;
    private DocumentReference match_document;
    private LanguageInterface lang=new LanguageEnglish(); //set language --TO DO: GET LANGUAGE FROM APP OPTIONS
    private Player p1;
    private Player p2;

    private int current_set; // describes number representation of current set (1,2,3)
    private TextView set1_colon;
    private TextView set2_colon;
    private TextView set3_colon;
    private TextView current_set_colon;

    LiveScorePresenter(LiveScoreActivity activity){ // constructor
        this.activity=activity;
        init();
    }

    @Override
    public void init() {
        match_document=new LiveScoreModel().getMatchReference();
        set1_colon=activity.findViewById(R.id.set1_colon);
        set2_colon=activity.findViewById(R.id.set2_colon);
        set3_colon=activity.findViewById(R.id.set3_colon);

        p1=new Player(activity,"1");
        p2=new Player(activity,"2");

        current_set=1;
        setCurrentSet();

        p1.getInc_point().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p1.getPoints().getText().toString()); // get current points
                points+=1; // increase points by 1
                p1.getPoints().setText(String.valueOf(points));
                updatePointsScore();
                checkSetWinner(p1,p2);
            }
        });
        p1.getDec_point().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p1.getPoints().getText().toString()); // get current points
                if(points>0)
                    points-=1; // decrease points by 1
                p1.getPoints().setText(String.valueOf(points));
                updatePointsScore();
                checkSetWinner(p1,p2);
            }
        });
        p2.getInc_point().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p2.getPoints().getText().toString()); // get current points
                points+=1; // increase points by 1
                p2.getPoints().setText(String.valueOf(points));
                updatePointsScore();
                checkSetWinner(p1,p2);
            }
        });
        p2.getDec_point().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p2.getPoints().getText().toString()); // get current points
                if(points>0)
                    points-=1; // decrease points by 1
                p2.getPoints().setText(String.valueOf(points));
                updatePointsScore();
                checkSetWinner(p1,p2);
            }
        });
    }

    private void confirmSetScore(final Player set_winner, final Player set_looser){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(lang.getConfirmSetMessage())
                    .setPositiveButton(lang.getYesOption(), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            set_winner.win_set();
                            set_looser.loose_set();
                            current_set_colon.setText(":");
                            updateSetScore();
                            if (checkMatchEnd()) { //check if match is over
                                confirmMatchScore(set_winner,set_looser);
                            }
                            else {
                                current_set += 1;
                                setCurrentSet();
                                p1.enable_buttons();
                                p2.enable_buttons();
                            }
                        }
                    })
                    .setNegativeButton(lang.getNoOption(), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            p1.enable_buttons();
                            p2.enable_buttons();
                        }
                    });
            builder.setCancelable(false).show();
    }

    private void confirmMatchScore(final Player match_winner, final Player match_looser){ //get match document and update
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(lang.getEndOfMatchMessage())
                .setPositiveButton(lang.getOkOption(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        p1.disable_buttons();
                        p2.disable_buttons();
                        //save & close match document  c c
                        match_winner.getName().setText("WINNER");
                    }
                });
        builder.setCancelable(false).show();
    }

    private boolean checkMatchEnd(){
        String p1_score=p1.getSet_bilans().getText().toString();
        String p2_score=p2.getSet_bilans().getText().toString();
        return p1_score.equals("2") || p2_score.equals("2") ? true : false;
    }

    private void setCurrentSet(){
        switch (current_set){
            case 1:
                p1.setCurrent_set_points(p1.getSet1_points());
                p2.setCurrent_set_points(p2.getSet1_points());
                current_set_colon=set1_colon;
                break;

            case 2:
                p1.setCurrent_set_points(p1.getSet2_points());
                p2.setCurrent_set_points(p2.getSet2_points());
                current_set_colon=set2_colon;
                setRTL(activity.getResources().getConfiguration());
                break;

            case 3:
                p1.setCurrent_set_points(p1.getSet3_points());
                p2.setCurrent_set_points(p2.getSet3_points());
                current_set_colon=set3_colon;
                setLTR(activity.getResources().getConfiguration());
                break;
        }
    }

    private void checkSetWinner(Player p1, Player p2) { // return set winner player
        int p1_pts = p1.getPointsInt();
        int p2_pts = p2.getPointsInt();

        if (p1_pts == 30 && p2_pts == 29) { //p1 wins
            p1.disable_buttons();
            p2.disable_buttons();
            confirmSetScore(p1,p2);
        }
        else if (p1_pts >= 21 && p1_pts < 30 && p1_pts - p2_pts >= 2) { //p1 wins
            p1.disable_buttons();
            p2.disable_buttons();
            confirmSetScore(p1,p2);
        }
        else if (p2_pts == 30 && p1_pts == 29) { //p2 wins
            p1.disable_buttons();
            p2.disable_buttons();
            confirmSetScore(p2,p1);
        }
        else if (p2_pts >= 21 && p2_pts < 30 && p2_pts - p1_pts >= 2) { //p2 wins
            p1.disable_buttons();
            p2.disable_buttons();
            confirmSetScore(p2,p1);
        }
    }

    private void updatePointsScore(){

        String p1_current_set_points;
        String p2_current_set_points;

        switch(current_set){
            case 1:
                p1_current_set_points="p1_s1_points";
                p2_current_set_points="p2_s1_points";
            case 2:
                p1_current_set_points="p1_s2_points";
                p2_current_set_points="p2_s2_points";
            case 3:
                p1_current_set_points="p1_s3_points";
                p2_current_set_points="p2_s3_points";
            default:
                p1_current_set_points="p1_s1_points";
                p2_current_set_points="p2_s1_points";
        }
        match_document.update(
                p1_current_set_points,p1.getPoints().getText().toString(),
                p2_current_set_points,p2.getPoints().getText().toString()
        );
    }

    private void updateSetScore(){
        match_document.update(
                "p1_set_bilans",p1.getSet_bilans().getText().toString(),
                "p2_set_bilans",p2.getSet_bilans().getText().toString()
        );
    }

    private void setRTL(Configuration newConfig){ //setup layout for right-to-left orientation
        activity.onConfigurationChanged(newConfig);
        newConfig.screenLayout=Configuration.SCREENLAYOUT_LAYOUTDIR_RTL;
    }

    private void setLTR(Configuration newConfig){
        activity.onConfigurationChanged(newConfig);
        newConfig.screenLayout=Configuration.SCREENLAYOUT_LAYOUTDIR_LTR;
    }
}
