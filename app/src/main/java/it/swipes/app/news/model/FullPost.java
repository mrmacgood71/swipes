package it.swipes.app.news.model;

import java.util.List;

import it.swipes.app.publisher.model.PostPublisher;

public class FullPost {

    private int id;
    private List<PhotoUrl> image;
    private PostPublisher author;
    private int likes;
    private int commentsCount;

    public FullPost() {
    }

    public FullPost(
            int id,
            List<PhotoUrl> image,
            PostPublisher author,
            int likes,
            int commentsCount
    ) {
        this.id = id;
        this.image = image;
        this.author = author;
        this.likes = likes;
        this.commentsCount = commentsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PhotoUrl> getImage() {
        return image;
    }

    public void setImage(List<PhotoUrl> image) {
        this.image = image;
    }

    public PostPublisher getAuthor() {
        return author;
    }

    public void setAuthor(PostPublisher author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
