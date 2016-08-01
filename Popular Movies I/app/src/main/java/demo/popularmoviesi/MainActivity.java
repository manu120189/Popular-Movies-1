package demo.popularmoviesi;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import demo.popularmoviesi.fragments.MovieDetailFragment;
import demo.popularmoviesi.fragments.MovieListFragment;
import demo.popularmoviesi.models.Movie;
import demo.popularmoviesi.models.TheMovieDBResponse;
import demo.popularmoviesi.services.MovieService;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieDetailFragment.OnFragmentInteractionListener, MovieListFragment.OnFragmentInteractionListener{

    public static final String TAG_MOVIE_LIST_FRAGMENT = "TAG_MOVIE_LIST_FRAGMENT";
    public static final String MOVIE_DETAIL_FRAGMENT = "MOVIE_DETAIL_FRAGMENT";
    private MovieService movieService;
    //API KEY GOES HERE
    private String MOVIE_API_KEY = "";
    private static List<Movie> moviesInMemory;
    private static final String SORT_MOST_POPULAR = "popularity.desc";
    private static final String SORT_HIGHEST_RATED = "vote_average.desc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MovieListFragment firstFragment = MovieListFragment.newInstance();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment, TAG_MOVIE_LIST_FRAGMENT).commit();
        }

        loadMovies(SORT_MOST_POPULAR);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.sort_most_popular) {
            loadMovies(SORT_MOST_POPULAR);
        }else if(id == R.id.sort_highest_rated){
            loadMovies(SORT_HIGHEST_RATED);
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadMovies(String sort){
        movieService = new MovieService();
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading. Please wait...", true);
        movieService.getMovieAPI()
                .getPopularMovies(MOVIE_API_KEY, sort)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TheMovieDBResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Error loading movies", Toast.LENGTH_SHORT).show();
                        if(dialog != null && dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(TheMovieDBResponse response) {
                        if(response != null){
                            setMoviesInMemory(response.getPopularMovies());
                            refreshMovieList();
                            if(dialog != null && dialog.isShowing()){
                                dialog.dismiss();
                            }
                        }
                    }
                });
    }

    public void refreshMovieList(){
        MovieListFragment movieListFragment =
                (MovieListFragment) getSupportFragmentManager().findFragmentByTag(TAG_MOVIE_LIST_FRAGMENT);
        if(movieListFragment != null){
            movieListFragment.refreshMovieList(getMoviesInMemory());
        }
    }

    public static List<Movie> getMoviesInMemory() {
        return moviesInMemory;
    }

    public static void setMoviesInMemory(List<Movie> moviesInMemory) {
        MainActivity.moviesInMemory = moviesInMemory;
    }

    public void loadMovieDetailFragment(Movie movie){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MovieDetailFragment mdf = MovieDetailFragment.newInstance(movie);
        ft.replace(R.id.fragment_container, mdf);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(MOVIE_DETAIL_FRAGMENT);
        ft.commit();
    }

    public void onFragmentInteraction(Uri uri){

    }
}
