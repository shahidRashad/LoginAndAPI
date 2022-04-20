package com.example.assignment_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView Cname, Cmode, Ctrack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        imageView = findViewById(R.id.imgCrs);
        Cname = findViewById(R.id.NameCrs);
        Cmode = findViewById(R.id.ModeCrs);
        Ctrack = findViewById(R.id.TrackCrs);

        Intent i = getIntent();
        Cname.setText(i.getStringExtra("name"));
        Cmode.setText(i.getStringExtra("mode"));
        Ctrack.setText(i.getStringExtra("track"));
        Picasso.get().load(i.getStringExtra("image")).into(imageView);
    }
}