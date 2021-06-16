package com.example.pfairplayservice.common.util;

import com.example.pfairplayservice.model.enumfield.Status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MatchQueryBuilder {

    private int pageSize = 20;

    private String query = "select * from soccer_match m where ";

    public static MatchQueryBuilder builder() {
        return new MatchQueryBuilder();
    }

    public MatchQueryBuilder dateList(List<String> dateList) {
        String dateQueryFormat = "";
        for (String date : dateList) {
            dateQueryFormat = dateQueryFormat + "'" + date + "',";
        }

        dateQueryFormat = dateQueryFormat.substring(0, dateQueryFormat.length() - 1);

        query = query + "and date_format(m.start_date, '%Y-%m-%d') in (" + dateQueryFormat + ") ";

        return this;

    }

    public MatchQueryBuilder mainAddress(String state, String city) {
        String mainAddress = state + " " + city;

        query = query + "m.play_ground_no in (select pg.play_ground_no from play_ground pg where pg.main_address = '" + mainAddress + "') ";

        return this;
    }

    public MatchQueryBuilder date(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCondition = simpleDateFormat.format(date);

        query = query + "and m.start_date = '" + dateCondition + "' ";

        return this;
    }

    public MatchQueryBuilder time(int minStartTime, int maxStartTime) {
        query = query + "and hour(m.start_date) >= " + minStartTime + " and hour(m.start_date) <= " + maxStartTime + " ";

        return this;
    }

//    public MatchQueryBuilder level(int minLevel, int maxLevel) {
//
//        if (minLevel != 0 && maxLevel !=0 ) {
//            query = query + "and m.owner_team_tid in (select t.tid from team t where t.level >= "
//                    + minLevel + " and level <= " + maxLevel + ") ";
//        }
//
//        return this;
//    }

    public MatchQueryBuilder playGroundNo(int playGroundNo) {
        if (playGroundNo != 0) {
            query = query + "and m.play_ground_no = " + playGroundNo + " ";
        }
        return this;
    }

    public MatchQueryBuilder isOnlyOngoing(boolean isOnlyOngoing) {
        if (isOnlyOngoing) {
            query = query + "and m.status = " + Status.ONGOING.getStatus() + " ";
        } else {
            query = query + "and m.status != " + Status.HiDE.getStatus() + " ";
        }

        return this;
    }

    public MatchQueryBuilder offset(int offset) {
        int offsetValue = pageSize * (offset - 1);
        query = query + "order by m.start_date limit " + offsetValue + ", " + pageSize;

        return this;
    }


    public String build() {
        return query;
    }


}


