package project.pfairplay.api.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateSelector {


    public static List<String> selectDateStringByMonthAndDayOfWeek(int month, int dayOfWeekNum) {

        // yyyy-MM-dd 형식의 date String 을 담을 수 있는 객체 생성
        List<String> dateList = new ArrayList<>();

        // Get Calender
        Calendar cal = Calendar.getInstance();

        // 오늘 년도
        int year = cal.get(Calendar.YEAR);

        // SimpleDateFormat yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 끝나는 날짜 지정 param으로 받은 month+1 의 1일
        cal.set(year, month, 1);
        String endDate = dateFormat.format(cal.getTime());

        // 시작 날짜 지정 param으로 받은 month의 1일
        cal.set(year, month - 1, 1);
        String startDate = dateFormat.format(cal.getTime());

        // 요일에 해당하는 param dayOfWeekNum과 일치하면 list에 add 아니면 pass
        while (!startDate.equals(endDate)) {
            if (dayOfWeekNum == cal.get(Calendar.DAY_OF_WEEK)) {
                dateList.add(dateFormat.format(cal.getTime()));
            }
            // 1일씩 증가
            cal.add(Calendar.DATE, 1);
            startDate = dateFormat.format(cal.getTime());
        }

        // return dateList
        return dateList;

    }

    public static List<Date> selectDateByMonthAndDayOfWeek(int month, int dayOfWeekNum) {

        // yyyy-MM-dd 형식의 date String 을 담을 수 있는 객체 생성
        List<Date> dateList = new ArrayList<>();

        // Get Calender
        Calendar cal = Calendar.getInstance();

        // 오늘 년도
        int year = cal.get(Calendar.YEAR);

        // SimpleDateFormat yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 끝나는 날짜 지정 param으로 받은 month+1 의 1일
        cal.set(year, month, 1);
        String endDate = dateFormat.format(cal.getTime());

        // 시작 날짜 지정 param으로 받은 month의 1일
        cal.set(year, month - 1, 1);
        String startDate = dateFormat.format(cal.getTime());

        // 요일에 해당하는 param dayOfWeekNum과 일치하면 list에 add 아니면 pass
        while (!startDate.equals(endDate)) {
            if (dayOfWeekNum == cal.get(Calendar.DAY_OF_WEEK)) {
                dateList.add(cal.getTime());
            }
            // 1일씩 증가
            cal.add(Calendar.DATE, 1);
            startDate = dateFormat.format(cal.getTime());
        }

        // return dateList
        return dateList;

    }




}
