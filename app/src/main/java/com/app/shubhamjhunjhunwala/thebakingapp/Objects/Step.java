package com.app.shubhamjhunjhunwala.thebakingapp.Objects;

import org.parceler.Parcel;

/**
 * Created by shubham on 14/03/18.
 */

@Parcel
public class Step {
    public String id;
    public String shortDescription;
    public String description;
    public String imageURL;
    public String videoURL;

    public Step() {}

    public Step(String id, String shortDescription, String description, String imageURL, String videoURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.imageURL = imageURL;
        this.videoURL = videoURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
