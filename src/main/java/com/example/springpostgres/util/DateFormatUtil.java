package com.example.springpostgres.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    public static String formatDateTime(Date date){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
        
    }

}
