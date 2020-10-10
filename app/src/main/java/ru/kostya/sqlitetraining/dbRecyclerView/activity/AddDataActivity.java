package ru.kostya.sqlitetraining.dbRecyclerView.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.kostya.sqlitetraining.R;
import ru.kostya.sqlitetraining.dbRecyclerView.HelperRecyclerView;

public class AddDataActivity extends AppCompatActivity {

    private EditText edName,edSurname,edMark;
    private Button addData;
    private HelperRecyclerView helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        addData = findViewById(R.id.addDataRecyclerView);

        edName = findViewById(R.id.edName);
        edSurname = findViewById(R.id.edSurname);
        edMark = findViewById(R.id.edMark);

        helper = new HelperRecyclerView(this);

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edName.getText().toString();
                String surname = edSurname.getText().toString();
                String mark = edMark.getText().toString();

               boolean isInsert =  helper.addData(name,surname,mark);

               if (isInsert){
                   Toast.makeText(AddDataActivity.this, "data was inserted", Toast.LENGTH_SHORT).show();
               } else {
                   Toast.makeText(AddDataActivity.this, "Error inserted data", Toast.LENGTH_SHORT).show();
               }
                Intent recyclerViewActivity = new Intent(AddDataActivity.this,RecyclerViewActivity.class);
                startActivity(recyclerViewActivity);
            }
        });

    }
}