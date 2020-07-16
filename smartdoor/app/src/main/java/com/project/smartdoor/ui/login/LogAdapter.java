package com.project.smartdoor.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.smartdoor.R;

import java.util.ArrayList;

public class LogAdapter extends BaseAdapter {
    Context mContext = null;
    int layout =0;
    ArrayList<String> data =null;
    LayoutInflater inflater = null;

    public LogAdapter(Context c, int l, ArrayList<String> d){
        this.mContext=c;
        this.layout=l;
        this.data=d;
        this.inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null) {
            convertView = inflater.inflate(this.layout, viewGroup, false);
        }

        TextView log_id = (TextView) convertView.findViewById(R.id.log_id);

        log_id.setText(data.get(position));




        return convertView;
    }
}
