package elena.popularmovies1;

public class Movie {
    private String posterImage;
    private String originalTitle;
    private String rating;
    private String overview;
    private String releaseDate;

    private int id;
    private String videoNames[];
    private String videoYoutubeKeys[];
    private String reviewAuthors[];
    private String reviewContents[];
    private boolean hasReviews;
    private boolean hasVideos;
    private boolean isFav;

    public Movie(String originalTitle, String posterImage, String overview,String rating,String releaseDate,
                   int id,String videoNames[], String videoYoutubeKeys[], String reviewAuthors[], String reviewContents[]
                    ,boolean hasReviews, boolean hasVideos, boolean isFav) {
        this.posterImage = posterImage;
        this.originalTitle = originalTitle;
        this.rating = rating;
        this.overview = overview;
        this.releaseDate=releaseDate;

        this.id=id;
        this.videoNames= videoNames;
        this.videoYoutubeKeys = videoYoutubeKeys;
        this.reviewAuthors = reviewAuthors;
        this.reviewContents = reviewContents;
        this.hasReviews = hasReviews;
        this.hasVideos = hasVideos;
        this.isFav = isFav;
    }

    public Movie() {
        this(null,null,null,null,null,0,null,null,null,null,false,false,false);

    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public String getPosterImage() {
        return posterImage;
    }
    public String getOriginalTitle() {
        return originalTitle;
    }
    public String getRating() {
        return rating;
    }
    public String getOverview() {
        return overview;
    }

    public int getId() {
        return id;
    }
    public String[] getVideoNames() {
        return videoNames;
    }
    public String[] getVideoYoutubeKeys() {
        return videoYoutubeKeys;
    }
    public String[] getReviewAuthors() {
        return reviewAuthors;
    }
    public String[] getReviewContents() {
        return reviewContents;
    }
    public boolean getHasReviews(){ return hasReviews; }
    public boolean getHasVideos(){
        return hasVideos;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideoYoutubeKeys(String[] videoYoutubeKeys) {
        this.videoYoutubeKeys = videoYoutubeKeys;
    }

    public void setVideoNames(String[] videoNames) {
        this.videoNames = videoNames;
    }

    public void setReviewAuthors(String[] reviewAuthors) {
        this.reviewAuthors = reviewAuthors;
    }

    public void setReviewContents(String[] reviewContents) {
        this.reviewContents = reviewContents;
    }

    public void setHasReviews(boolean hasReviews) {
        this.hasReviews = hasReviews;
    }

    public void setHasVideos(boolean hasVideos) {
        this.hasVideos = hasVideos;
    }
    public void setIsFav(boolean isFav) {
        this.isFav = isFav;
    }
}
