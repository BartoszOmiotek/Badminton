package com.example.bartek.badminton.LiveScore;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import com.example.bartek.badminton.R;

public class Player {

    private TextView name;
    private TextView points;
    private Button inc_point;
    private Button dec_point;
    private TextView current_set_points;

    private long set_bilans;
    private TextView set1_points;
    private TextView set2_points;
    private TextView set3_points;

    private TextView court_top;
    private TextView court_bot;

    Player(Activity activity, String playerId){
        if(playerId.equals("1")) {
            name=activity.findViewById(R.id.p1_name);
            points = activity.findViewById(R.id.p1_points);
            inc_point = activity.findViewById(R.id.p1_inc_point_btn);
            dec_point = activity.findViewById(R.id.p1_dec_point_btn);
            set1_points = activity.findViewById(R.id.p1_set1_points);
            set2_points = activity.findViewById(R.id.p1_set2_points);
            set3_points = activity.findViewById(R.id.p1_set3_points);
            court_top = activity.findViewById(R.id.p1_court_top);
            court_bot = activity.findViewById(R.id.p1_court_bot);
        }
        else if(playerId.equals("2")){
            name=activity.findViewById(R.id.p2_name);
            points = activity.findViewById(R.id.p2_points);
            inc_point = activity.findViewById(R.id.p2_inc_point_btn);
            dec_point = activity.findViewById(R.id.p2_dec_point_btn);
            set1_points = activity.findViewById(R.id.p2_set1_points);
            set2_points = activity.findViewById(R.id.p2_set2_points);
            set3_points = activity.findViewById(R.id.p2_set3_points);
            court_top = activity.findViewById(R.id.p2_court_top);
            court_bot = activity.findViewById(R.id.p2_court_bot);
        }
    }

/*    public void changeSide(Activity activity){
        if(playerId.equals("1")) {
            name=activity.findViewById(R.id.p1_name);
            points = activity.findViewById(R.id.p1_points);
            inc_point = activity.findViewById(R.id.p1_inc_point_btn);
            dec_point = activity.findViewById(R.id.p1_dec_point_btn);
            set1_points = activity.findViewById(R.id.p1_set1_points);
            set2_points = activity.findViewById(R.id.p1_set2_points);
            set3_points = activity.findViewById(R.id.p1_set3_points);
            court_top = activity.findViewById(R.id.p1_court_top);
            court_bot = activity.findViewById(R.id.p1_court_bot);
        }
        else if(playerId.equals("2")){
            name=activity.findViewById(R.id.p2_name);
            points = activity.findViewById(R.id.p2_points);
            inc_point = activity.findViewById(R.id.p2_inc_point_btn);
            dec_point = activity.findViewById(R.id.p2_dec_point_btn);
            set1_points = activity.findViewById(R.id.p2_set1_points);
            set2_points = activity.findViewById(R.id.p2_set2_points);
            set3_points = activity.findViewById(R.id.p2_set3_points);
            court_top = activity.findViewById(R.id.p2_court_top);
            court_bot = activity.findViewById(R.id.p2_court_bot);
        }
    }*/

    public void winSet(){
        //current_set_points.setText(points.getText().toString());
        set_bilans++;
        //points.setText("0");
    }

    public void looseSet(){
        //current_set_points.setText(points.getText().toString());
        //points.setText("0");
    }

    public void disableButtons(){
        inc_point.setEnabled(false);
        dec_point.setEnabled(false);
    }

    public void enableButtons(){
        inc_point.setEnabled(true);
        dec_point.setEnabled(true);
    }

//getters
    public TextView getPoints() { return points; }
    public int getPointsInt(){ return Integer.parseInt(current_set_points.getText().toString()); }
    public long getSetBilans() { return set_bilans; }
    public Button getIncPoint() {
        return inc_point;
    }
    public Button getDecPoint() {
        return dec_point;
    }
    public TextView getSet1Points() {
        return set1_points;
    }
    public TextView getSet2Points() {
        return set2_points;
    }
    public TextView getSet3Points() {
        return set3_points;
    }
    public TextView getCurrentSetPoints() {
        return current_set_points;
    }
    public TextView getName(){
        return name;
    }
    public TextView getCourtTop() {return court_top; };
    public TextView getCourtBot() {return court_bot; };

//setters
    public void setSet_bilans(int set_bilans) { this.set_bilans = set_bilans; }
    public void setCurrentSetPoints(TextView current_set_points) { this.current_set_points = current_set_points; }
    public void setSet1_points(TextView set1_points) {
        this.set1_points = set1_points;
    }
    public void setSet2_points(TextView set2_points) {
        this.set2_points = set2_points;
    }
    public void setSet3_points(TextView set3_points) {
        this.set3_points = set3_points;
    }
    public void setPoints(TextView points) {
        this.points = points;
    }
    public void setInc_point(Button inc_point) {
        this.inc_point = inc_point;
    }
    public void setDec_point(Button dec_point) {
        this.dec_point = dec_point;
    }
    public void setName(TextView name){
        this.name=name;
    }
    public void SetCourtTop(TextView court_top) {this.court_top=court_top; };
    public void SetCourtBot(TextView court_bot) {this.court_bot=court_bot; };
}
