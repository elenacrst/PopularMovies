package elena.popularmovies1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class OpenUtils {

        public static Movie[] getStringsFromJson(String jsonStr)
                throws JSONException {

            final String LIST = "results";

            final String ORIGINAL_TITLE="original_title";
            final String POSTER_IMAGE="poster_path";
            final String OVERVIEW="overview";
            final String RATING="vote_average";
            final String RELEASE_DATE="release_date";

            final String ID = "id";
            final String VIDEO_YOUTUBE_KEY = "key";
            final String VIDEO_NAME = "name";
            final String REVIEW_AUTHOR = "author";
            final String REVIEW_CONTENT = "content";

            Movie[] movies;

            JSONObject jsonObject = new JSONObject(jsonStr);


            JSONArray jsonArray = jsonObject.getJSONArray(LIST);

            movies= new Movie[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                String originalTitle;
                String posterImage;
                String overview;
                String rating;
                String releaseDate;

                int id;

                JSONObject arrayItem = jsonArray.getJSONObject(i);

                originalTitle=arrayItem.getString(ORIGINAL_TITLE);
                posterImage=arrayItem.getString(POSTER_IMAGE);
                overview=arrayItem.getString(OVERVIEW);
                rating=arrayItem.getString(RATING);
                releaseDate=arrayItem.getString(RELEASE_DATE);

                id = arrayItem.getInt(ID);
                boolean hasReviews;
                boolean hasVideos;
                URL videosRequestUrl = NetworkUtils.videosUrl(id);
                String jsonResponseVideos = null;
                try {
                    jsonResponseVideos = NetworkUtils
                            .getResponseFromHttpUrl(videosRequestUrl);
                } catch (Exception e) {

                }
                String[] videoNames=null;
                String[] videoYoutubeKeys=null;
                if(jsonResponseVideos  != null){
                    JSONObject jsonObjectVideos = new JSONObject(jsonResponseVideos);
                    JSONArray jsonArrayVideos = jsonObjectVideos.getJSONArray(LIST);
                    videoNames= new String[jsonArrayVideos.length()];
                    videoYoutubeKeys= new String[jsonArrayVideos.length()];
                    for (int j = 0; j < jsonArrayVideos.length(); j++) {

                        String youtubeKey;
                        String name;
                        JSONObject arrayItemVideos = jsonArrayVideos.getJSONObject(j);
                        youtubeKey=arrayItemVideos.getString(VIDEO_YOUTUBE_KEY);
                        name=arrayItemVideos.getString(VIDEO_NAME);
                        videoNames[j]=name;
                        videoYoutubeKeys[j] = youtubeKey;

                    }
                    hasVideos=true;
                }
                else
                    hasVideos = false;
                URL reviewsRequestUrl = NetworkUtils.reviewsUrl(id);
                String jsonResponseReviews = null;
                try {
                    jsonResponseReviews = NetworkUtils
                            .getResponseFromHttpUrl(reviewsRequestUrl);
                } catch (Exception e) {

                }
                String[] authors=null;
                String[] contents=null;
                if(jsonResponseReviews  != null){
                    JSONObject jsonObjectReviews = new JSONObject(jsonResponseReviews);
                    JSONArray jsonArrayReviews = jsonObjectReviews.getJSONArray(LIST);
                    authors= new String[jsonArrayReviews.length()];
                    contents= new String[jsonArrayReviews.length()];
                    for (int j = 0; j < jsonArrayReviews.length(); j++) {

                        String author;
                        String content;
                        JSONObject arrayItemReviews = jsonArrayReviews.getJSONObject(j);
                        author=arrayItemReviews.getString(REVIEW_AUTHOR);
                        content=arrayItemReviews.getString(REVIEW_CONTENT);
                        authors[j]=author;
                        contents[j] = content;
                    }
                    hasReviews=true;
                }
                else
                    hasReviews = false;

                movies[i]=new Movie(originalTitle,posterImage,overview,rating,releaseDate,id,
                        videoNames,videoYoutubeKeys,authors,contents,hasReviews, hasVideos, false);

            }

            return movies;
        }

    public static void setVideosFromJSON(Movie movie) throws JSONException{
        final String LIST = "results";

        int id = movie.getId();
        URL videosRequestUrl = NetworkUtils.videosUrl(id);
        String jsonResponseVideos = null;
        try {
            jsonResponseVideos = NetworkUtils
                    .getResponseFromHttpUrl(videosRequestUrl);
        } catch (Exception e) {

        }
        String[] videoNames=null;
        String[] videoYoutubeKeys=null;

        final String VIDEO_YOUTUBE_KEY = "key";
        final String VIDEO_NAME = "name";

        if(jsonResponseVideos  != null){
            JSONObject jsonObjectVideos = new JSONObject(jsonResponseVideos);
            JSONArray jsonArrayVideos = jsonObjectVideos.getJSONArray(LIST);
            videoNames= new String[jsonArrayVideos.length()];
            videoYoutubeKeys= new String[jsonArrayVideos.length()];
            for (int j = 0; j < jsonArrayVideos.length(); j++) {

                String youtubeKey;
                String name;
                JSONObject arrayItemVideos = jsonArrayVideos.getJSONObject(j);
                youtubeKey=arrayItemVideos.getString(VIDEO_YOUTUBE_KEY);
                name=arrayItemVideos.getString(VIDEO_NAME);
                videoNames[j]=name;
                videoYoutubeKeys[j] = youtubeKey;

            }

        }

        movie.setVideoYoutubeKeys(videoYoutubeKeys);
        movie.setVideoNames(videoNames);


    }
    public static void setReviewsFromJSON(Movie movie) throws JSONException{
        final String LIST = "results";

        int id = movie.getId();
        URL reviewsRequestUrl = NetworkUtils.reviewsUrl(id);
        String jsonResponseReviews = null;
        try {
            jsonResponseReviews = NetworkUtils
                    .getResponseFromHttpUrl(reviewsRequestUrl);
        } catch (Exception e) {

        }
        String[] authors=null;
        String[] contents=null;
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";
        if(jsonResponseReviews  != null){
            JSONObject jsonObjectReviews = new JSONObject(jsonResponseReviews);
            JSONArray jsonArrayReviews = jsonObjectReviews.getJSONArray(LIST);
            authors= new String[jsonArrayReviews.length()];
            contents= new String[jsonArrayReviews.length()];
            for (int j = 0; j < jsonArrayReviews.length(); j++) {

                String author;
                String content;
                JSONObject arrayItemReviews = jsonArrayReviews.getJSONObject(j);
                author=arrayItemReviews.getString(REVIEW_AUTHOR);
                content=arrayItemReviews.getString(REVIEW_CONTENT);
                authors[j]=author;
                contents[j] = content;

            }

        }

    }

}