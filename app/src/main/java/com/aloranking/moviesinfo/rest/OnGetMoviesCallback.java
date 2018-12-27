package com.aloranking.moviesinfo.rest;

import com.aloranking.moviesinfo.Model.Movie;
import com.aloranking.moviesinfo.Model.ResultSearched;

import java.util.List;

public interface OnGetMoviesCallback {

    void onSuccess(List<Movie> movies);

    void onSuccessSearched(List<ResultSearched> resultSearchedList);

    void onError();
}
