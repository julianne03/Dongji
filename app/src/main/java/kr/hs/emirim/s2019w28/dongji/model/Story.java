package kr.hs.emirim.s2019w28.dongji.model;

import java.util.Date;

public class Story extends kr.hs.emirim.s2019w28.dongji.model.StoryId {
    private String post_image_uri, user_id;
    private Date timestamp;

    public Story() {

    }

    public Story(String post_image_uri, String user_id, Date timestamp) {
        this.post_image_uri = post_image_uri;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public String getPost_image_uri() {
        return post_image_uri;
    }

    public void setPost_image_uri(String post_image_uri) {
        this.post_image_uri = post_image_uri;
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
