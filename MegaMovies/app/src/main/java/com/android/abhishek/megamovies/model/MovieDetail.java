package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieDetail {

    //  Detail Api
    @SerializedName(EndPoint.ADULT)
    private String adult;
    @SerializedName(EndPoint.BACKDROP_PATH)
    private String backdropPath;
    @SerializedName(EndPoint.OVERVIEW)
    private String overview;
    @SerializedName(EndPoint.POSTER_PATH)
    private String posterPath;
    @SerializedName(EndPoint.PRODUCTION)
    private ArrayList<ProductionCompany> productionCompanies;
    @SerializedName(EndPoint.RELEASE_DATE)
    private String releaseDate;
    @SerializedName(EndPoint.RUNTIME)
    private String runtime;
    @SerializedName(EndPoint.STATUS)
    private String status;
    @SerializedName(EndPoint.TITLE)
    private String title;
    @SerializedName(EndPoint.VOTE_AVERAGE)
    private String voteAvg;
    @SerializedName(EndPoint.VOTE_COUNT)
    private String voteCount;

    //  Videos
    @SerializedName(EndPoint.VIDEOS)
    private Videos movieVideos;

    //  Review
    @SerializedName(EndPoint.REVIEW)
    private Review movieReview;

    //  Casts
    @SerializedName(EndPoint.CASTS)
    private MovieCasts movieCasts;

    public MovieDetail(String adult, String backdropPath, String overview, String posterPath, ArrayList<ProductionCompany> productionCompanies, String releaseDate, String runtime, String status, String title, String voteAvg, String voteCount, Videos movieVideos, Review movieReview, MovieCasts movieCasts) {
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.posterPath = posterPath;
        this.productionCompanies = productionCompanies;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.status = status;
        this.title = title;
        this.voteAvg = voteAvg;
        this.voteCount = voteCount;
        this.movieVideos = movieVideos;
        this.movieReview = movieReview;
        this.movieCasts = movieCasts;
    }

    public String getAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public ArrayList<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public Videos getMovieVideos() {
        return movieVideos;
    }

    public Review getMovieReview() {
        return movieReview;
    }

    public MovieCasts getMovieCasts() {
        return movieCasts;
    }
}
