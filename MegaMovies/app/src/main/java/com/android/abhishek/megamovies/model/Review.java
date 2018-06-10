package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Review {
    @SerializedName(EndPoint.RESULTS)
    private ArrayList<ReviewResults> movieReviewResults;
    @SerializedName(EndPoint.TOTAL_PAGES)
    private int totalPages;
    @SerializedName(EndPoint.TOTAL_RESULTS)
    private int totalResults;

    public Review(ArrayList<ReviewResults> movieReviewResults, int totalPages, int totalResults) {
        this.movieReviewResults = movieReviewResults;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public ArrayList<ReviewResults> getMovieReviewResults() {
        return movieReviewResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
