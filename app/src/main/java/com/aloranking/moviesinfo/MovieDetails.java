package com.aloranking.moviesinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloranking.moviesinfo.Model.Genre;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails extends AppCompatActivity {
    private TextView title,overview,rating,genre;
    private ImageView movieImage;

    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private List<Genre> allGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        title = findViewById(R.id.movie_detail_title);
        overview = findViewById(R.id.movie_detail_description);
        rating = findViewById(R.id.movie_detail_rate);
        genre = findViewById(R.id.movie_genre_detail);
        movieImage = findViewById(R.id.movie_detail_poster);

        allGenres = new ArrayList<>();


        Bundle extras = getIntent().getExtras();
        if(extras==null){
            return;
        }
         String movieTitle = extras.getString("title");
        float movieRating = extras.getFloat("rating");
        String movieOverview = extras.getString("overview");
        String moviePoster = extras.getString("poster");

        ArrayList<Integer> test = extras.getIntegerArrayList("genre");

        title.setText(movieTitle);
        overview.setText(movieOverview);
        rating.setText(String.valueOf(movieRating));
        genre.setText(getGenres(test));

        Glide.with(this).load(IMAGE_BASE_URL + moviePoster)
                .into(movieImage);

        //getGenres(test);




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

}
