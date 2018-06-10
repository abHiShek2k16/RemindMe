package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class List {

    @SerializedName(EndPoint.TOTAL_PAGES)
    private int totalPages;
    @SerializedName(EndPoint.RESULTS)
    private ArrayList<ListResults> results;

    public List(int totalPages, ArrayList<ListResults> results){
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<ListResults> getResults() {
        return results;
    }
}
