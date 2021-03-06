package org.sotap.MissionTap.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public final class Calendars {
    public static Integer timeOffset;

    public static void init() {
        timeOffset = Files.config.getInt("time-offset");
    }

    public static SimpleDateFormat getFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static String stampToString(Long stamp) {
        return getFormatter().format(new Date(stamp));
    }

    public static Long getNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (timeOffset != 0) {
            cal.add(Calendar.HOUR_OF_DAY, timeOffset);
        }
        return cal.getTime().getTime();
    }

    public static Date getNextWeeklyRefresh() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int today = cal.get(Calendar.DAY_OF_WEEK);
        int refreshDay = Files.config.getInt("weekly-refresh-time");
        int nextWeekdayOffset = today == (refreshDay - 1) ? 1 : refreshDay + 7 - today;
        cal.add(Calendar.DAY_OF_MONTH, nextWeekdayOffset);
        if (timeOffset != 0) {
            cal.add(Calendar.HOUR_OF_DAY, timeOffset);
        }
        return cal.getTime();
    }

    public static Date getNextDailyRefresh() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int now = cal.get(Calendar.HOUR_OF_DAY);
        int refreshHour = Files.config.getInt("daily-refresh-time");
        if (now >= refreshHour) {
            cal.add(Calendar.DATE, 1);
        }
        cal.set(Calendar.HOUR_OF_DAY, refreshHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        if (timeOffset != 0) {
            cal.add(Calendar.HOUR_OF_DAY, timeOffset);
        }
        return cal.getTime();
    }

    public static long getMissionExpiration(String type) {
        if (!List.of("daily", "weekly").contains(type))
            return 0;
        if (Objects.equals(type, "daily")) {
            return getNextDailyRefresh().getTime();
        } else {
            Date weeklyRefresh = getNextWeeklyRefresh();
            if (Files.config.getBoolean("allow-tarriance")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                if (timeOffset != 0) {
                    cal.add(Calendar.HOUR_OF_DAY, timeOffset);
                }
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                weeklyRefresh = cal.getTime();
            }
            return weeklyRefresh.getTime();
        }
    }

    public static long getNextRefresh(String type) {
        if (!List.of("daily", "weekly").contains(type))
            return 0;
        return type == "daily" ? getNextDailyRefresh().getTime() : getNextWeeklyRefresh().getTime();
    }
}
