package com.project.smartdoor.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.project.smartdoor.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        final Button id_find_button = findViewById(R.id.id_find_button);
        final Button password_find_button = findViewById(R.id.password_find_button);
        final EditText et_name_idFind =findViewById(R.id.name_idFind);
        final EditText et_email_idFind=findViewById(R.id.email_idFind);
        final EditText et_name_passwordFind =findViewById(R.id.name_passwordFind);
        final EditText et_email_passwordFind=findViewById(R.id.email_passwordFind);
        final EditText et_id_passwordFind=findViewById(R.id.id_passwordFind);
        final EditText new_password = new EditText(FindActivity.this);
        final EditText new_confirm_password = new EditText(FindActivity.this);

        id_find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SELECT ID FROM USER WHERE USER_NAME = ${이름} and USER_EMAIL = ${이메일} 후 결과에 따라 ID리턴 ------------
                String USER_NAME = et_name_idFind.getText().toString();
                String USER_EMAIL = et_email_idFind.getText().toString();

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
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                                builder.setTitle("아이디 찾기").setMessage("회원님의 ID는 "+userID+"입니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.show();
                            }
                            //커넥트, 쿼리문 실행 실패
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                                builder.setTitle("아이디 찾기").setMessage("일치하는 회원이 없습니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 volley를 이용해서 요청함
                FindIdRequest findIdRequest = new FindIdRequest(USER_NAME,USER_EMAIL,responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindActivity.this);
                queue.add(findIdRequest);
            }
        });

        password_find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SELECT ID FROM USER WHERE USER_NAME = ${이름} and USER_EMAIL = ${이메일} USER_ID = ${ID} 후 결과에 따라 새로운 PASSWORD설정 가능 ------------
                final String USER_ID = et_id_passwordFind.getText().toString();
                String USER_NAME = et_name_passwordFind.getText().toString();
                String USER_EMAIL = et_email_passwordFind.getText().toString();

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON 형식으로 받아옴
                            final JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            //커넥트, 쿼리문 실행 성공
                            if(success){
                                final AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                                final String[] USER_NEW_PASSWORD = new String[1];
                                builder.setTitle("비밀번호 찾기").setMessage("새로운 비밀번호를 설정하십시오.");
                                builder.setView(new_password);

                                // UPDATE USER SET USER_PASSWORD = ${USER_PASSWORD} WHERE USER_ID=${USER_ID} -------------------------------
                                builder.setPositiveButton("비밀번호 변경", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Response.Listener<String> passResponseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    final JSONObject jsonObject2 = new JSONObject(response);
                                                    boolean success2 = jsonObject2.getBoolean("success");
                                                    if(success2){
                                                        Toast.makeText(getApplicationContext(),"비밀번호가 변경되었습니다.",Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        Toast.makeText(getApplicationContext(),"비밀번호가 변경이 취소되었습니다..",Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        };

                                        if(!new_password.getText().toString().equals("")){

                                            USER_NEW_PASSWORD[0] = new_password.getText().toString();
                                            System.out.println();
                                            ReplacePasswordRequest replacePasswordRequest = new ReplacePasswordRequest(USER_ID,USER_NEW_PASSWORD[0],passResponseListener);
                                            RequestQueue queue2 = Volley.newRequestQueue(FindActivity.this);
                                            queue2.add(replacePasswordRequest);

                                        }else{
                                            builder.setMessage("비밀번호를 입력하시오.");
                                        }
                                    }

                                });
                                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.show();
                            }
                            //커넥트, 쿼리문 실행 실패
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                                builder.setTitle("비밀번호 찾기").setMessage("일치하는 회원이 없습니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 volley를 이용해서 요청함
                FindPasswordRequest findPasswordRequest = new FindPasswordRequest(USER_ID, USER_NAME, USER_EMAIL,responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindActivity.this);
                queue.add(findPasswordRequest);
            }
        });


    }
}