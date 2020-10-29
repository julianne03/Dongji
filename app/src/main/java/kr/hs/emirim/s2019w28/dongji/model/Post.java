package kr.hs.emirim.s2019w28.dongji.model;

import java.util.Date;

public class Post extends kr.hs.emirim.s2019w28.dongji.model.PostId {
    public String post_title,post_content,post_image;
    public Date timestamp;

    public Post() {
    }

    public Post(String post_title, String post_content, String post_image, Date timestamp) {
        this.post_title = post_title;
        this.post_content = post_content;
        this.post_image = post_image;
        this.timestamp = timestamp;
    }

    public Post(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
