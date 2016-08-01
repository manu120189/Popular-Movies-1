package demo.popularmoviesi.services;

import java.util.List;

import demo.popularmoviesi.models.Movie;
import demo.popularmoviesi.models.TheMovieDBResponse;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by emma on 01/11/2015.
 */
public class MovieService {

    private static final String THE_MOVIE_DB_URL = "http://api.themoviedb.org/3";
    private MovieAPI mMovieAPI;

    public MovieAPI getMovieAPI() {
        return mMovieAPI;
    }

    public MovieService(){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(THE_MOVIE_DB_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mMovieAPI = restAdapter.create(MovieAPI.class);
    }

    public interface MovieAPI {
        //sort can be popularity.desc or vote_average.desc
        @GET("/discover/movie")
        public Observable<TheMovieDBResponse>
        getPopularMovies(@Query("api_key") String apiKey, @Query("sort_by") String sort);
    }
}
