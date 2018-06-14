package com.example.thu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    String TenthapPho = "";
//
    ImageView Img_Back;
    TextView Txt_NameCiTy;
    ListView List_Theme;
    //
    CustomAdapter customAdapter;
    ArrayList<ThoiTiet> MangThoiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Addontrols();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("Result", "Du lieu truyen qua :" + city);
        if (city.equals("")) {
            TenthapPho = "SaiGon";
            Get7DayData(TenthapPho);
        } else {
            TenthapPho = city;
            Get7DayData(TenthapPho);
        }
        //
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();//tra lai phia truoc.
            }
        });
    }

    private void Addontrols() {
        Img_Back= (ImageView) findViewById(R.id.Img_Back);
        Txt_NameCiTy= (TextView) findViewById(R.id.Txt_NameCity);
        List_Theme= (ListView) findViewById(R.id.List_Theme);
    MangThoiTiet=new ArrayList<ThoiTiet>();
        customAdapter=new CustomAdapter(Main2Activity.this,MangThoiTiet);
        List_Theme.setAdapter(customAdapter);
    }

    private void Get7DayData(String Data) {
        RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
        String url = "https://api.openweathermap.org/data/2.5/forecast/daily?q=" + Data + "&units=metric&cnt=7&appid=53fbf527d52d4d773e828243b90c1f8e";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//Log.d("Result",response);
               // Log.d("KetQua", "Json: " + response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
               JSONObject jsonObjectCiTy=jsonObject.getJSONObject("city");
                    String name=jsonObjectCiTy.getString("name");
                    Txt_NameCiTy.setText(name);

                    JSONArray jsonArrayList=jsonObject.getJSONArray("list");
                    for(int i=0;i<jsonArrayList.length();i++)
                    {
                        JSONObject jsonObjectList=jsonArrayList.getJSONObject(i);
                        String ngay=jsonObjectList.getString("dt");

                        long l=Long.valueOf(ngay);
                        Date date=new Date(l*1000L);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE yyyy-MM-dd ");
                        String Day=simpleDateFormat.format(date);

                   //nhiet do
                        JSONObject jsonObjectTemp=jsonObjectList.getJSONObject("temp");
                        String max=jsonObjectTemp.getString("max");
                        String min=jsonObjectTemp.getString("min");
                        Double a=Double.valueOf(max);
                        Double b=Double.valueOf(min);
                        String NhietDoMax=String.valueOf(a.intValue());
                        String NhietDoMin=String.valueOf(b.intValue());

                   //
                        JSONArray jsonArrayWeather=jsonObjectList.getJSONArray("weather");
                        JSONObject jsonObjectweather=jsonArrayWeather.getJSONObject(0);//chir cos 1 cawjp ther jsonobject thooi.
                        String status=jsonObjectweather.getString("description");
                        String icon=jsonObjectweather.getString("icon");

                        MangThoiTiet.add(new ThoiTiet(Day,status,icon,NhietDoMax,NhietDoMin));
                        customAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
