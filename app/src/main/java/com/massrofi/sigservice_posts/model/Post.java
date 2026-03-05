package com.massrofi.sigservice_posts.model;

public class Post {
    private int userId;
    private Integer id;
    private String title;
    private String body;


    public int getUserId() { return userId; }
    public Integer  getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }


    public void setTitle(String title) { this.title = title; }
    public void setBody(String body) { this.body = body; }

    public int getWordCount() {
        if (body == null || body.isEmpty()) return 0;
        return body.trim().split("\\s+").length;
    }

}
