package com.project.smartdoor.ui.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class SignalRequest extends StringRequest {
    //서버 URL설정
    final static private String URL = "http://ezpro.duckdns.org/smartdoor/signal.php";
    private Map<String, String> map;

    public SignalRequest(String userId, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map = new HashMap<>();
        map.put("USER_ID",userId);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}