package ru.home.government.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Committees {

    @SerializedName("responsible")
    @Expose
    private Responsible responsible;
    @SerializedName("profile")
    @Expose
    private List<Profile> profile = null;
    @SerializedName("soexecutor")
    @Expose
    private List<Object> soexecutor = null;

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

    public List<Profile> getProfile() {
        return profile;
    }

    public void setProfile(List<Profile> profile) {
        this.profile = profile;
    }

    public List<Object> getSoexecutor() {
        return soexecutor;
    }

    public void setSoexecutor(List<Object> soexecutor) {
        this.soexecutor = soexecutor;
    }
}