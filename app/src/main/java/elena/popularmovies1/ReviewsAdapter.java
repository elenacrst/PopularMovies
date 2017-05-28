package elena.popularmovies1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder>{
    Movie movie;
    String[] authors;
    String[] contents;


    public void setData(Movie movie) {
        if(movie!=null){

            this.movie = movie;
            if(movie.getHasReviews()){
                this.authors=movie.getReviewAuthors();
                this.contents = movie.getReviewContents();
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(authors==null)
            return 0;
        return authors.length;
    }

    @Override
    public ReviewsAdapter.ReviewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ReviewsAdapter.ReviewHolder(view);
    }
    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewHolder holder, int position) {

        String author = authors [position];
        String content = contents [ position];

        holder.textViewAuthor.setText(author);
        holder.textViewContent.setText(content);

    }

    public class ReviewHolder extends RecyclerView.ViewHolder   {
        public TextView textViewAuthor;
        public TextView textViewContent;

        public ReviewHolder(View itemView) {
            super(itemView);
            textViewAuthor =(TextView)itemView.findViewById(R.id.author_tv);
            textViewContent = (TextView) itemView.findViewById(R.id.content_tv);

        }

    }
}
