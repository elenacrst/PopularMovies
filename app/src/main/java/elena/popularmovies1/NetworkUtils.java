package elena.popularmovies1;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY="";//TODO INSERT YOUR API KEY HERE

    private static final String API_QUERY="api_key";

    private static final String VIDEOS = "/videos";
    private static final String REVIEWS = "/reviews";

    public static java.net.URL buildUrl(String popularOrRated) {

        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(popularOrRated)
                .appendQueryParameter(API_QUERY,API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static java.net.URL videosUrl(int id) {

        String videoBaseUrl = MOVIE_BASE_URL+id+VIDEOS;
        Uri  builtUri= Uri.parse(videoBaseUrl).buildUpon()
                .appendQueryParameter(API_QUERY,API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static java.net.URL reviewsUrl(int id) {

        String reviewsBaseUrl = MOVIE_BASE_URL+id+REVIEWS;
        Uri  builtUri= Uri.parse(reviewsBaseUrl).buildUpon()
                .appendQueryParameter(API_QUERY,API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }
}