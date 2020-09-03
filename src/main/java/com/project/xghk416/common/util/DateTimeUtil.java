package com.project.xghk416.common.util;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static String returnDateStream(int plusDay){
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();
        return today.plusDays(plusDay).format(dft);
    }
    public static String returnOneDateLimit(String date,int plusDay){
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime current_date = LocalDate.parse(date,dft).atStartOfDay();
        return current_date.plusDays(plusDay).format(dft);
    }
}
