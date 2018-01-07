package spencerstudios.com.codespace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContributeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        final EditText etTitle = (EditText) findViewById(R.id.et_title),
                etDescription = (EditText) findViewById(R.id.et_description),
                etContribute = (EditText) findViewById(R.id.et_contribute),
                etLink = (EditText) findViewById(R.id.et_link);

        etTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60)});
        etDescription.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});
        etContribute.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        etLink.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});

        Button btnSub = (Button) findViewById(R.id.btn_submit);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString(),
                        desc = etDescription.getText().toString(),
                        cont = etContribute.getText().toString(),
                        link = etLink.getText().toString();

                if (title.length() > 0 && desc.length() > 0 && cont.length() > 0 && link.length() > 0) {

                    Data data = new Data(desc, cont, link, System.currentTimeMillis(), title);

                    myRef.child("data").push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                etContribute.setText("");
                                etTitle.setText("");
                                etDescription.setText("");
                                etLink.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "something went wrong there!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "One or fields is incomplete", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
