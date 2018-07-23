package me.bo0tzz.cvbot.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prediction {

    @SerializedName("boundingBox")
    @Expose
    private Object boundingBox;

    @SerializedName("probability")
    @Expose
    private Double probability;

    @SerializedName("tagId")
    @Expose
    private String tagId;

    @SerializedName("tagName")
    @Expose
    private String tagName;

    public Object getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Object boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}