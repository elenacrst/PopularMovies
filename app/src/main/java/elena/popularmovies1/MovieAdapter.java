package elena.popularmovies1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import static elena.popularmovies1.MainActivity.IMG_BASE_URL;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>

{
    Movie[] moviesData;
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public void setData(Movie[] movies) {
        if(movies!=null)
            moviesData = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(moviesData==null)
            return 0;
        return moviesData.length;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieHolder(view);
    }
    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {

        String imgUrl;
        imgUrl = IMG_BASE_URL + moviesData[position].getPosterImage();
        Picasso.with(holder.imageView.getContext()).load(imgUrl).fit()
                .into(holder.imageView);

    }

    public class MovieHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public ImageView imageView;

        public MovieHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            Movie movie=moviesData[position];
            mClickHandler.onClick(movie);
        }

    }
}
