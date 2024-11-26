package ru.home.government.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subject {

    @SerializedName("deputies")
    @Expose
    private List<Deputy> deputies = null;
    @SerializedName("departments")
    @Expose
    private List<Object> departments = null;
    @SerializedName("factions")
    @Expose
    private List<Faction> factions = null;

    public List<Deputy> getDeputies() {
        return deputies;
    }

    public void setDeputies(List<Deputy> deputies) {
        this.deputies = deputies;
    }

    public List<Object> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Object> departments) {
        this.departments = departments;
    }

    public List<Faction> getFactions() {
        return factions;
    }

    public void setFactions(List<Faction> factions) {
        this.factions = factions;
    }
}
