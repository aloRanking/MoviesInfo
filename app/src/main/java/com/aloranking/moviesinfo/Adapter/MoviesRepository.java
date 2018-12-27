package com.aloranking.moviesinfo.Adapter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.aloranking.moviesinfo.BuildConfig;
import com.aloranking.moviesinfo.Model.MoviesResponse;
import com.aloranking.moviesinfo.rest.OnGetGenresCallback;
import com.aloranking.moviesinfo.rest.OnGetMoviesCallback;
import com.aloranking.moviesinfo.rest.TMDbApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    //private static final String API_KEY = "fbcc57805de502c4c3c2a872efa65083";
    String API_KEY = BuildConfig.ApiKey;

    private static MoviesRepository repository;

    private TMDbApi api;

    private MoviesRepository(TMDbApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(TMDbApi.class));
        }

        return repository;
    }

    /*public void getMovies(final OnGetMoviesCallback callback) {
        api.getPopularMovies(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getResultSearchedList() != null) {
                                callback.onSuccess(moviesResponse.getResultSearchedList());
                                Log.i("Response", "response " + moviesResponse);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }*/




    public void getSearchedMovies(String searchedQuery,final OnGetMoviesCallback callback) {

        /*String searchedQuery = null;
        SearchResult searchResult = new SearchResult();
        searchResult.query = searchedQuery;
        //String searchedQuery = searchResult.query;*/
        Log.i("TAG", "query word is " + searchedQuery);

        api.getSearchedMovie(API_KEY, LANGUAGE, searchedQuery, 1,false)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getResultSearchedList() != null) {
                                callback.onSuccessSearched(moviesResponse.getResultSearchedList());
                                Log.i("Response", "response is " + moviesResponse.getResultSearchedList());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getGenres(final OnGetGenresCallback callback) {
        api.getGenres(API_KEY, LANGUAGE)
                .enqueue(new Callback<GenresResponse>() {
                    @Override
                    public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                        if (response.isSuccessful()) {
                            GenresResponse genresResponse = response.body();
                            if (genresResponse != null && genresResponse.getGenres() != null) {
                                callback.onSuccess(genresResponse.getGenres());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenresResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}
