package spencerstudios.com.codespace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_actvity);

        final ListView listView = (ListView)findViewById(R.id.list_view) ;

        final ArrayList<Data> dataArrayList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        final DatabaseReference ref = myRef.child("data");

        final ListAdapter listAdapter = new ListAdapter(DataActivity.this, dataArrayList);
        listView.setAdapter(listAdapter);

      ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    String title = (String)ds.child("title").getValue();
                    String description = (String)ds.child("description").getValue();
                    String contributor = (String)ds.child("contributor").getValue();
                    String link = (String)ds.child("link").getValue();
                    long timeStamp = (long)ds.child("timeStamp").getValue();

                    dataArrayList.add(new Data(title, description, contributor, link, timeStamp));
                }

                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
