package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

public class ProductionCompany {

    @SerializedName(EndPoint.LOGO_PATH)
    private String logoPath;
    @SerializedName(EndPoint.NAME)
    private String name;

    public ProductionCompany(String logoPath, String name) {
        this.logoPath = logoPath;
        this.name = name;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getName() {
        return name;
    }
}
