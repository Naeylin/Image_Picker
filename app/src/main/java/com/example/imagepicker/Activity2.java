package com.example.imagepicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    EditText edText;
    Button btnSearchG;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edText = findViewById(R.id.ed_text);
        btnSearchG = findViewById(R.id.btn_SearchG);


        btnSearchG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerms = edText.getText().toString();
                if (!searchTerms.equals("")) {
                    searchNet(searchTerms);
                }
            }
        });


    }

    private void searchNet (String words) {
        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, words);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            searchNetCompat(words);
        }

    }



    private void searchNetCompat (String words) {
        try {
            Uri uri = Uri.parse("http://www.google.com/#q" + words);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(SearchManager.QUERY, words);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }
}