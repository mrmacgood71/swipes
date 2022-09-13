package it.swipes.app.publisher.model;

import it.swipes.app.publisher.model.Author;

public class PostPublisher {

    private Long id;
    private String name;
    private String description;
    private String profilePic;
    private Author author;

    public PostPublisher() {
    }

    public PostPublisher(
            Long id,
            String name,
            String description,
            String profilePic,
            Author author
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.profilePic = profilePic;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "PostPublisher{" +
                "id=" + id + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", description='" + description + '\'' + "\n" +
                ", profilePic='" + profilePic + '\'' + "\n" +
                ", author=" + author + "\n" +
                '}';
    }
}
