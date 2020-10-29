package kr.hs.emirim.s2019w28.dongji.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class PostId<T extends Post> {

    @Exclude
    public String PostId;

    public <T extends Post> T withId(@NonNull final String id) {
        this.PostId = id;
        return (T) this;
    }
}
