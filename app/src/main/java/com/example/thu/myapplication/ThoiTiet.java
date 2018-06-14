package com.example.thu.myapplication;

/**
 * Created by THU on 27/02/2018.
 */

public class ThoiTiet {
    public String Day;
    public String Status;
    public String Image;
    public  String Maxtemp;
    public String MinTemp;

    public ThoiTiet(String day, String status, String image, String maxtemp, String minTemp) {
        Day = day;
        Status = status;
        Image = image;
        Maxtemp = maxtemp;
        MinTemp = minTemp;
    }
}
