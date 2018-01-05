package spencerstudios.com.codespace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import spencerstudios.com.bungeelib.Bungee;


public class DataActivity extends AppCompatActivity {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_actvity);

        if (getSupportActionBar()!=null) getSupportActionBar().setElevation(0f);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        listView = (ListView)findViewById(R.id.list_view) ;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFireBaseData();
            }
        });

        fetchFireBaseData();
    }

    private void fetchFireBaseData(){

        final ArrayList<Data> dataArrayList = new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        final DatabaseReference ref = myRef.child("data");

        final ListAdapter listAdapter = new ListAdapter(DataActivity.this, dataArrayList);
        listView.setAdapter(listAdapter);

        ref.orderByChild("timeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    String title = (String)ds.child("title").getValue();
                    String description = (String)ds.child("description").getValue();
                    String contributor = (String)ds.child("contributor").getValue();
                    String link = (String)ds.child("link").getValue();
                    long timeStamp = (long)ds.child("timeStamp").getValue();

                    dataArrayList.add(new Data(description, contributor, link, timeStamp, title));
                }
                Collections.reverse(dataArrayList);
                listAdapter.notifyDataSetChanged();

                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void click(View v){

        int id = v.getId();

        if (id==R.id.btn_contribute){
            startActivity(new Intent(DataActivity.this, MainActivity.class));
            Bungee.diagonal(DataActivity.this);
        }
    }
}
