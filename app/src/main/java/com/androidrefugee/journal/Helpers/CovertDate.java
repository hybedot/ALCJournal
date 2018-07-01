package com.androidrefugee.journal.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CovertDate {
    public CovertDate() {

    }

    public static String toDate(Long timestamp) {
        return formatDate(new Date(timestamp));
    }

    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String formatDate(Date date){
        String pattern = "MMMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
