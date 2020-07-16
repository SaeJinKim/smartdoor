package com.project.smartdoor.ui.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class LoginRequest extends StringRequest {
    //서버 URL설정
    final static private String URL = "http://ezpro.duckdns.org/smartdoor/login.php";
    private Map<String, String> map;

    public LoginRequest(String userId, String userPassword, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map = new HashMap<>();
        map.put("USER_ID",userId);
        map.put("USER_PASSWORD",userPassword);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}