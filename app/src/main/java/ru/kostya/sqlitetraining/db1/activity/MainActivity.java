package ru.kostya.sqlitetraining.db1.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.kostya.sqlitetraining.R;
import ru.kostya.sqlitetraining.db1.DatabaseHelper;
import ru.kostya.sqlitetraining.dbRecyclerView.activity.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    private EditText edName,edSurname,edMark,edId;
    private Button addData,viewData,updateData,deleteData,twoActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DatabaseHelper(this);

        addData = findViewById(R.id.addDataBtn);
        viewData = findViewById(R.id.viewDataBtn);
        updateData = findViewById(R.id.updateDataBtn);
        deleteData = findViewById(R.id.deleteDataBtn);
        twoActivityBtn = findViewById(R.id.twoActivity);

        edName = findViewById(R.id.edName);
        edSurname = findViewById(R.id.edSurname);
        edMark = findViewById(R.id.edMark);
        edId = findViewById(R.id.edId);

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

        //При отображении данных
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllData();
            }
        });

        //При обновлении данных
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        //При удалении данных
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });

        //При переходе на 2 активность с RecyclerView
        twoActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recyclerActivity = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(recyclerActivity);
            }
        });

    }



    private void addData() {
        //Add data
        //GET DATA of editTexts
        String name = edName.getText().toString();
        String surname = edSurname.getText().toString();
        int mark = Integer.parseInt(edMark.getText().toString());

        boolean isInsertData = helper.insertData(name,surname,mark);

        if (isInsertData){
            //Если данные вставились,то
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
        } else {
            //Иначе
            Toast.makeText(this, "Data don't inserted", Toast.LENGTH_SHORT).show();
        }

    }

    private void getAllData() {
        Cursor cursor = helper.getAllData();

        //if  в таблице нету дыннх выходим из метода,иначе...
        if (cursor.getCount() == 0){
            showMessage("Ошибка!","Данные в таблице отсутствуют!");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        //Пока есть данные в курсоре
        while (cursor.moveToNext()){
            //Добавляем id  по индексу,начиная с 0
            buffer.append("Id: " + cursor.getString(0) + "\n");
            buffer.append("Name: " + cursor.getString(1) + "\n");
            buffer.append("Surname: " + cursor.getString(2) + "\n");
            buffer.append("Mark: " + cursor.getString(3) + "\n\n");
        }
        //После чего в AlertDialog display data
        showMessage("Данные", buffer.toString());
    }

    private void updateData(){

        String id = edId.getText().toString();
        String name = edName.getText().toString();
        String surname = edSurname.getText().toString();
        int mark = Integer.parseInt(edMark.getText().toString());

        boolean isUpdated = helper.updateData(id,name,surname,mark);

        if (isUpdated){
            Toast.makeText(this, "Data was updated", Toast.LENGTH_SHORT).show();
        }

    }

    private void deleteData(){
        String id = edId.getText().toString();

        int deletedRows = helper.deleteData(id);
        Toast.makeText(this, "Было удалено: " + deletedRows + " записей", Toast.LENGTH_SHORT).show();
    }

    private void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setTitle(title)
                .setMessage(message)
                .show();

    }

}