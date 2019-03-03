package com.example.bartek.badminton.LiveScore;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.bartek.badminton.R;
import com.google.firebase.firestore.DocumentReference;

public class LiveScorePresenter{

    private LiveScoreActivity activity;
    private DocumentReference match_doc_ref;
    private LiveScorePlayerHolder p1;
    private LiveScorePlayerHolder p2;

    private int current_set; // describes number representation of current set (1,2,3)
    private LiveScorePlayerHolder current_player;

    LiveScorePresenter(LiveScoreActivity activity,DocumentReference match_doc_ref){ // constructor
        this.activity = activity;
        this.match_doc_ref = match_doc_ref;
        init();
        updateMatchStatus("1"); //update match status to LIVE
    }

    public void init() {
        p1=new LiveScorePlayerHolder(activity,"1");
        p2=new LiveScorePlayerHolder(activity,"2");

        current_set=1;
        setCurrentSet(current_set);
        current_player=p1; // set p1 as starting player
        setSides(p1,p2); // set sides for beginning

        p1.getIncPoint().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p1.getCurrentSetPoints().getText().toString()); // get current points
                points+=1; // increase points by 1
                p1.getCurrentSetPoints().setText(String.valueOf(points));
                p1.getPoints().setText(String.valueOf(points));
                current_player=p1;
                setSides(p1,p2);
                updatePointsScore();
                checkSetWinner(p1,p2);
            }
        });
        p1.getDecPoint().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p1.getCurrentSetPoints().getText().toString()); // get current points
                if(points>0)
                    points-=1; // decrease points by 1
                p1.getCurrentSetPoints().setText(String.valueOf(points));
                p1.getPoints().setText(String.valueOf(points));
                current_player=p1;
                setSides(p1,p2);
                updatePointsScore();
                checkSetWinner(p1,p2);
            }
        });
        p2.getIncPoint().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p2.getCurrentSetPoints().getText().toString()); // get current points
                points+=1; // increase points by 1
                p2.getCurrentSetPoints().setText(String.valueOf(points));
                p2.getPoints().setText(String.valueOf(points));
                current_player=p2;
                setSides(p1,p2);
                updatePointsScore();
                checkSetWinner(p1,p2);
            }
        });
        p2.getDecPoint().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points=Integer.parseInt(p2.getCurrentSetPoints().getText().toString()); // get current points
                if(points>0)
                    points-=1; // decrease points by 1
                p2.getCurrentSetPoints().setText(String.valueOf(points));
                p2.getPoints().setText(String.valueOf(points));
                current_player=p2;
                setSides(p1,p2);
                updatePointsScore();
                checkSetWinner(p1,p2);
            }
        });
    }
    private void confirmSetScore(final LiveScorePlayerHolder set_winner, final LiveScorePlayerHolder set_looser){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(R.string.confirm_set_message)
                    .setPositiveButton(R.string.yes_option, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            set_winner.winSet();
                            set_winner.getCurrentSetPoints().setTypeface(null, Typeface.BOLD);
                            set_winner.getPoints().setText("0");
                            set_looser.looseSet();
                            set_looser.getPoints().setText("0");
                            updateSetScore();
                            if (checkMatchEnd()) { //check if match is over
                                confirmMatchScore(set_winner,set_looser);
                            }
                            else {
                                current_set += 1;
                                setCurrentSet(current_set);
                                p1.enableButtons();
                                p2.enableButtons();
                            }
                        }
                    })
                    .setNegativeButton(R.string.no_option, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            p1.enableButtons();
                            p2.enableButtons();
                        }
                    });
            builder.setCancelable(false).show();
    }

    private void confirmMatchScore(final LiveScorePlayerHolder match_winner, final LiveScorePlayerHolder match_looser){ //get match document and update
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.end_match_message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        p1.disableButtons();
                        p2.disableButtons();
                        //save & close match document
                        updateMatchStatus("2"); //update match status to ended
                    }
                });
        builder.setCancelable(false).show();
    }

    private boolean checkMatchEnd(){
        return p1.getSetResult()==2 || p2.getSetResult()==2 ? true : false;
    }

    private void setCurrentSet(int current_set){
        switch (current_set){
            case 1:
                p1.setCurrentSetPoints(p1.getSet1Points());
                p2.setCurrentSetPoints(p2.getSet1Points());
                p1.getCurrentSetPoints().setText("0");
                p2.getCurrentSetPoints().setText("0");
                break;

            case 2:
                p1.setCurrentSetPoints(p1.getSet2Points());
                p2.setCurrentSetPoints(p2.getSet2Points());
                p1.getCurrentSetPoints().setText("0");
                p2.getCurrentSetPoints().setText("0");
                break;

            case 3:
                p1.setCurrentSetPoints(p1.getSet3Points());
                p2.setCurrentSetPoints(p2.getSet3Points());
                p1.getCurrentSetPoints().setText("0");
                p2.getCurrentSetPoints().setText("0");
                break;
        }
    }

    private void checkSetWinner(LiveScorePlayerHolder p1, LiveScorePlayerHolder p2) { // return set winner player
        int p1_pts = p1.getPointsInt();
        int p2_pts = p2.getPointsInt();

        if (p1_pts == 30 && p2_pts == 29) { //p1 wins
            p1.disableButtons();
            p2.disableButtons();
            confirmSetScore(p1,p2);
        }
        else if (p1_pts >= 21 && p1_pts < 30 && p1_pts - p2_pts >= 2) { //p1 wins
            p1.disableButtons();
            p2.disableButtons();
            confirmSetScore(p1,p2);
        }
        else if (p2_pts == 30 && p1_pts == 29) { //p2 wins
            p1.disableButtons();
            p2.disableButtons();
            confirmSetScore(p2,p1);
        }
        else if (p2_pts >= 21 && p2_pts < 30 && p2_pts - p1_pts >= 2) { //p2 wins
            p1.disableButtons();
            p2.disableButtons();
            confirmSetScore(p2,p1);
        }
    }

    private void updatePointsScore(){

        String p1_current_set_points;
        String p2_current_set_points;

        switch(current_set){
            case 1:
                p1_current_set_points="p1S1Points";
                p2_current_set_points="p2S1Points";
                break;

            case 2:
                p1_current_set_points="p1S2Points";
                p2_current_set_points="p2S2Points";
                break;

            case 3:
                p1_current_set_points="p1S3Points";
                p2_current_set_points="p2S3Points";
                break;

            default:
                p1_current_set_points="p1S1Points";
                p2_current_set_points="p2S1Points";
        }
        match_doc_ref.update(
                p1_current_set_points,p1.getCurrentSetPoints().getText().toString(),
                p2_current_set_points,p2.getCurrentSetPoints().getText().toString()
        );
    }

    private void updateSetScore(){
        match_doc_ref.update(
                "p1SetResult",p1.getSetResult(),
                "p2SetResult",p2.getSetResult()
        );
    }

    private void updateMatchStatus(String status){
        match_doc_ref.update(
                "matchStatus",status
        );
    }

    private void setSides(LiveScorePlayerHolder p1, LiveScorePlayerHolder p2){
        if(current_player.equals(p1)) {
            if (p1.getPointsInt() % 2 == 0) { //check for right side serve
                setCourtRightSidesVisibile(true);
                setCourtLeftSidesVisibile(false);
                p1.getCourtBot().setBackgroundResource(R.color.colorLiveScoreCourt);
                p2.getCourtTop().setBackgroundColor(Color.TRANSPARENT);
            }
            else{
                setCourtRightSidesVisibile(false);
                setCourtLeftSidesVisibile(true);
                p1.getCourtTop().setBackgroundResource(R.color.colorLiveScoreCourt);
                p2.getCourtBot().setBackgroundColor(Color.TRANSPARENT);
            }
        }
        else{
            if (p2.getPointsInt() % 2 == 0) { //check for right side serve
                setCourtRightSidesVisibile(true);
                setCourtLeftSidesVisibile(false);
                p2.getCourtTop().setBackgroundResource(R.color.colorLiveScoreCourt);
                p1.getCourtBot().setBackgroundColor(Color.TRANSPARENT);
            }
            else{
                setCourtRightSidesVisibile(false);
                setCourtLeftSidesVisibile(true);
                p2.getCourtBot().setBackgroundResource(R.color.colorLiveScoreCourt);
                p1.getCourtTop().setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private void setCourtRightSidesVisibile(boolean visible){
        if(visible){
            p1.getCourtBot().setVisibility(View.VISIBLE);
            p2.getCourtTop().setVisibility(View.VISIBLE);
        }
        else{
            p1.getCourtBot().setVisibility(View.INVISIBLE);
            p2.getCourtTop().setVisibility(View.INVISIBLE);
        }
    }

    private void setCourtLeftSidesVisibile(boolean visible){
        if(visible){
            p1.getCourtTop().setVisibility(View.VISIBLE);
            p2.getCourtBot().setVisibility(View.VISIBLE);
        }
        else{
            p1.getCourtTop().setVisibility(View.INVISIBLE);
            p2.getCourtBot().setVisibility(View.INVISIBLE);
        }
    }

    public void changeSides(LiveScorePlayerHolder p1, LiveScorePlayerHolder p2){

    }
}
