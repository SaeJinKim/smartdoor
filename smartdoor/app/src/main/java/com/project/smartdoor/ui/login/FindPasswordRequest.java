package com.project.smartdoor.ui.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class FindPasswordRequest extends StringRequest {
    //서버 URL설정
    final static private String URL = "http://ezpro.duckdns.org/smartdoor/find_password.php";
    private Map<String, String> map;

    public FindPasswordRequest(String userId, String userName, String userEmail, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map = new HashMap<>();
        map.put("USER_ID",userId);
        map.put("USER_NAME",userName);
        map.put("USER_EMAIL",userEmail);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}