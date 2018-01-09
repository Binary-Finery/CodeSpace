package spencerstudios.com.codespace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import spencerstudios.com.bungeelib.Bungee;
import spencerstudios.com.fab_toast.FabToast;

public class ContributeActivity extends AppCompatActivity {

    private AutoCompleteTextView etContribute;
    private ArrayAdapter autoAdapter;
    private ArrayList<String> suggestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        suggestionList = new ArrayList<>();
        suggestionList.add("Dean");
        suggestionList.add("Binary Finery");
        suggestionList.add("Dean Spencer");

        final AutoCompleteTextView etContribute = (AutoCompleteTextView) findViewById(R.id.et_contribute);

        autoAdapter = new ArrayAdapter<>(this, R.layout.custom_text_view, suggestionList);
        etContribute.setThreshold(1);
        etContribute.setAdapter(autoAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        final EditText etTitle = (EditText) findViewById(R.id.et_title), etDescription = (EditText) findViewById(R.id.et_description), etLink = (EditText) findViewById(R.id.et_link);

        etTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60)});
        etDescription.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});
        etContribute.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        etLink.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});

        Button btnSub = (Button) findViewById(R.id.btn_submit);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString(), desc = etDescription.getText().toString(), cont = etContribute.getText().toString(), link = etLink.getText().toString();

                if (title.length() > 0 && desc.length() > 0 && cont.length() > 0 && link.length() > 0) {

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
                            }
                        }
                    });
                } else {
                    FabToast.makeText(getApplicationContext(), "one or more  fields is missing required information", FabToast.LENGTH_LONG, FabToast.WARNING, FabToast.POSITION_DEFAULT).show();
                }
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(ContributeActivity.this, LauncherActivity.class));
        finish();
        Bungee.zoom(ContributeActivity.this);
    }
}
