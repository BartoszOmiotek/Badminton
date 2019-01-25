package com.example.bartek.badminton.LiveScore;

import java.util.Date;

public class Match {
    Date match_date;
    String match_location;
    String ref_name;
    String match_status;

    String p1_name;
    String p1_s1_points;
    String p1_s2_points;
    String p1_s3_points;
    long p1_set_bilans;

    String p2_name;
    String p2_s1_points;
    String p2_s2_points;
    String p2_s3_points;
    long p2_set_bilans;

    public Match(){

    }

    public String getMatchStatus(){ return match_status; }

    public void setMatchStatus(String match_status){ this.match_status=match_status; }

    public Date getMatchDate() {
        return match_date;
    }

    public void setMatchDate(Date match_date) {
        this.match_date = match_date;
    }

    public String getMatchLocation() {
        return match_location;
    }

    public void setMatchLocation(String match_location) {
        this.match_location = match_location;
    }

    public String getP1Name() {
        return p1_name;
    }

    public void setP1Name(String p1_name) {
        this.p1_name = p1_name;
    }

    public String getP1S1Points() {
        return p1_s1_points;
    }

    public void setP1S1Points(String  p1_s1_points) {
        this.p1_s1_points = p1_s1_points;
    }

    public String getP1S2Points() {
        return p1_s2_points;
    }

    public void setP1S2Points(String  p1_s2_points) {
        this.p1_s2_points = p1_s2_points;
    }

    public String getP1S3Points() {
        return p1_s3_points;
    }

    public void setP1S3Points(String  p1_s3_points) {
        this.p1_s3_points = p1_s3_points;
    }

    public long getP1SetBilans() { return p1_set_bilans;
    }

    public void setP1SetBilans(long p1_set_bilans) {
        this.p1_set_bilans = p1_set_bilans;
    }

    public String getP2Name() {
        return p2_name;
    }

    public void setP2Name(String p2_name) {
        this.p2_name = p2_name;
    }

    public String getP2S1Points() {
        return p2_s1_points;
    }

    public void setP2S1Points(String  p2_s1_points) {
        this.p2_s1_points = p2_s1_points;
    }

    public String getP2S2Points() {
        return p2_s2_points;
    }

    public void setP2S2Points(String  p2_s2_points) {
        this.p2_s2_points = p2_s2_points;
    }

    public String getP2S3Points() {
        return p2_s3_points;
    }

    public void setP2S3Points(String  p2_s3_points) {
        this.p2_s3_points = p2_s3_points;
    }

    public long getP2SetBilans() {
        return p2_set_bilans;
    }

    public void setP2SetBilans(long p2_set_bilans) {
        this.p2_set_bilans = p2_set_bilans;
    }

    public String getRefName() {
        return ref_name;
    }

    public void setRefName(String ref_name) {
        this.ref_name = ref_name;
    }
}
