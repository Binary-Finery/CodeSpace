package spencerstudios.com.codespace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        final EditText etTitle = (EditText) findViewById(R.id.et_title);
        final EditText etDescription = (EditText) findViewById(R.id.et_description);
        final EditText etContribute = (EditText) findViewById(R.id.et_contribute);
        final EditText etLink = (EditText) findViewById(R.id.et_link) ;

        Button btnSub = (Button) findViewById(R.id.btn_submit);
        Button btnPrev = (Button) findViewById(R.id.btn_records);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Data d = new Data(etTitle.getText().toString(),
                        etDescription.getText().toString(),
                        etContribute.getText().toString(),
                        etLink.getText().toString(),
                        System.currentTimeMillis());

                myRef.child("data").push().setValue(d);

                etContribute.setText("");
                etTitle.setText("");
                etDescription.setText("");
                etLink.setText("");
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReviewActivity.class));
            }
        });
    }
}
