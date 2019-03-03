package com.example.bartek.badminton.DataModels;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Match {

    @ServerTimestamp
    private Date matchDate;
    private String matchLocation;
    private String refName;
    private String matchStatus;
    private String matchCourt;
    private DocumentReference matchWinner;

    private DocumentReference p1DocReference;
    private String p1Name;
    private String p1S1Points;
    private String p1S2Points;
    private String p1S3Points;
    private long p1SetResult;

    private DocumentReference p2DocReference;
    private String p2Name;
    private String p2S1Points;
    private String p2S2Points;
    private String p2S3Points;
    private long p2SetResult;

    public Match(){ }

    public Date getMatchDate() {
        return matchDate;
    }

    public String getMatchLocation() {
        return matchLocation;
    }

    public String getRefName() {
        return refName;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public String getMatchCourt() {
        return matchCourt;
    }

    public DocumentReference getMatchWinner() {
        return matchWinner;
    }

    public String getP1Name() {
        return p1Name;
    }

    public String getP1S1Points() {
        return p1S1Points;
    }

    public String getP1S2Points() {
        return p1S2Points;
    }

    public String getP1S3Points() {
        return p1S3Points;
    }

    public long getP1SetResult() {
        return p1SetResult;
    }

    public DocumentReference getP1DocReference() {
        return p1DocReference;
    }

    public DocumentReference getP2DocReference() {
        return p2DocReference;
    }

    public String getP2Name() {
        return p2Name;
    }

    public String getP2S1Points() {
        return p2S1Points;
    }

    public String getP2S2Points() {
        return p2S2Points;
    }

    public String getP2S3Points() {
        return p2S3Points;
    }

    public long getP2SetResult() {
        return p2SetResult;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public void setMatchLocation(String matchLocation) {
        this.matchLocation = matchLocation;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public void setMatchCourt(String matchCourt) {
        this.matchCourt = matchCourt;
    }

    public void setMatchWinner(DocumentReference matchWinner) {
        this.matchWinner = matchWinner;
    }

    public void setP1DocReference(DocumentReference p1DocReference) {
        this.p1DocReference = p1DocReference;
    }

    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    public void setP1S1Points(String p1S1Points) {
        this.p1S1Points = p1S1Points;
    }

    public void setP1S2Points(String p1S2Points) {
        this.p1S2Points = p1S2Points;
    }

    public void setP1S3Points(String p1S3Points) {
        this.p1S3Points = p1S3Points;
    }

    public void setP1SetResult(long p1SetResult) {
        this.p1SetResult = p1SetResult;
    }

    public void setP2DocReference(DocumentReference p2DocReference) {
        this.p2DocReference = p2DocReference;
    }

    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    public void setP2S1Points(String p2S1Points) {
        this.p2S1Points = p2S1Points;
    }

    public void setP2S2Points(String p2S2Points) {
        this.p2S2Points = p2S2Points;
    }

    public void setP2S3Points(String p2S3Points) {
        this.p2S3Points = p2S3Points;
    }

    public void setP2SetResult(long p2SetResult) {
        this.p2SetResult = p2SetResult;
    }
}
