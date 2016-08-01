package demo.popularmoviesi.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Movie {

    @SerializedName("poster_path") public String movieImage;
    @SerializedName("original_title") public String title;
    @SerializedName("overview") public String overview;
    @SerializedName("release_date") public String releaseDate;
    @SerializedName("vote_average") public Double rate;


    public String getMovieImage() {
        return movieImage;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getRate() {
        return rate;
    }
}
