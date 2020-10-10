package ru.kostya.sqlitetraining.dbRecyclerView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import ru.kostya.sqlitetraining.R;
import ru.kostya.sqlitetraining.dbRecyclerView.model.Student;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

    private List<Student> studentList;
    private Context mContext;

    public RecAdapter(Context mContext,List<Student> studentList) {
        this.mContext = mContext;
        this.studentList = studentList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,surname,mark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            surname = itemView.findViewById(R.id.surname);
            mark = itemView.findViewById(R.id.mark);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student currentStudent = studentList.get(position);

        holder.name.setText(currentStudent.getName());
        holder.surname.setText(currentStudent.getSurname());
        holder.mark.setText(currentStudent.getMark());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

}
