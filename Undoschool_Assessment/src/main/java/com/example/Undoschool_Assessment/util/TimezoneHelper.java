package com.example.Undoschool_Assessment.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimezoneHelper {

    public static LocalDateTime toUtc(String localDateTimeStr, String teacherZoneId) {
        LocalDateTime local = LocalDateTime.parse(localDateTimeStr);
        ZoneId zone = ZoneId.of(teacherZoneId);
        ZonedDateTime zoned = ZonedDateTime.of(local, zone);
        return zoned.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    public static String toParentLocal(LocalDateTime utcDateTime, String parentZoneId, String pattern) {
        ZonedDateTime utc = utcDateTime.atZone(ZoneOffset.UTC);
        ZoneId parentZone = ZoneId.of(parentZoneId);
        ZonedDateTime local = utc.withZoneSameInstant(parentZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return local.format(formatter);
    }
}

