package com.example.skylap_datn_md03.utils;

import java.util.Date;

public class DateUtils {

    // Phương thức tính số ngày giữa một ngày cụ thể và thời gian hiện tại
    public static int getDaysDifference(Date date) {
        // Lấy thời gian hiện tại
        Date currentDate = new Date();

        // Chuyển đổi thời gian thành mili giây
        long time1 = date.getTime();
        long time2 = currentDate.getTime();

        // Tính số ngày cách nhau
        long diffInMillies = time1 - time2;
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

        return (int) diffInDays;
    }
}
