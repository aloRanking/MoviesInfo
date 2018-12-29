package com.aloranking.moviesinfo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aloranking.moviesinfo.Adapter.GenresResponse;
import com.aloranking.moviesinfo.Adapter.MoviesAdapter;
import com.aloranking.moviesinfo.Adapter.MoviesRepository;
import com.aloranking.moviesinfo.Model.Genre;
import com.aloranking.moviesinfo.Model.Movie;
import com.aloranking.moviesinfo.Model.ResultSearched;
import com.aloranking.moviesinfo.rest.ClickListener;
import com.aloranking.moviesinfo.rest.OnGetGenresCallback;
import com.aloranking.moviesinfo.rest.OnGetMoviesCallback;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;





public class SearchResult extends AppCompatActivity {

    private RecyclerView moviesList;
    private MoviesAdapter moviesAdapter;

    private MoviesRepository moviesRepository;

    public String query;
    private ProgressBar progressBar;
    RotateLoading rotateLoading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search_result);
        rotateLoading = findViewById(R.id.rotateloading);
        rotateLoading.setLoadingColor(Color.BLUE);

        rotateLoading.start();





        query = getIntent().getStringExtra("search");
        Log.i("TAG", "query word is " + query);


        moviesRepository = MoviesRepository.getInstance();

        moviesList =  findViewById(R.id.result_recyclerView);
        moviesList.setLayoutManager(new LinearLayoutManager(this));
        moviesList.setItemAnimator(new DefaultItemAnimator());


        getGenres();




    }

    private void getGenres() {
        moviesRepository.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                //progressBar.setVisibility(View.VISIBLE);
                getMovies(genres);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void getMovies(final List<Genre> genres) {
        moviesRepository.getSearchedMovies(query,new OnGetMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {


            }

            @Override
            public void onSuccessSearched(final List<ResultSearched> resultSearchedList) {


                rotateLoading.stop();

                moviesAdapter = new MoviesAdapter(resultSearchedList, genres);
                moviesList.setAdapter(moviesAdapter);
                moviesAdapter.setOnItemClickListener(new ClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        ResultSearched result = resultSearchedList.get(position);
                       // GenresResponse genre = new GenresResponse();

                        Intent intent = new Intent(SearchResult.this, MovieDetails.class);
                        intent.putExtra("poster", result.getPosterPath());
                        intent.putExtra("title", result.getTitle());
                        intent.putExtra("overview", result.getOverview());
                        intent.putExtra("rating", result.getRating());
                        intent.putIntegerArrayListExtra("genre", (ArrayList<Integer>) result.getGenreIds());
                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(int position, View v) {

                    }
                });
            }


            @Override
            public void onError() {
                Toast.makeText(SearchResult.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void showError() {
        Toast.makeText(SearchResult.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }

    /*private String getGenres(List<Integer> genreIds) {
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
    }*/



}
