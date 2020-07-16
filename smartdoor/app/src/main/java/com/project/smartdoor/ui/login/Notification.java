package com.project.smartdoor.ui.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.smartdoor.R;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {
        ListView listView;
        ArrayList<Noti> notiList;
        ArrayAdapter<String> adapter;
        Noti noti;
        ArrayList<String> stringArrayList = new ArrayList<String>();
        NotiAdapter nAdapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Intent intent = getIntent();
        notiList = (ArrayList<Noti>) intent.getSerializableExtra("list");

        nAdapter = new NotiAdapter();
        for(int i =0; i<notiList.size();i++) {
                nAdapter.addItem(notiList.get(i).getNoti_title(),
                        notiList.get(i).getNoti_content(),
                        notiList.get(i).getNoti_admin(),
                        notiList.get(i).getNoti_time());
        }

        listView = findViewById(R.id.noti_listview);
        listView.setAdapter(nAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView admin = view.findViewById(R.id.admin_name);
                        TextView desc = view.findViewById(R.id.noti_description);
                        TextView title = view.findViewById(R.id.noti_title);

                }
        });

        }
}