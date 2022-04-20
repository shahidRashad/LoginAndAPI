package com.example.assignment_login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_login.Model.CourseModel;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private ArrayList<CourseModel> courseModelArrayList;
    private Context context;

    public CourseAdapter(ArrayList<CourseModel> courseModelArrayList, Context context) {
        this.courseModelArrayList = courseModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_adapter_activity,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseModel model = courseModelArrayList.get(position);
        holder.name.setText(model.getCourseName());
        holder.mode.setText(model.getCourseMode());
        holder.track.setText(model.getCourseTrack());
        Picasso.get().load(model.getCourseImg()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailedActivity.class);
                intent.putExtra("name",model.getCourseName());
                intent.putExtra("mode",model.getCourseMode());
                intent.putExtra("track",model.getCourseTrack());
                intent.putExtra("image",model.getCourseImg());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, mode, track;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.idTvName);
            mode = itemView.findViewById(R.id.idTvMode);
            track = itemView.findViewById(R.id.idTvTrack);
            imageView = itemView.findViewById(R.id.idIVCourse);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
