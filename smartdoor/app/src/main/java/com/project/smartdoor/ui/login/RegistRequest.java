package com.project.smartdoor.ui.login;

import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class RegistRequest extends StringRequest {
    //서버 URL설정
    final static private String URL = "http://ezpro.duckdns.org/smartdoor/regist.php";
    private Map<String, String> map;

    public RegistRequest(String userId, String userPassword, String userName, String userEmail, String userPhone, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map = new HashMap<>();
        map.put("USER_ID",userId);
        map.put("USER_PASSWORD",userPassword);
        map.put("USER_NAME",userName);
        map.put("USER_EMAIL",userEmail);
        map.put("USER_PHONE",userPhone);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}