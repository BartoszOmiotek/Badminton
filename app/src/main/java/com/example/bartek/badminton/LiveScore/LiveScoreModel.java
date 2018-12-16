package com.example.bartek.badminton.LiveScore;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LiveScoreModel {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    /*private DocumentReference match_reference=db
            .collection("Leagues")
            .document("TestLiga")
            .collection("Matches")
            .document("Bpg583w7qxEpU4mAHANY");*/

    private DocumentReference match_reference=db.document("Leagues/TestLiga/Matches/Bpg583w7qxEpU4mAHANY");

    public DocumentReference getMatchReference(){
        return match_reference;
    }

}
