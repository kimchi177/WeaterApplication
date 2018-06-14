package com.example.thu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    TextView Edt_Search;
    Button Btn_Search,Btn_ChangeActivity;
    TextView Txt_Name,Txt_Country,Txt_Humidity,Txt_Cloud,Txt_Wind,Txt_UpdateDay,Txt_Status,Txt_temperature;
    ImageView Img_ImageIcon;

    String City_Big="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddCOntrols();
        GetCurrenWeatherData("Saigon");
        clickButtonSerarch();
        ClickDayNext();
    }

    private void ClickDayNext() {
        Btn_ChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=Edt_Search.getText().toString();
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
               intent.putExtra("name",city);
                startActivity(intent);
            }
        });
    }


    private void clickButtonSerarch() {
        Btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=Edt_Search.getText().toString();
               if(city.equals(""))
               {
                   City_Big="Saigon";
                   GetCurrenWeatherData(City_Big);
               }
               else
               {
                   City_Big=city;
                   GetCurrenWeatherData(City_Big);
               }
            }
        });
    }

    public  void GetCurrenWeatherData(String Data)
{
    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
    String url="http://api.openweathermap.org/data/2.5/weather?q="+Data+"&appid=bfb6d4795bf88bbc27bd3bc0a900ac9f";
    StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
//Log.d("Result",response);
            try {
                JSONObject jsonObject=new JSONObject(response);
            String day=jsonObject.getString("dt");
            String name=jsonObject.getString("name");
                Txt_Name.setText("Name of City : " +name);

                long l=Long.valueOf(day);
                Date date=new Date(l*1000L);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
 String Day=simpleDateFormat.format(date);
                Txt_UpdateDay.setText(Day);
                JSONArray jsonArrayWeather=jsonObject.getJSONArray("weather");
                JSONObject jsonObject1Weather=jsonArrayWeather.getJSONObject(0);
                String status=jsonObject1Weather.getString("main");
                String icon=jsonObject1Weather.getString("icon");

                Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(Img_ImageIcon);
                Txt_Status.setText(status);
                JSONObject jsonObject1Main=jsonObject.getJSONObject("main");
                String nhietdo=jsonObject1Main.getString("temp");
                String doam=jsonObject1Main.getString("humidity");

                Double a=Double.valueOf(nhietdo);
                String NhietDo=String.valueOf(a.intValue());
                Txt_temperature.setText(NhietDo+" C");
                Txt_Humidity.setText(doam+" %");

                JSONObject jsonObject1Wind=jsonObject.getJSONObject("wind");
                String Gio=jsonObject1Wind.getString("speed");
                Txt_Wind.setText(Gio+" m/s");

                JSONObject jsonObject1Clounds=jsonObject.getJSONObject("clouds");
                String May=jsonObject1Clounds.getString("all");
                Txt_Cloud.setText(May+" %");


                JSONObject jsonObject1Sys=jsonObject.getJSONObject("sys");
                String country=jsonObject1Sys.getString("country");
                Txt_Country.setText("Name Country : "+country+"");
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
    private void AddCOntrols() {
        Edt_Search=(TextView) findViewById(R.id.Edt_Search);
        Btn_Search=(Button) findViewById(R.id.Btn_Search);
        Btn_ChangeActivity=(Button) findViewById(R.id.Btn_ChangeActivity);
        Txt_Name=(TextView) findViewById(R.id.Txt_Name);
        Txt_Country=(TextView) findViewById(R.id.Txt_Country);
        Txt_Humidity=(TextView) findViewById(R.id.Txt_Humidity);
        Txt_Cloud=(TextView) findViewById(R.id.Txt_Cloud);
        Txt_Wind=(TextView) findViewById(R.id.Txt_Wind);
        Txt_UpdateDay=(TextView) findViewById(R.id.Txt_UpdateDay);
        Txt_temperature=(TextView) findViewById(R.id.Txt_temperature);
        Txt_Status=(TextView) findViewById(R.id.Txt_Status);
        Img_ImageIcon=(ImageView) findViewById(R.id.Img_ImageIcon);
    }
}
