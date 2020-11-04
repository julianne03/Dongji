package kr.hs.emirim.s2019w28.dongji.model;

public class Story {
    private String post_image, timestamp, user_id;

    public Story() {

    }
    public Story(String post_image, String timestamp, String user_id) {
        this.post_image = post_image;
        this.timestamp = timestamp;
        this.user_id = user_id;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
