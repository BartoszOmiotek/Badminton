package com.example.bartek.badminton.DataModels;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class Player {
    private String firstName;
    private String lastName;
    private String height;
    private String weight;
    private String country;
    private String profilePicture;
    @ServerTimestamp
    private Date birthDate;
    private List<DocumentReference> form;
    private long matchesWon;
    private long matchesLost;
    private long setsWon;
    private long setsLost;
    private long points;

    public Player(){

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getCountry() {
        return country;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public List<DocumentReference> getForm() {
        return form;
    }

    public long getMatchesWon() {
        return matchesWon;
    }

    public long getMatchesLost() {
        return matchesLost;
    }

    public long getSetsWon() {
        return setsWon;
    }

    public long getSetsLost() {
        return setsLost;
    }

    public long getPoints() {
        return points;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setForm(List<DocumentReference> form) {
        this.form = form;
    }

    public void setMatchesWon(long matchesWon) {
        this.matchesWon = matchesWon;
    }

    public void setMatchesLost(long matchesLost) {
        this.matchesLost = matchesLost;
    }

    public void setSetsWon(long setsWon) {
        this.setsWon = setsWon;
    }

    public void setSetsLost(long setsLost) {
        this.setsLost = setsLost;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
