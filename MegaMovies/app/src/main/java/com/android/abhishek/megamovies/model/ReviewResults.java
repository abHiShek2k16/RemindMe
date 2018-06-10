package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

public class ReviewResults {

    @SerializedName(EndPoint.AUTHOR)
    private String author;
    @SerializedName(EndPoint.CONTENT)
    private String content;

    public ReviewResults(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
