package ru.home.government.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GovResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("wording")
    @Expose
    private String wording;
    @SerializedName("laws")
    @Expose
    private List<Law> laws = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public List<Law> getLaws() {
        return laws;
    }

    public void setLaws(List<Law> laws) {
        this.laws = laws;
    }

}
