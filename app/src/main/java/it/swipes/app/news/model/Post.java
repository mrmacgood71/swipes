package it.swipes.app.news.model;

public class Post {
    private long id;
    private String postImage;
    private String publisher;

    public Post() {
    }

    public Post(long id, String postImage, String publisher) {
        this.id = id;
        this.postImage = postImage;
        this.publisher = publisher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
