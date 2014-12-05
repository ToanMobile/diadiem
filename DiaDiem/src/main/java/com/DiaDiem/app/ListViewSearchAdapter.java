package com.DiaDiem.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Toan_Kul on 4/20/2014.
 * Lay du lieu cho activity Search
 */
public class ListViewSearchAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    //ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewSearchAdapter(Context context,
                                 ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
       // imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView name;
        TextView thongtin;
        TextView diachi;
        TextView toadox;
        TextView toadoy;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item_search, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        name = (TextView) itemView.findViewById(R.id.text_ten);
        thongtin = (TextView) itemView.findViewById(R.id.text_thongtin);
        diachi = (TextView) itemView.findViewById(R.id.text_diachi);
        toadox = (TextView) itemView.findViewById(R.id.ToaDox);
        toadoy = (TextView) itemView.findViewById(R.id.ToaDoy);

        name.setText(resultp.get(Activity_search.TAG_TEN));
        thongtin.setText(resultp.get(Activity_search.TAG_THONGTIN));
        diachi.setText(resultp.get(Activity_search.TAG_DIACHI));
        toadox.setText(resultp.get(Activity_search.TAG_TOADOX));
        toadoy.setText(resultp.get(Activity_search.TAG_TOADOY));
        return itemView;
    }
}
