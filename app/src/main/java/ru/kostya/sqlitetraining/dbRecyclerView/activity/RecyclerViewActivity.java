package ru.kostya.sqlitetraining.dbRecyclerView.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.kostya.sqlitetraining.R;
import ru.kostya.sqlitetraining.dbRecyclerView.HelperRecyclerView;
import ru.kostya.sqlitetraining.dbRecyclerView.adapter.RecAdapter;
import ru.kostya.sqlitetraining.dbRecyclerView.model.Student;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecAdapter adapter;
    private List<Student> studentList;
    private Button addBtn;
    private HelperRecyclerView helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        helper = new HelperRecyclerView(this);
        studentList = new ArrayList<>();
        adapter = new RecAdapter(this,studentList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addDataActivity = new Intent(RecyclerViewActivity.this,AddDataActivity.class);
                startActivity(addDataActivity);
            }
        });

        getData();
    }

    private void getData() {
        Cursor currentCursor = helper.getData();

        if (currentCursor.getCount() == 0){
            Toast.makeText(this, "Таблица пустая", Toast.LENGTH_SHORT).show();
            return;
        }
        //Пока естсь данные в курсоре
        while (currentCursor.moveToNext()){
            //Под индексом 0 идет столбец Id,нам он не нужен
            String name = currentCursor.getString(1);
            String surname = currentCursor.getString(2);
            String mark = currentCursor.getString(3);

            Student student = new Student();
            student.setName(name);
            student.setSurname(surname);
            student.setMark(mark);

            studentList.add(student);
        }
        adapter.notifyDataSetChanged();
    }
}