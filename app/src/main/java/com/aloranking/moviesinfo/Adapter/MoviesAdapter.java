package com.aloranking.moviesinfo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloranking.moviesinfo.Model.Genre;
import com.aloranking.moviesinfo.Model.Movie;
import com.aloranking.moviesinfo.Model.ResultSearched;
import com.aloranking.moviesinfo.R;
import com.aloranking.moviesinfo.rest.ClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

   // private List<Movie> movies;
    List<ResultSearched> resultSearchedList;
    private List<Genre> allGenres;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private static ClickListener clickListener;

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public MoviesAdapter(List<ResultSearched> resultSearchedList, List<Genre> allGenres) {
        this.resultSearchedList = resultSearchedList;
        this.allGenres = allGenres;
        //this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_row, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        // TODO: Populate adapter with movies
        holder.bind(resultSearchedList.get(position));
       // holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return resultSearchedList.size();
        //return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView releaseDate;
        TextView title;
        TextView rating;
        TextView genres;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            releaseDate = itemView.findViewById(R.id.item_movie_release_date);
            title = itemView.findViewById(R.id.item_movie_title);
            rating = itemView.findViewById(R.id.item_movie_rating);
            genres = itemView.findViewById(R.id.item_movie_genre);
            poster = itemView.findViewById(R.id.item_movie_poster);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(ResultSearched resultSearched) {
            releaseDate.setText(resultSearched.getReleaseDate().split("-")[0]);
            title.setText(resultSearched.getTitle());
            rating.setText(String.valueOf(resultSearched.getRating()));
            genres.setText(getGenres(resultSearched.getGenreIds()));
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + resultSearched.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(poster);
        }

        private String getGenres(List<Integer> genreIds) {
            List<String> movieGenres = new ArrayList<>();
            for (Integer genreId : genreIds) {
                for (Genre genre : allGenres) {
                    if (genre.getId() == genreId) {
                        movieGenres.add(genre.getName());
                        break;
                    }
                }
            }
            return TextUtils.join(", ", movieGenres);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);

        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }


        /*public void bind(Movie movies) {
            releaseDate.setText(movies.getReleaseDate().split("-")[0]);
            title.setText(movies.getTitle());
            rating.setText(String.valueOf(movies.getRating()));
            genres.setText("");
        }*/
    }
}
