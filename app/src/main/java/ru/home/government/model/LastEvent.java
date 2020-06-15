package ru.home.government.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastEvent {
    @SerializedName("stage")
    @Expose
    private Stage stage;
    @SerializedName("phase")
    @Expose
    private Phase phase;
    @SerializedName("solution")
    @Expose
    private Object solution;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("document")
    @Expose
    private Object document;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Object getSolution() {
        return solution;
    }

    public void setSolution(Object solution) {
        this.solution = solution;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getDocument() {
        return document;
    }

    public void setDocument(Object document) {
        this.document = document;
    }

}
