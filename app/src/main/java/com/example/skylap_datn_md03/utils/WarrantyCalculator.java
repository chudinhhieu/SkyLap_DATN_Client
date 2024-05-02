package com.example.skylap_datn_md03.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WarrantyCalculator {

    // Phương thức tính thời gian còn lại của bảo hành
    public static int remainingWarrantyTime(Date warrantyDate, String warrantyPeriod) {
        // Lấy ngày hiện tại
        Date currentDate = new Date();

        // Tạo định dạng cho thời gian
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Tạo mẫu biểu thức chính quy để tìm số tháng
        Pattern pattern = Pattern.compile("\\d+"); // Tìm các số trong chuỗi
        Matcher matcher = pattern.matcher(warrantyPeriod);
        int warrantyMonths = 0;
        if (matcher.find()) {
            warrantyMonths = Integer.parseInt(matcher.group());
        }

        // Tính thời gian kết thúc bảo hành
        Date warrantyEndDate = new Date(warrantyDate.getTime());
        warrantyEndDate.setMonth(warrantyEndDate.getMonth() + warrantyMonths);

        // Tính số ngày còn lại của bảo hành
        long remainingTimeInMillis = warrantyEndDate.getTime() - currentDate.getTime();
        int remainingDays = (int) (remainingTimeInMillis / (1000 * 60 * 60 * 24));

        return remainingDays;
    }
}
