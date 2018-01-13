package spencerstudios.com.codespace;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.frederico.showtipsview.ShowTipsBuilder;
import net.frederico.showtipsview.ShowTipsView;
import net.frederico.showtipsview.ShowTipsViewInterface;

import java.util.ArrayList;
import java.util.Collections;

import spencerstudios.com.bungeelib.Bungee;
import spencerstudios.com.fab_toast.FabToast;


public class LauncherActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Data> temp, dataArrayList;

    private Button btnContribute, btnSave;
    private SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnContribute = (Button)findViewById(R.id.btn_contribute);
        btnSave = (Button)findViewById(R.id.btn_saved);
        sv = (SearchView)findViewById(R.id.sv) ;
        sv.setOnQueryTextListener(this);
        sv.setQueryHint("search...");


        final ShowTipsView showtips = new ShowTipsBuilder(LauncherActivity.this)
                .setTarget(sv).setTitle("Search").setDescription("Search link titles based on keywords").setDelay(500).setBackgroundAlpha(180).setCloseButtonColor(Color.parseColor("#E91E63")).build();
        showtips.show(LauncherActivity.this);

        showtips.setCallback(new ShowTipsViewInterface() {
            @Override
            public void gotItClicked() {
                ShowTipsView showtips2 = new ShowTipsBuilder(LauncherActivity.this).setTarget(btnSave).setTitle("Saved Links").setDescription("View your saved links").setCloseButtonColor(Color.parseColor("#E91E63")).setBackgroundAlpha(180).build();
                showtips2.show(LauncherActivity.this);

                showtips2.setCallback(new ShowTipsViewInterface() {
                    @Override
                    public void gotItClicked() {
                        ShowTipsView showtips3 = new ShowTipsBuilder(LauncherActivity.this).setTarget(btnContribute).setTitle("Contribute").setDescription("Submit a quality link to the database").setCloseButtonColor(Color.parseColor("#E91E63")).setBackgroundAlpha(180).build();
                        showtips3.show(LauncherActivity.this);
                    }
                });
            }
        });


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        listView = (ListView) findViewById(R.id.list_view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sv.setIconified(true);
                supportInvalidateOptionsMenu();
                fetchFireBaseData();
            }
        });

        temp = new ArrayList<>();
        dataArrayList = new ArrayList<>();

        fetchFireBaseData();
    }

    private void fetchFireBaseData() {

        dataArrayList.clear();
        temp.clear();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(), ref = myRef.child("data");

        final ListAdapter listAdapter = new ListAdapter(LauncherActivity.this, dataArrayList);

        listView.setAdapter(listAdapter);

        ref.orderByChild("timeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String title = (String) ds.child("title").getValue();
                    String description = (String) ds.child("description").getValue();
                    String contributor = (String) ds.child("contributor").getValue();
                    String link = (String) ds.child("link").getValue();
                    long timeStamp = (long) ds.child("timeStamp").getValue();
                    String key = ds.getKey();

                    dataArrayList.add(new Data(description, contributor, link, timeStamp, title, key));
                }
                Collections.reverse(dataArrayList);
                listView.setAdapter(new ListAdapter(LauncherActivity.this, dataArrayList));

                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FabToast.makeText(getApplicationContext(), "oops, something went wrong", FabToast.LENGTH_LONG, FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
            }
        });
    }

    public void click(View v) {

        int id = v.getId();

        if (id == R.id.btn_contribute) {
            startActivity(new Intent(LauncherActivity.this, ContributeActivity.class));
            finish();
            Bungee.diagonal(LauncherActivity.this);
        } else {
            startActivity(new Intent(LauncherActivity.this, SavedLinksActivity.class));
            Bungee.diagonal(LauncherActivity.this);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        temp.clear();
        for (int i = 0; i < dataArrayList.size(); i++) {
            if (dataArrayList.get(i).getTitle().toLowerCase().contains(query.toLowerCase())) {
                temp.add(dataArrayList.get(i));
            }
        }
        listView.setAdapter(new ListAdapter(LauncherActivity.this, temp));
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       if (id == R.id.about) {

           final Dialog dialog = new Dialog(LauncherActivity.this);
           dialog.setContentView(R.layout.about_dialog);
           Button close = dialog.findViewById(R.id.btn_close_dialog) ;
           close.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   dialog.dismiss();
               }
           });
           dialog.show();

           return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTips(){

    }
}
