package elena.popularmovies1;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import elena.popularmovies1.data.MoviesContract;

import static elena.popularmovies1.MainActivity.getIdSQL;
import static elena.popularmovies1.R.id.overview;
import static elena.popularmovies1.R.id.recyclerview;
import static elena.popularmovies1.R.id.recyclerview_reviews;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailerAdapterOnClickHandler {

    TextView detailsTextView;
    ImageView detailsImage;
    TextView overviewText;

    public RecyclerView mRecyclerView;
    public RecyclerView mReviewsRecyclerView;
    public TrailersAdapter trailersAdapter;
    public ReviewsAdapter reviewsAdapter;
    public String[] videosNames;
    static ToggleButton toggleButton;
    static Movie movie = new Movie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailsTextView = (TextView) findViewById(R.id.text_detail);
        detailsImage = (ImageView) findViewById(R.id.detail_image);
        overviewText = (TextView) findViewById(overview);

        Intent intentThatStartedThisActivity = getIntent();

        mReviewsRecyclerView = (RecyclerView) findViewById(recyclerview_reviews);
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewsRecyclerView.setHasFixedSize(true);
        mRecyclerView = (RecyclerView) findViewById(recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        trailersAdapter = new TrailersAdapter(this);
        reviewsAdapter = new ReviewsAdapter();
        mRecyclerView.setAdapter(trailersAdapter);
        mReviewsRecyclerView.setAdapter(reviewsAdapter);


        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.getExtras() != null) {
            if (intentThatStartedThisActivity.hasExtra("posterImage")) {
                movie.setPosterImage(intentThatStartedThisActivity.getStringExtra("posterImage"));
            }
            if (intentThatStartedThisActivity.hasExtra("originalTitle")) {
                movie.setOriginalTitle(intentThatStartedThisActivity.getStringExtra("originalTitle"));

            }

            if (intentThatStartedThisActivity.hasExtra("rating")) {
                movie.setRating(intentThatStartedThisActivity.getStringExtra("rating"));

            }
            if (intentThatStartedThisActivity.hasExtra("overview")) {
                movie.setOverview(intentThatStartedThisActivity.getStringExtra("overview"));

            }
            if (intentThatStartedThisActivity.hasExtra("releaseDate")) {
                movie.setReleaseDate(intentThatStartedThisActivity.getStringExtra("releaseDate"));

            }
            if (intentThatStartedThisActivity.hasExtra("id")) {
                movie.setId(intentThatStartedThisActivity.getIntExtra("id", 0));

            }
            if (intentThatStartedThisActivity.hasExtra("videoNames")) {
                movie.setVideoNames(intentThatStartedThisActivity.getStringArrayExtra("videoNames"));

            }
            if (intentThatStartedThisActivity.hasExtra("videoYoutubeKeys")) {
                movie.setVideoYoutubeKeys(intentThatStartedThisActivity.getStringArrayExtra("videoYoutubeKeys"));

            }
            if (intentThatStartedThisActivity.hasExtra("reviewAuthors")) {
                movie.setReviewAuthors(intentThatStartedThisActivity.getStringArrayExtra("reviewAuthors"));

            }
            if (intentThatStartedThisActivity.hasExtra("reviewContents")) {
                movie.setReviewContents(intentThatStartedThisActivity.getStringArrayExtra("reviewContents"));

            }
            if (intentThatStartedThisActivity.hasExtra("hasReviews")) {
                movie.setHasReviews(intentThatStartedThisActivity.getBooleanExtra("hasReviews", false));

            }
            if (intentThatStartedThisActivity.hasExtra("hasVideos")) {
                movie.setHasVideos(intentThatStartedThisActivity.getBooleanExtra("hasVideos", false));

            }

            if (movie.getHasVideos()) {

                videosNames = movie.getVideoNames();

                if (videosNames.length > 0) {

                    trailersAdapter.setData(movie);
                    mRecyclerView.setAdapter(trailersAdapter);
                }

            }
            if (movie.getHasReviews()) {

                reviewsAdapter.setData(movie);
                mReviewsRecyclerView.setAdapter(reviewsAdapter);

            }
            toggleButton = (ToggleButton) findViewById(R.id.myToggleButton);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.stargrey));

            StringBuffer movieDetails = new StringBuffer("");

            movieDetails.append(getString(R.string.title));
            movieDetails.append(movie.getOriginalTitle());
            movieDetails.append("\n\n");

            String posterUrl = "http://image.tmdb.org/t/p/w342";

            posterUrl += movie.getPosterImage();
            Picasso.with(detailsImage.getContext()).load(posterUrl).fit()
                    .placeholder(R.drawable.notavailable)
                    .error(R.drawable.notavailable)
                    .into(detailsImage);

            StringBuffer overview = new StringBuffer("");

            overview.append(getString(R.string.overview));
            overview.append(movie.getOverview());
            overview.append("\n\n");
            movieDetails.append(getString(R.string.rating));
            movieDetails.append(movie.getRating());
            movieDetails.append("\n\n");
            movieDetails.append(getString(R.string.release_date));
            movieDetails.append(movie.getReleaseDate());
            detailsTextView.setText(movieDetails);
            overviewText.setText(overview);

            Cursor cursor = getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

            int ind = MainActivity.getIdSQL(movie.getId(),cursor);

            if (ind!=-1 ) {
                toggleButton.setChecked(true);
                toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.staryellow));
                Log.v("already fav",movie.getOriginalTitle());
            }

        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Cursor cursor = getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

                int ind = MainActivity.getIdSQL(movie.getId(),cursor);
                if (isChecked  ) {
                    if(getIdSQL(movie.getId(),cursor)==-1){
                        Log.v("inserting",movie.getOriginalTitle());
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MoviesContract.MovieEntry.COLUMN_POSTER_IMAGE_PATH, movie.getPosterImage());
                        contentValues.put(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
                        contentValues.put(MoviesContract.MovieEntry.COLUMN_RATING, movie.getRating());
                        contentValues.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                        contentValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
                        contentValues.put(MoviesContract.MovieEntry.COLUMN_ID, movie.getId());
                        contentValues.put(MoviesContract.MovieEntry.COLUMN_HAS_REVIEWS, movie.getHasReviews());
                        contentValues.put(MoviesContract.MovieEntry.COLUMN_HAS_VIDEOS, movie.getHasVideos());

                        getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, contentValues);
                    }
                    movie.setIsFav(true);
                    toggleButton.setChecked(true);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.staryellow));

                }

                else
                {
                        String stringId = Integer.toString(ind);
                        Uri uri = MoviesContract.MovieEntry.CONTENT_URI;
                        uri = uri.buildUpon().appendPath(stringId).build();

                        getContentResolver().delete(uri, null, null);

                    toggleButton.setChecked(false);
                    movie.setIsFav(false);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.stargrey));
                }

            }
        });

    }

    @Override
    public void onClick(String videoKey) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + videoKey));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

}
