package com.project.smartdoor.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.project.smartdoor.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Opendoor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opendoor);

        final ImageView lock_unlock = findViewById(R.id.lock_unlock);
        final TextView open_close_button = findViewById(R.id.open_close_Button);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        View.OnClickListener lockListener = new View.OnClickListener() {
            // 이미지, 텍스트 클릭시 문 계폐 신호 송출, 및 이미지,텍스트 변환 -------!--------
            @Override
            public void onClick(View view) {
                if(open_close_button.getText().toString().equals("개방")){
                    // 폐쇄 신호 ----!-----
                    Response.Listener<String> responseListener = responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //JSON 형식으로 받아옴
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                //커넥트, 쿼리문 실행 성공
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    //서버로 volley를 이용해서 요청함
                    SignalRequest signalRequest = new SignalRequest(userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Opendoor.this);
                    queue.add(signalRequest);

                    lock_unlock.setImageResource(R.drawable.open);
                    open_close_button.setText(R.string.close);
                    return;
                }
                if(open_close_button.getText().toString().equals("폐쇄")){
                    // 개방 신호 ----!----
                    Response.Listener<String> responseListener = responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //JSON 형식으로 받아옴
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                //커넥트, 쿼리문 실행 성공
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    //서버로 volley를 이용해서 요청함
                    SignalRequest signalRequest = new SignalRequest(userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Opendoor.this);
                    queue.add(signalRequest);


                    lock_unlock.setImageResource(R.drawable.close);
                    open_close_button.setText(R.string.open);
                    return;
                }
            }
        };

        lock_unlock.setOnClickListener(lockListener);
        open_close_button.setOnClickListener(lockListener);
    }
}