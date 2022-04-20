package com.example.assignment_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignment_login.Model.CourseModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    private RecyclerView courseRecycle;
    private ProgressBar progressBar;
    private CourseAdapter adapter;
    private ArrayList<CourseModel> courseModelArrayList;
    String url = "https://jsonkeeper.com/b/WO6S";

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        courseRecycle = findViewById(R.id.idRVCourses);
        progressBar = findViewById(R.id.idPB);

        courseModelArrayList = new ArrayList<>();
        getData();
        buildRecyvlerView();
    }


    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(HomePage.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                courseRecycle.setVisibility(View.VISIBLE);
                for (int i = 0 ; i < response.length() ; i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String courseName = jsonObject.getString("courseName");
                        String courseTrack = jsonObject.getString("courseTracks");
                        String courseMode = jsonObject.getString("courseMode");
                        String courseImg = jsonObject.getString("courseimg");
                        courseModelArrayList.add(new CourseModel(courseName,courseImg,courseMode,courseTrack));
                        buildRecyvlerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomePage.this, "Failed to get data...", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void buildRecyvlerView() {
        adapter = new CourseAdapter(courseModelArrayList,HomePage.this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRecycle.setHasFixedSize(true);
        courseRecycle.setLayoutManager(manager);
        courseRecycle.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
        builder.setMessage("Are you want to Exit?");
        builder.setCancelable(false);
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(i);
            }
        });
        builder.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                gsc = GoogleSignIn.getClient(HomePage.this,gso);
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        finish();
                        startActivity(new Intent(HomePage.this, LoginActivity.class));
                    }
                });
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.option,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(this,gso);
//        switch (item.getItemId()){
//            case R.id.logout:
//                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(Task<Void> task) {
//                        finish();
//                        startActivity(new Intent(HomePage.this, LoginActivity.class));
//                    }
//                });
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
//    }
}