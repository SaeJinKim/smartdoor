package com.project.smartdoor.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.project.smartdoor.R;
import com.project.smartdoor.ui.login.LoginViewModel;
import com.project.smartdoor.ui.login.LoginViewModelFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameEditText = findViewById(R.id.id);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final TextView find = findViewById(R.id.find);
        final TextView regist = findViewById(R.id.regist);


        find.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원 찾기 페이지로 이동 ------------
                Intent intent = new Intent(LoginActivity.this, FindActivity.class);
                startActivity(intent);
            }
        });

        regist.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원 등록 페이지로 이동------------
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });


        loginButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 회원 SELECT 쿼리 실행 후 Main으로 이동 -----!-------
                String USER_ID = usernameEditText.getText().toString();
                String USER_PASSWORD = passwordEditText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON 형식으로 받아옴
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            //커넥트, 쿼리문 실행 성공
                            if(success){
                                String userID =jsonObject.getString("USER_ID");

                                Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("userID",userID);
                                startActivity(intent);

                            }
                            //커넥트, 쿼리문 실행 실패
                            else {
                                Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 volley를 이용해서 요청함
                LoginRequest loginRequest = new LoginRequest(USER_ID,USER_PASSWORD,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });


    }
}