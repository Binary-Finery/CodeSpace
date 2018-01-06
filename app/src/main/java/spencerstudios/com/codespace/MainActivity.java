package spencerstudios.com.codespace;

import android.content.Intent;
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

import spencerstudios.com.bungeelib.Bungee;

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

        etTitle.setFilters(new InputFilter[] {new InputFilter.LengthFilter(80)});
        etDescription.setFilters(new InputFilter[] {new InputFilter.LengthFilter(150)});
        etContribute.setFilters(new InputFilter[] {new InputFilter.LengthFilter(25)});
        etLink.setFilters(new InputFilter[] {new InputFilter.LengthFilter(120)});




        Button btnSub = (Button) findViewById(R.id.btn_submit);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Data d = new Data(
                        etDescription.getText().toString(),
                        etContribute.getText().toString(),
                        etLink.getText().toString(),
                        System.currentTimeMillis(),
                        etTitle.getText().toString());

                Toast.makeText(getApplicationContext(), etTitle.getText().toString(), Toast.LENGTH_LONG).show();

                myRef.child("data").push().setValue(d).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                            etContribute.setText("");
                            etTitle.setText("");
                            etDescription.setText("");
                            etLink.setText("");
                        }else{
                            Toast.makeText(getApplicationContext(), "something went wrong there!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
