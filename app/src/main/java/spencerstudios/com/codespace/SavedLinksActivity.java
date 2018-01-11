package spencerstudios.com.codespace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;

public class SavedLinksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_links);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ListView listView = (ListView)findViewById(R.id.list_view_saved);
        TextView empty = (TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(empty);
        ArrayList<SavedLinksData> links = PrefUtils.links(SavedLinksActivity.this);
        SavedAdapter adapter = new SavedAdapter(this, links);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed(){
        super.onBackPressed();
        Bungee.zoom(SavedLinksActivity.this);
    }
}
