package kr.hs.emirim.s2019w28.dongji.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class CommentId<T extends Comments> {

    @Exclude
    public String CommentId;

    public <T extends Comments> T withId(@NonNull final String id) {
        this.CommentId = id;
        return (T) this;
    }
}
