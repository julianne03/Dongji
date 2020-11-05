package kr.hs.emirim.s2019w28.dongji.model;

import java.util.Date;

public class Story extends kr.hs.emirim.s2019w28.dongji.model.StoryId {
    private String story_image, user_id;
    private Date timestamp;

    public Story() {

    }

    public Story(String story_image, String user_id, Date timestamp) {
        this.story_image = story_image;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public String getStory_image() {
        return story_image;
    }

    public void setStory_image(String story_image) {
        this.story_image = story_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
