package elena.popularmovies1.data;

import android.net.Uri;
import android.provider.BaseColumns;
public class MoviesContract {

    public static final String AUTHORITY = "elena.popularmovies1";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_POSTER_IMAGE_PATH = "poster";
        public static final String COLUMN_ORIGINAL_TITLE = "title";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "date";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_HAS_REVIEWS = "reviews";
        public static final String COLUMN_HAS_VIDEOS = "videos";

    }
}
