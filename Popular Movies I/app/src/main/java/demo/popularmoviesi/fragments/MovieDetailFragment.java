package demo.popularmoviesi.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import demo.popularmoviesi.R;
import demo.popularmoviesi.models.Movie;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment{

    private Movie movie;
    private OnFragmentInteractionListener mListener;

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setMovie(movie);
        return fragment;
    }
    public void setMovie(Movie movie){
        this.movie = movie;
    }
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        TextView movieTitle = (TextView) getView().findViewById(R.id.movieTitle);
        TextView movieDescription = (TextView) getView().findViewById(R.id.movieDescription);
        TextView releaseDate = (TextView) getView().findViewById(R.id.releaseDate);
        TextView voteAverage = (TextView) getView().findViewById(R.id.averageVote);

        if(movieTitle != null){
            movieTitle.setText(movie.getTitle());
        }
        if(movieDescription != null){
            movieDescription.setText(movie.getOverview());
        }
        if(releaseDate != null){
            releaseDate.setText(movie.getReleaseDate());
        }
        if(voteAverage != null){
            voteAverage.setText(movie.getRate().toString());
        }
        ImageView moviePoster = (ImageView)getView().findViewById(R.id.moviePoster);
        if(moviePoster != null){
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + movie.getMovieImage()).placeholder(R.drawable.placeholder).into(moviePoster);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        void loadMovieDetailFragment(Movie movie);
    }


}
