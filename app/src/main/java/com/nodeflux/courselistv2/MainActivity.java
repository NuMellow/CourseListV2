package com.nodeflux.courselistv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView coursesResult;
    private DatabaseReference courseDb;

    private String courseIndex;
    private ArrayList<String> courses;
    private ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseIndex = "Namibia-Agriculture";

        courseDb = FirebaseDatabase.getInstance().getReference().child("Course");

        lstView = (ListView) findViewById(R.id.courses_list);

        coursesResult = (RecyclerView) findViewById(R.id.courses_result);
        coursesResult.setHasFixedSize(true);
        coursesResult.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Courses, CourseViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Courses, CourseViewHolder>(
                Courses.class,
                R.layout.courses_row,
                CourseViewHolder.class,
                courseDb.orderByChild("Country").equalTo(courseIndex)
        ) {
            @Override
            protected void populateViewHolder(CourseViewHolder viewHolder, Courses model, int position) {

                viewHolder.setUniName(model.getUniversity());
                viewHolder.setCourses();
            }
        };

        coursesResult.setAdapter(firebaseRecyclerAdapter);
    }


    public class CourseViewHolder extends RecyclerView.ViewHolder
    {
        View myView;

        public CourseViewHolder(View itemView)
        {
            super(itemView);

            myView = itemView;
        }

        public void setUniName(String name)
        {
            TextView uniName = (TextView) myView.findViewById(R.id.textUniName);
            uniName.setText(name);
        }

        public void setCourses()
        {
            MainActivity.this.retrieveData();
        }
    }

    private void retrieveData()
    {
        courseDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getData(DataSnapshot ds)
    {
        courses.clear();

        for(DataSnapshot data:ds.getChildren())
        {
            Courses c = new Courses();
            c.setName(data.getValue(Courses.class).getName());

            courses.add(c.getName());
        }

        if(courses.size() > 0)
        {
            ArrayAdapter adp = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, courses);
            lstView.setAdapter(adp);

        }else
        {
            Toast.makeText(MainActivity.this, "No Data Found",Toast.LENGTH_SHORT).show();
        }
    }
}
