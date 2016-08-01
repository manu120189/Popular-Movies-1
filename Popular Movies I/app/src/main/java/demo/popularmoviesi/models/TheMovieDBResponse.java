package demo.popularmoviesi.models;

import java.util.List;

/**
 * Created by emma on 01/11/2015.
 */
public class TheMovieDBResponse {
    public int page;
    public List<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getPopularMovies() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
