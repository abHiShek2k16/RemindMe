package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvDetail {

    @SerializedName(EndPoint.BACKDROP_PATH)
    private String backdropPath;
    @SerializedName(EndPoint.EPISODE_RUNTIME)
    private ArrayList<Integer> runTime;
    @SerializedName(EndPoint.CREATED_BY)
    private ArrayList<TvCreatedByResults> tvCreatedByResults;
    @SerializedName(EndPoint.FIRST_AIR_DATE)
    private String firstAirDate;
    @SerializedName(EndPoint.LAST_AIR_DATE)
    private String lastAirDate;
    @SerializedName(EndPoint.NAME)
    private String name;
    @SerializedName(EndPoint.NO_OF_EPISODE)
    private String noOfEpisode;
    @SerializedName(EndPoint.NO_OF_SEASON)
    private String noOfSeason;
    @SerializedName(EndPoint.OVERVIEW)
    private String overview;
    @SerializedName(EndPoint.POSTER_PATH)
    private String posterPath;
    @SerializedName(EndPoint.VOTE_AVERAGE)
    private String voteAvg;
    @SerializedName(EndPoint.VOTE_COUNT)
    private String voteCount;
    @SerializedName(EndPoint.VIDEOS)
    private Videos videos;
    @SerializedName(EndPoint.REVIEW)
    private Review review;
    @SerializedName(EndPoint.PRODUCTION)
    private ArrayList<ProductionCompany> productionCompanies;

    public TvDetail(String backdropPath, ArrayList<Integer> runTime, ArrayList<TvCreatedByResults> tvCreatedByResults, String firstAirDate, String lastAirDate, String name, String noOfEpisode, String noOfSeason, String overview, String posterPath, String voteAvg, String voteCount, Videos videos, Review review, ArrayList<ProductionCompany> productionCompanies) {
        this.backdropPath = backdropPath;
        this.runTime = runTime;
        this.tvCreatedByResults = tvCreatedByResults;
        this.firstAirDate = firstAirDate;
        this.name = name;
        this.noOfEpisode = noOfEpisode;
        this.noOfSeason = noOfSeason;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAvg = voteAvg;
        this.voteCount = voteCount;
        this.videos = videos;
        this.review = review;
        this.productionCompanies = productionCompanies;
        this.lastAirDate = lastAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public ArrayList<Integer> getRunTime() {
        return runTime;
    }

    public ArrayList<TvCreatedByResults> getTvCreatedByResults() {
        return tvCreatedByResults;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public String getName() {
        return name;
    }

    public String getNoOfEpisode() {
        return noOfEpisode;
    }

    public String getNoOfSeason() {
        return noOfSeason;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public Videos getVideos() {
        return videos;
    }

    public Review getReview() {
        return review;
    }

    public ArrayList<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }
}
