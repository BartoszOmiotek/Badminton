package com.example.bartek.badminton.DataModels;

import java.util.Date;

public class News {
    String newsTitle;
    String newsText;
    Date newsDate;

    News(){

    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsText() {
        return newsText;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }
}
