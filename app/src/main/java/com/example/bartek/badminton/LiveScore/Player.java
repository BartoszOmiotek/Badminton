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

    private TextView set_bilans;
    private TextView set1_points;
    private TextView set2_points;
    private TextView set3_points;

    Player(Activity activity, String playerId){
        if(playerId.equals("1")) {
            name=activity.findViewById(R.id.p1_name);
            points = activity.findViewById(R.id.p1_points);
            inc_point = activity.findViewById(R.id.p1_inc_point_btn);
            dec_point = activity.findViewById(R.id.p1_dec_point_btn);
            set_bilans = activity.findViewById(R.id.p1_set_bilans);
            set1_points = activity.findViewById(R.id.p1_set1_points);
            set2_points = activity.findViewById(R.id.p1_set2_points);
            set3_points = activity.findViewById(R.id.p1_set3_points);
        } else if(playerId.equals("2")){
            name=activity.findViewById(R.id.p2_name);
            points = activity.findViewById(R.id.p2_points);
            inc_point = activity.findViewById(R.id.p2_inc_point_btn);
            dec_point = activity.findViewById(R.id.p2_dec_point_btn);
            set_bilans = activity.findViewById(R.id.p2_set_bilans);
            set1_points = activity.findViewById(R.id.p2_set1_points);
            set2_points = activity.findViewById(R.id.p2_set2_points);
            set3_points = activity.findViewById(R.id.p2_set3_points);
        }
    }

    public void win_set(){
        current_set_points.setText(points.getText().toString());
        set_bilans.setText(String.valueOf(Integer.parseInt(set_bilans.getText().toString()) + 1));
        points.setText("0");
    }

    public void loose_set(){
        current_set_points.setText(points.getText().toString());
        points.setText("0");
    }

    public void disable_buttons(){
        inc_point.setEnabled(false);
        dec_point.setEnabled(false);
    }

    public void enable_buttons(){
        inc_point.setEnabled(true);
        dec_point.setEnabled(true);
    }

//getters
    public TextView getPoints() {
        return points;
    }
    public int getPointsInt(){
        return Integer.parseInt(points.getText().toString());
    }
    public Button getInc_point() {
        return inc_point;
    }
    public Button getDec_point() {
        return dec_point;
    }
    public TextView getSet_bilans() {
        return set_bilans;
    }
    public TextView getSet1_points() {
        return set1_points;
    }
    public TextView getSet2_points() {
        return set2_points;
    }
    public TextView getSet3_points() {
        return set3_points;
    }
    public TextView getCurrent_set_points() {
        return current_set_points;
    }
    public TextView getName(){
        return name;
    }
//setters
    public void setPoints(TextView points) {
        this.points = points;
    }
    public void setInc_point(Button inc_point) {
        this.inc_point = inc_point;
    }
    public void setDec_point(Button dec_point) {
        this.dec_point = dec_point;
    }
    public void setSet_bilans(TextView set_bilans) {
        this.set_bilans = set_bilans;
    }
    public void setSet1_points(TextView set1_points) {
        this.set1_points = set1_points;
    }
    public void setSet2_points(TextView set2_points) {
        this.set2_points = set2_points;
    }
    public void setSet3_points(TextView set3_points) {
        this.set3_points = set3_points;
    }
    public void setCurrent_set_points(TextView current_set_points) {
        this.current_set_points = current_set_points;
    }
    public void setName(TextView name){
        this.name=name;
    }
}
