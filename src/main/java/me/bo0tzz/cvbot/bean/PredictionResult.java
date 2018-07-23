package me.bo0tzz.cvbot.bean;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PredictionResult {

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("iteration")
    @Expose
    private String iteration;

    @SerializedName("predictions")
    @Expose
    private List<Prediction> predictions = null;

    @SerializedName("project")
    @Expose
    private String project;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIteration() {
        return iteration;
    }

    public void setIteration(String iteration) {
        this.iteration = iteration;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

}