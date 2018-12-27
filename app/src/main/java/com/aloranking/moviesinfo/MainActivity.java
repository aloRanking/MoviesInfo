package com.aloranking.moviesinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aloranking.moviesinfo.Adapter.MoviesAdapter;
import com.aloranking.moviesinfo.Model.Movie;
import com.aloranking.moviesinfo.rest.OnGetMoviesCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public EditText editText;
    private Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editext_movie_search);
        mSearchButton = findViewById(R.id.search_btn);
    }

    public void searchMovies(View view) {



        String searchQuery = editText.getText().toString();

        if(TextUtils.isEmpty(searchQuery)){
            Toast.makeText(MainActivity.this, "Enter a searh term", Toast.LENGTH_SHORT)
                    .show();
            return;

        }

        Intent intent = new Intent(MainActivity.this, SearchResult.class);
        intent.putExtra("search", searchQuery);
        startActivity(intent);


    }
}
