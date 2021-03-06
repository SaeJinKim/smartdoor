package com.project.smartdoor.ui.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

class NotiRequest extends StringRequest {
    //서버 URL설정
    final static private String URL = "http://ezpro.duckdns.org/smartdoor/selectAllNoti.php";
    private Map<String, String> map;

    public NotiRequest(Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}