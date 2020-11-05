package kr.hs.emirim.s2019w28.dongji.model;

import java.util.Date;

public class Post extends kr.hs.emirim.s2019w28.dongji.model.PostId {
    public String post_title,post_content,virus_category,post_image,user_id;
    public Date timestamp;

    public Post() {
    }

    public Post(String post_title, String post_content, String virus_category, String post_image, String user_id, Date timestamp) {
        this.post_title = post_title;
        this.post_content = post_content;
        this.virus_category = virus_category;
        this.post_image = post_image;
        this.user_id = user_id;
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

    public String getVirus_category() {
        return virus_category;
    }

    public void setVirus_category(String virus_category) {
        this.virus_category = virus_category;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
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
