package com.example.registration;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CustomRow extends ArrayAdapter<Complaint> {
    int likes;
    List<String> likedby;
    public CustomRow (Context context, ArrayList<Complaint> arrayList){
        super(context,0,arrayList);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View  currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }
        TextView row_title=currentItemView.findViewById(R.id.row_title);
        TextView row_username=currentItemView.findViewById(R.id.row_name);
        TextView row_regno=currentItemView.findViewById(R.id.row_regno);
        TextView row_status=currentItemView.findViewById(R.id.row_status);



        // get the position of the view from the ArrayAdapter
        Complaint currentNumberPosition = getItem(position);


        assert currentNumberPosition != null;

        row_title.setText(currentNumberPosition.getTitle());
        row_username.setText(currentNumberPosition.getName());
        row_regno.setText(currentNumberPosition.getRegno());
        String status_value=currentNumberPosition.getStatus();
        String final_status=status_value.replaceAll(" ","\n");
        row_status.setText(final_status);


        ImageButton transact_button=currentItemView.findViewById(R.id.row_chat);

        transact_button.setFocusable(false);
        transact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "implementation pending", Toast.LENGTH_SHORT).show();
            }
        });

        return currentItemView;
    }

    @Override
    public void notifyDataSetChanged() {
        
        super.notifyDataSetChanged();
    }
}
