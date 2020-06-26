package ru.home.government.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Law {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("comments")
    @Expose
    private Object comments;
    @SerializedName("introductionDate")
    @Expose
    private String introductionDate;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("transcriptUrl")
    @Expose
    private String transcriptUrl;
    @SerializedName("lastEvent")
    @Expose
    private LastEvent lastEvent;
    @SerializedName("subject")
    @Expose
    private Subject subject;
    @SerializedName("committees")
    @Expose
    private Committees committees;
    @SerializedName("type")
    @Expose
    private Type type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getComments() {
        return comments;
    }

    public void setComments(Object comments) {
        this.comments = comments;
    }

    public String getIntroductionDate() {
        return introductionDate;
    }

    public void setIntroductionDate(String introductionDate) {
        this.introductionDate = introductionDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTranscriptUrl() {
        return transcriptUrl;
    }

    public void setTranscriptUrl(String transcriptUrl) {
        this.transcriptUrl = transcriptUrl;
    }

    public LastEvent getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(LastEvent lastEvent) {
        this.lastEvent = lastEvent;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Committees getCommittees() {
        return committees;
    }

    public void setCommittees(Committees committees) {
        this.committees = committees;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Law)) return false;
        Law law = (Law) o;
        return Objects.equals(getId(), law.getId()) &&
                Objects.equals(getNumber(), law.getNumber()) &&
                Objects.equals(getName(), law.getName()) &&
                Objects.equals(getComments(), law.getComments()) &&
                Objects.equals(getIntroductionDate(), law.getIntroductionDate()) &&
                Objects.equals(getUrl(), law.getUrl()) &&
                Objects.equals(getTranscriptUrl(), law.getTranscriptUrl()) &&
                Objects.equals(getLastEvent(), law.getLastEvent()) &&
                Objects.equals(getSubject(), law.getSubject()) &&
                Objects.equals(getCommittees(), law.getCommittees()) &&
                Objects.equals(getType(), law.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(), getNumber(), getName(), getComments(), getIntroductionDate(), getUrl(),
                getTranscriptUrl(), getLastEvent(), getSubject(), getCommittees(), getType()
        );
    }
}
