package com.example.admin_linux.csdevproject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateHelper {
    public static String normalizeDate(String inputDate){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", java.util.Locale.getDefault());
        try {
            calendar.setTime(sdf.parse(inputDate));

            String compareDate = calendar.get(Calendar.YEAR) + ":" + calendar.get(Calendar.MONTH) + ":" + calendar.get(Calendar.DAY_OF_MONTH);
            if(checkIfItIsToday(compareDate)){
                String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                String minute = String.valueOf(calendar.get(Calendar.MINUTE));

                if(minute.length() == 1) return hour + ":0" + minute;
                else return hour + ":" + minute;
            } else if(checkIfItIsYesterday(compareDate)){
                    return "Yesterday";
                } else {
                return getFriendlyMonth(calendar.get(Calendar.MONTH)) + ":" + calendar.get(Calendar.DAY_OF_MONTH);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFriendlyMonth(int key) {
        switch (key) {
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
            default:
                return "NaN";

        }
    }
            private static boolean checkIfItIsToday(String compareDate){
        Calendar currentTime = Calendar.getInstance();
        return compareDate
                .equals(currentTime.get(Calendar.YEAR) + ":" +
                        currentTime.get(Calendar.MONTH) + ":" +
                        currentTime.get(Calendar.DAY_OF_MONTH));
    }

    private static boolean checkIfItIsYesterday(String compareDate){
        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.DATE, -1);

        return compareDate
                .equals(currentTime.get(Calendar.YEAR) + ":" +
                        currentTime.get(Calendar.MONTH) + ":" +
                        currentTime.get(Calendar.DAY_OF_MONTH));
    }
}
