package spencerstudios.com.codespace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;

public class SavedLinksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_links);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView)findViewById(R.id.list_view_saved);
        ArrayList<SavedLinksData> links = PrefUtils.links(SavedLinksActivity.this);
        SavedAdapter adapter = new SavedAdapter(this, links);
        listView.setAdapter(adapter);
    }

    public void onBackPressed(){
        super.onBackPressed();
        Bungee.zoom(SavedLinksActivity.this);
    }
}
