package com.example.bartek.badminton.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static Integer calculateAge(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal1 = Calendar.getInstance(Locale.getDefault());
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance(Locale.getDefault());
        int i = 0;
        while (cal1.before(cal2)) {
            cal1.add(Calendar.YEAR, 1);
            i += 1;
        }
        return i;
    }
}
