package com.keinosuke.todoapp.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class TimeConverter {
    public static LocalDateTime longToLocalDateTime(Long epochSecond){
        TimeZone timeZone = TimeZone.getDefault();
        int timeZoneOffset = timeZone.getOffset(System.currentTimeMillis());
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timeZoneOffset / 1000);
        return LocalDateTime.ofEpochSecond(epochSecond, 0, zoneOffset);
    }

    public static Long localDateTimeToLong(LocalDateTime localDateTime){
        TimeZone timeZone = TimeZone.getDefault();
        int timeZoneOffset = timeZone.getOffset(System.currentTimeMillis());
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timeZoneOffset / 1000);
        return localDateTime.toEpochSecond(zoneOffset);
    }
}
