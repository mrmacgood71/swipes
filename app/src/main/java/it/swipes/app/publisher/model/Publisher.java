package it.swipes.app.publisher.model;

public class Publisher {
    private Long id;
    private String name;
    private String description;
    private String profilePic;

    public Publisher() {
    }

    public Publisher(String name, String description, String profilePic) {
        this.name = name;
        this.description = description;
        this.profilePic = profilePic;
    }

    public Publisher(Long id, String name, String description, String profilePic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.profilePic = profilePic;
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
}
