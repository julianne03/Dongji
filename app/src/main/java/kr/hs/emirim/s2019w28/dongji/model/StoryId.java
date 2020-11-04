package kr.hs.emirim.s2019w28.dongji.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class StoryId<T extends Story> {

    @Exclude
    public String StoryId;

    public <T extends Story> T withId(@NonNull final String id) {
        this.StoryId = id;
        return (T) this;
    }
}
