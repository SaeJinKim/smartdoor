package com.project.smartdoor.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.smartdoor.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpenInformation extends AppCompatActivity {
    ListView listView;
    ArrayList<Data> dataList;
    ArrayAdapter<String> adapter;
    Data data;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_information);

        Intent intent = getIntent();
        dataList = (ArrayList<Data>) intent.getSerializableExtra("list");
        for(int i =0; i<dataList.size();i++){
            stringArrayList.add(""+dataList.get(i).getLog_id().toString()+" : "+dataList.get(i).getLog_time().toString());
        }

        listView = findViewById(R.id.listView);



        adapter = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_list_item_1,stringArrayList);
        listView.setAdapter(adapter);

    }
}