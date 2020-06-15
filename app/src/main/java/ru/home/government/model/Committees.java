package ru.home.government.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Committees {

    @SerializedName("responsible")
    @Expose
    private Object responsible;
    @SerializedName("profile")
    @Expose
    private List<Object> profile = null;
    @SerializedName("soexecutor")
    @Expose
    private List<Object> soexecutor = null;

    public Object getResponsible() {
        return responsible;
    }

    public void setResponsible(Object responsible) {
        this.responsible = responsible;
    }

    public List<Object> getProfile() {
        return profile;
    }

    public void setProfile(List<Object> profile) {
        this.profile = profile;
    }

    public List<Object> getSoexecutor() {
        return soexecutor;
    }

    public void setSoexecutor(List<Object> soexecutor) {
        this.soexecutor = soexecutor;
    }
}