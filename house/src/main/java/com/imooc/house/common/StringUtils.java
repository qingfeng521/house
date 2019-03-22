package com.imooc.house.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {


    public static String date2String(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        if(date==null){
            date = new Date();
        }
        return sdf.format(date);


    }
}
