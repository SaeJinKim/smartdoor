package com.project.smartdoor.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.project.smartdoor.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        final Button idDupliButton = (Button)findViewById(R.id.idDupli);
        final Button registButton = (Button)findViewById(R.id.registButton);
        final EditText et_id=findViewById(R.id.id);
        final EditText et_pw=findViewById(R.id.password);
        final EditText et_cf_pw=findViewById(R.id.passwordConfirm);
        final EditText et_name=findViewById(R.id.name);
        final EditText et_email=findViewById(R.id.email);
        final EditText et_phone=findViewById(R.id.phone);
        final TextView pw_confirm = findViewById(R.id.password_correct);
        final Boolean[] id_dupli = {false};
        final Boolean[] samePassword ={false};

        // 비밀번호와 비밀번호 확인을 비교하는 문장 필요
        //--------------------------!-----------------------
        et_cf_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_pw.getText().toString().equals(et_cf_pw.getText().toString())){
                    pw_confirm.setText("비밀번호가 일치합니다.");
                    samePassword[0]=true;
                }else{
                    pw_confirm.setText("비밀번호가 일치하지 않습니다.");
                    samePassword[0]=false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        idDupliButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 아이디 중복 확인 sql실행 (php 파일 연결) ------------
                String USER_ID = et_id.getText().toString();
                Response.Listener<String> responseListener = null;
                if(USER_ID.equals("")){
                    Toast.makeText(getApplicationContext(),"아이디를 입력하세요.",Toast.LENGTH_SHORT).show();
                }else {
                    responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //JSON 형식으로 받아옴
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                //커넥트, 쿼리문 실행 성공
                                if (success) {
                                    Toast.makeText(getApplicationContext(), "아이디가 중복됩니다.", Toast.LENGTH_SHORT).show();
                                }
                                //커넥트, 쿼리문 실행 실패
                                else {
                                    Toast.makeText(getApplicationContext(), "사용할 수 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                                    builder.setTitle("아이디 중복 확인");
                                    builder.setMessage("이 아이디를 사용하시겠습니까?");
                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @SuppressLint("ResourceAsColor")
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            et_id.setFocusable(false);
                                            et_pw.requestFocus();
                                            idDupliButton.setBackgroundColor(R.color.gray);
                                            idDupliButton.setClickable(false);
                                            id_dupli[0] =true;
                                        }
                                    });
                                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            et_id.setText("");
                                            et_id.requestFocus();
                                        }
                                    });

                                    builder.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                }

                //서버로 volley를 이용해서 요청함
                IdDupliRequest idDupliRequest = new IdDupliRequest(USER_ID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegistActivity.this);
                queue.add(idDupliRequest);
            }
        });


        registButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 회원 INSERT 쿼리 처리 (php 파일 연결) ------------
                String USER_ID = et_id.getText().toString();
                String USER_PASSWORD = et_pw.getText().toString();
                String USER_CONFIRM_PASSWORD = et_cf_pw.getText().toString();
                String USER_NAME = et_name.getText().toString();
                String USER_EMAIL = et_email.getText().toString();
                String USER_PHONE = et_phone.getText().toString();
                Response.Listener<String> responseListener = responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON 형식으로 받아옴
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            //커넥트, 쿼리문 실행 성공
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원등록성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            //커넥트, 쿼리문 실행 실패
                            else {
                                Toast.makeText(getApplicationContext(), "회원등록실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                if(USER_ID.equals("")) {
                        Toast.makeText(getApplicationContext(),"아이디를 입력하세요.",Toast.LENGTH_SHORT).show();
                        et_id.requestFocus();
                        return;
                }else if(!id_dupli[0]){
                        Toast.makeText(getApplicationContext(),"아이디 중복을 확인하세요.",Toast.LENGTH_SHORT).show();
                        et_id.requestFocus();
                    return;
                }else if(USER_PASSWORD.equals("")|USER_CONFIRM_PASSWORD.equals("")){
                        Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요.",Toast.LENGTH_SHORT).show();
                        et_pw.requestFocus();
                    return;
                }else if(!samePassword[0]){
                         Toast.makeText(getApplicationContext(),"비밀번호를 확인하세요.",Toast.LENGTH_SHORT).show();
                         et_pw.requestFocus();
                    return;
                }else if(USER_NAME.equals("")){
                    Toast.makeText(getApplicationContext(),"이름을 입력하세요",Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                }else if(USER_EMAIL.equals("")){
                    Toast.makeText(getApplicationContext(),"이메일을 입력하세요",Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();
                    return;
                }else if(USER_PHONE.equals("")){
                    Toast.makeText(getApplicationContext(),"전화번호를 입력하세요",Toast.LENGTH_SHORT).show();
                    et_phone.requestFocus();
                    return;
                }else{
                    //서버로 volley를 이용해서 요청함
                    RegistRequest registRequest = new RegistRequest(USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL, USER_PHONE, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegistActivity.this);
                    queue.add(registRequest);
                }


            }
        });
    }
}