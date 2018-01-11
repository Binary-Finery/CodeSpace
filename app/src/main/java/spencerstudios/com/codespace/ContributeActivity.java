package spencerstudios.com.codespace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;
import spencerstudios.com.fab_toast.FabToast;

public class ContributeActivity extends AppCompatActivity {

    private AutoCompleteTextView etContribute;
    private ArrayAdapter autoAdapter;
    private ArrayList<String> autoList;
    private SharedPreferences sp;

    private static final String AUTO = "auto suggest";
    private static final String AUTO_KEY = "auto key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sp = getSharedPreferences(AUTO, MODE_PRIVATE);

        autoList = getAuto();

        final AutoCompleteTextView etContribute = (AutoCompleteTextView) findViewById(R.id.et_contribute);

        autoAdapter = new ArrayAdapter<>(this, R.layout.custom_text_view, autoList);
        etContribute.setThreshold(1);
        etContribute.setAdapter(autoAdapter);

        etContribute.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                String str = etContribute.getText().toString();
                if (action == EditorInfo.IME_ACTION_NEXT && str.length() > 0) {
                    if (!autoList.contains(str)) addAuto(str);
                }
                return false;
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        final EditText etTitle = (EditText) findViewById(R.id.et_title), etDescription = (EditText) findViewById(R.id.et_description), etLink = (EditText) findViewById(R.id.et_link);

        etTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60)});
        etDescription.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});
        etContribute.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        etLink.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});

        final Button btnSub = (Button) findViewById(R.id.btn_submit);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString(), desc = etDescription.getText().toString(), cont = etContribute.getText().toString(), link = etLink.getText().toString();

                if (title.length() > 0 && desc.length() > 0 && cont.length() > 0 && link.length() > 0) {

                    btnSub.setClickable(false);
                    Data data = new Data(desc, cont, link, System.currentTimeMillis(), title);

                    myRef.child("data").push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                FabToast.makeText(getApplicationContext(), "successfully published, thank you", FabToast.LENGTH_LONG, FabToast.SUCCESS, FabToast.POSITION_DEFAULT).show();
                                startActivity(new Intent(ContributeActivity.this, LauncherActivity.class));
                                finish();
                                Bungee.zoom(ContributeActivity.this);
                            } else {
                                FabToast.makeText(getApplicationContext(), "oops, something went wrong", FabToast.LENGTH_LONG, FabToast.ERROR, FabToast.POSITION_DEFAULT).show();
                                btnSub.setClickable(true);
                            }
                        }
                    });
                } else {
                    FabToast.makeText(getApplicationContext(), "one or more fields is missing required information", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                    btnSub.setClickable(true);
                }
            }
        });
    }

    private ArrayList<String> getAuto() {
        ArrayList<String> l = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(sp.getString(AUTO_KEY, "[]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                l.add(jsonArray.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l;
    }

    private void addAuto(String item) {
        ArrayList<String> modList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(sp.getString(AUTO_KEY, "[]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                modList.add(jsonArray.get(i).toString());
            }
            modList.add(item);
            JSONArray ja = new JSONArray(modList);
            modList.clear();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(AUTO_KEY, ja.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            returnHome();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        returnHome();
    }

    private void returnHome() {
        startActivity(new Intent(ContributeActivity.this, LauncherActivity.class));
        finish();
        Bungee.zoom(ContributeActivity.this);
    }
}
