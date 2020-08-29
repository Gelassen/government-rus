package ru.home.government.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class VotesResponse {

    @SerializedName("totalCount")
    @Expose
    private String totalCount = "";
    @SerializedName("page")
    @Expose
    private String page = "";
    @SerializedName("pageSize")
    @Expose
    private String pageSize = "";
    @SerializedName("wording")
    @Expose
    private String wording = "";
    @SerializedName("votes")
    @Expose
    private List<Vote> votes = Collections.emptyList();

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

}
