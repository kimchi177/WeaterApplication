package com.example.thu.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by THU on 27/02/2018.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> arrayList;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_listview,null);

        ThoiTiet thoiTiet=arrayList.get(position);
        TextView Txt_Ngay= (TextView) view.findViewById(R.id.Txt_Ngay);
        TextView Txt_TrangThai= (TextView) view.findViewById(R.id.Txt_TrangThai);
        TextView Txt_MaxText= (TextView) view.findViewById(R.id.Txt_MaxText);
        TextView Txt_MinText= (TextView) view.findViewById(R.id.Txt_MinText);
        ImageView Img_trangThai= (ImageView) view.findViewById(R.id.Img_trangThai);

        Txt_Ngay.setText(thoiTiet.Day);
        Txt_TrangThai.setText(thoiTiet.Status);
        Txt_MaxText.setText(thoiTiet.Maxtemp+"C");
        Txt_MinText.setText(thoiTiet.MinTemp+"C");
        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoiTiet.Image+".png").into(Img_trangThai);
        return view;
    }
}
