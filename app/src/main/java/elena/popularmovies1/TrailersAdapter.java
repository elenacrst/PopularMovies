package elena.popularmovies1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrailersAdapter  extends RecyclerView.Adapter<TrailersAdapter.TrailerHolder>{
    Movie movie;
    String[] videosNames;
    private final TrailersAdapter.TrailerAdapterOnClickHandler mClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(String videoKey);
    }

    public TrailersAdapter(TrailersAdapter.TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public void setData(Movie movie) {
        if(movie!=null){

            this.movie = movie;
            if(movie.getHasVideos())
                this.videosNames=movie.getVideoNames();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(videosNames==null)
            return 0;
        return videosNames.length;
    }

    @Override
    public TrailersAdapter.TrailerHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new TrailersAdapter.TrailerHolder(view);
    }
    @Override
    public void onBindViewHolder(TrailersAdapter.TrailerHolder holder, int position) {

        String videoName = videosNames [position];

        holder.textView.setText(videoName);

    }

    public class TrailerHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView textView;

        public TrailerHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.trailer_tv);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            String videoKey=movie.getVideoYoutubeKeys()[position];
            mClickHandler.onClick(videoKey);
        }

    }
}
