package com.example.bartek.badminton.LiveScore;

public class LiveScorePresenter implements LiveScoreContract.Presenter{
    private LiveScoreContract.MvpView view;

    LiveScorePresenter(LiveScoreContract.MvpView view){
        this.view=view;
    }

}
