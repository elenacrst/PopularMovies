package elena.popularmovies1;

import android.os.AsyncTask;
import android.view.View;

import java.net.URL;

import static android.os.Build.VERSION_CODES.M;
import static elena.popularmovies1.MainActivity.mRecyclerView;
import static elena.popularmovies1.MainActivity.movieAdapter;
import static elena.popularmovies1.MainActivity.movies;

public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

    int nr=0;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.mLoading.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Movie[] theMovies) {
        super.onPostExecute(theMovies);
        MainActivity.mLoading.setVisibility(View.INVISIBLE);
        nr++;

        movies = theMovies;
        if (movies != null) {
            MainActivity.movieAdapter.setData(movies);
            MainActivity.showContent();
        }
        else
            {
            if(nr==1)
               new FetchMoviesTask().execute(MainActivity.popularOrRated);
            else
                MainActivity.showError();
        }

        mRecyclerView.setAdapter(movieAdapter);
        mRecyclerView.scrollToPosition(MainActivity.scrollPosition);
    }



    @Override
    protected Movie[] doInBackground(String... strings) {

        if (strings.length == 0) {
            return null;
        }

        String popOrRated = strings[0];
        URL movieRequestUrl = NetworkUtils.buildUrl(popOrRated);

        try {
            String jsonResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieRequestUrl);

            Movie[] simpleJsonData = OpenUtils
                    .getStringsFromJson(jsonResponse);

            return simpleJsonData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

