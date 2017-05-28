package elena.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;

import elena.popularmovies1.data.MoviesContract;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler

{
    public static Movie[] movies;
    public static RecyclerView mRecyclerView;
    public static MovieAdapter movieAdapter;

    public static TextView errorView;
    public static ProgressBar mLoading;

    public static String popularOrRated = "popular";

    public static Cursor mCursor = null;
    public static Movie[] favMovies;

    int lastOptionMenu=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        errorView = (TextView) findViewById(R.id.errormessage);
        mLoading = (ProgressBar) findViewById(R.id.pb_loading_indicator);


        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;

        int numCol = (int) (dpWidth / 180);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numCol));
        mRecyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(movieAdapter);

        if(savedInstanceState!=null && savedInstanceState.getString("poprated")!=null){
            popularOrRated = savedInstanceState.getString("poprated");
        }
        if(savedInstanceState!=null){
            lastOptionMenu = savedInstanceState.getInt("lastoption");
        }

        if(lastOptionMenu == 1)
            loadData(true);
        else
         loadData(false);
        if(savedInstanceState != null)
        {
            scrollPosition = savedInstanceState.getInt("pos");
            mRecyclerView.scrollToPosition(scrollPosition);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("poprated",popularOrRated);
        outState.putInt("lastoption",lastOptionMenu);
        scrollPosition = ((GridLayoutManager)mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        outState.putInt("pos",scrollPosition);

    }

   public static int scrollPosition;
    @Override
    protected void onPause() {
        super.onPause();

        scrollPosition = ((GridLayoutManager)mRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.scrollToPosition(scrollPosition);

    }

    public static void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.VISIBLE);
    }

    public static void showContent() {
        mRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        scrollPosition = 0;
        if (id == R.id.sort_popular) {
            popularOrRated = "popular";
            lastOptionMenu = 0;
            loadData(false);
        }
        if (id == R.id.sort_rated) {

            popularOrRated = "top_rated";
            lastOptionMenu = 0;
            loadData(false);

        }
        if( id == R.id.favorites ){
            lastOptionMenu = 1;
            loadData(true);
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadData(boolean fav) {
        if(!fav){
            new FetchMoviesTask().execute(popularOrRated);
        }

        else{
            movies = favMovies;
            if (movies != null) {
                MainActivity.movieAdapter.setData(movies);
                MainActivity.showContent();
            }
            else{
                MainActivity.showError();
            }

            mRecyclerView.setAdapter(movieAdapter);
        }
    }

    public static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w342";

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        Log.v("posterImage",movie.getPosterImage());
        intentToStartDetailActivity.putExtra("posterImage",movie.getPosterImage());
        intentToStartDetailActivity.putExtra("originalTitle",movie.getOriginalTitle());
        intentToStartDetailActivity.putExtra("rating",movie.getRating());
        intentToStartDetailActivity.putExtra("overview",movie.getOverview());
        intentToStartDetailActivity.putExtra("releaseDate",movie.getReleaseDate());
        intentToStartDetailActivity.putExtra("id",movie.getId());
        intentToStartDetailActivity.putExtra("videoNames",movie.getVideoNames());
        intentToStartDetailActivity.putExtra("videoYoutubeKeys",movie.getVideoYoutubeKeys());
        intentToStartDetailActivity.putExtra("reviewAuthors",movie.getReviewAuthors());
        intentToStartDetailActivity.putExtra("reviewContents",movie.getReviewContents());
        intentToStartDetailActivity.putExtra("hasReviews",movie.getHasReviews());
        intentToStartDetailActivity.putExtra("hasVideos",movie.getHasVideos());

        startActivity(intentToStartDetailActivity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;

        int numCol = (int) (dpWidth / 180);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numCol));

       mRecyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(movieAdapter);


        mCursor = getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        mCursor.moveToFirst();
        favMovies = null;
        favMovies = new Movie[mCursor.getCount()];

        int posterIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER_IMAGE_PATH);
        int ratingIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RATING);
        int titleIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        int overviewIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW);
        int dateIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE);
        int idIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ID);
        int hasReviewsIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_HAS_REVIEWS);
        int hasVideosIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_HAS_VIDEOS);

        for (int i = 0; i < mCursor.getCount(); i++) {

            mCursor.moveToPosition(i);
            favMovies[i] = new Movie();
            String poster = mCursor.getString(posterIndex);
            favMovies[i].setPosterImage(poster);
            String rating = mCursor.getString(ratingIndex);
            favMovies[i].setRating(rating);
            String title = mCursor.getString(titleIndex);
            favMovies[i].setOriginalTitle(title);
            String overview = mCursor.getString(overviewIndex);
            favMovies[i].setOverview(overview);
            String date = mCursor.getString(dateIndex);
            favMovies[i].setReleaseDate(date);
            int id = mCursor.getInt(idIndex);
            favMovies[i].setId(id);
            String hasReviews = mCursor.getString(hasReviewsIndex);
            favMovies[i].setHasReviews(Boolean.getBoolean(hasReviews));
            String hasVideos = mCursor.getString(hasVideosIndex);
            favMovies[i].setHasVideos(Boolean.getBoolean(hasVideos));
            boolean isFav = true;
            favMovies[i].setIsFav(isFav);
            try {
                OpenUtils.setVideosFromJSON(favMovies[i]);
            } catch (Exception e) {

            }
            try {
                OpenUtils.setReviewsFromJSON(favMovies[i]);
            } catch (Exception e) {

            }

        }
      if(lastOptionMenu==1)
          loadData(true);
        else
          loadData(false);

    }
    public static int getIdSQL(int id, Cursor mCursor){

        mCursor.moveToFirst();
        int idIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ID);
        int index=mCursor.getColumnIndex(MoviesContract.MovieEntry._ID);
        for (int i = 0; i < mCursor.getCount(); i++){
            mCursor.moveToPosition(i);
            int idval = mCursor.getInt(idIndex);
            if(idval == id){
                int ind = mCursor.getInt(index);
                return ind;
            }
        }
        return -1;
    }

}