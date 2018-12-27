package com.aloranking.moviesinfo.rest;

import com.aloranking.moviesinfo.Model.Genre;
import com.aloranking.moviesinfo.Model.ResultSearched;

import java.util.List;

public interface OnGetGenresCallback {

    void onSuccess(List<Genre> genres);

    void onError();
}
