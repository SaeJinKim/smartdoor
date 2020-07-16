package com.project.smartdoor.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.smartdoor.R;

import java.util.ArrayList;

public class NotiAdapter extends BaseAdapter {
    private  ArrayList<Noti> notiList = new ArrayList<Noti>();

    @Override
    public int getCount() {
        return notiList.size();
    }

    @Override
    public Object getItem(int i) {
        return notiList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_notification,viewGroup,false);
        }

        TextView noti_title = view.findViewById(R.id.noti_title);
        TextView noti_desc = view.findViewById(R.id.noti_description);
        TextView noti_admin = view.findViewById(R.id.admin_name);
        TextView post_name = view.findViewById(R.id.post_date);

        Noti noti = (Noti) getItem(i);

        noti_title.setText(noti.getNoti_title());
        noti_desc.setText(noti.getNoti_content());
        noti_admin.setText(noti.getNoti_admin());
        post_name.setText(noti.getNoti_time());


        return view;
    }

    public void addItem(String title, String description, String admin, String postdate) {

        Noti noti = new Noti();

        noti.setNoti_title(title);
        noti.setNoti_content(description);
        noti.setNoti_admin(admin);
        noti.setNoti_time(postdate);

        /* 데이터그릇 mItem에 담음 */
        notiList.add(noti);
        }
    }




