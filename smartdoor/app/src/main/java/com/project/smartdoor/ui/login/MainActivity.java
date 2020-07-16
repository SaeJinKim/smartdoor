package com.project.smartdoor.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.project.smartdoor.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView savePicture = findViewById(R.id.savePicture);
        final ImageView openDoor = findViewById(R.id.openDoor);
        final ImageView openInformation = findViewById(R.id.openInformation);
        final ImageView notification = findViewById(R.id.notification);
        final TextView name_hello = findViewById(R.id.name_hello);
        final Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        name_hello.setText(userID+"님 환영합니다.");


        savePicture.setOnClickListener(new View.OnClickListener() {
            // 사진 등록 페이지 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PictureRegist.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        openDoor.setOnClickListener(new View.OnClickListener() {
            //문 개폐 가능 페이지 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Opendoor.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        openInformation.setOnClickListener(new View.OnClickListener() {
            //문 개폐 정보 - (누가 들어오고 나왔는지) - 페이지로 이동
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON 형식으로 받아옴
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("response");
                            ArrayList<Data> list = new ArrayList<Data>();

                            //커넥트, 쿼리문 실행 성공
                            for(int i = 0 ; i < array.length(); i++) {
                                Data data = new Data(array.getJSONObject(i).getString("ID"),array.getJSONObject(i).getString("TIME"));
                                list.add(data);
                            }

                            Intent intent = new Intent(MainActivity.this,OpenInformation.class);
                            intent.putExtra("list",list);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 volley를 이용해서 요청함
                LogRequest logRequest = new LogRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(logRequest);


            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            //도움말 기능, 공지기능 페이지
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON 형식으로 받아옴
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("response");
                            ArrayList<Noti> list = new ArrayList<Noti>();

                            //커넥트, 쿼리문 실행 성공
                            for(int i = 0 ; i < array.length(); i++) {
                                Noti noti = new Noti();
                                noti.setNoti_id(array.getJSONObject(i).getInt("ID"));
                                noti.setNoti_title(array.getJSONObject(i).getString("TITLE"));
                                noti.setNoti_content(array.getJSONObject(i).getString("CONTENT"));
                                noti.setNoti_admin(array.getJSONObject(i).getString("ADMIN"));
                                noti.setNoti_time(array.getJSONObject(i).getString("TIME"));
                                list.add(noti);
                            }

                            Intent intent = new Intent(MainActivity.this,Notification.class);
                            intent.putExtra("list",list);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 volley를 이용해서 요청함
                NotiRequest notiRequest = new NotiRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(notiRequest);

            }
        });

    }
}