package common.util;

import model.enumfield.Status;

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
        String mainAddress;
        if (city != null) {
            mainAddress = state + " " + city;
        } else {
            mainAddress = state;
        }

        query = query + "m.play_ground_no in (select pg.play_ground_no from play_ground pg where pg.main_address like '" + mainAddress + "%') ";

        return this;
    }

    public MatchQueryBuilder date(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCondition = simpleDateFormat.format(date);

        query = query + "and date_format(m.start_date, '%Y-%m-%d') = '" + dateCondition + "' ";

        return this;
    }

    public MatchQueryBuilder time(Integer minStartTime, Integer maxStartTime) {
        if (minStartTime != null) {
            query = query + "and hour(m.start_date) >= " + minStartTime + " ";
        }

        if (maxStartTime != null) {
            query = query + "and hour(m.start_date) <= " + maxStartTime + " ";
        }

        return this;
    }

    public MatchQueryBuilder level(Integer minLevel, Integer maxLevel) {
        int minValue = 0;
        int maxValue = 5;
        if(minLevel != null) minValue = minLevel;
        if(maxLevel != null) maxValue = maxLevel;

            query = query + "and m.owner_team_tid in (select t.tid from team t where t.level >= "
                    + minValue + " and level <= " + maxValue + ") ";

        return this;
    }

    public MatchQueryBuilder playGroundNo(Integer playGroundNo) {
        if (playGroundNo != null) {
            query = query + "and m.play_ground_no = " + playGroundNo + " ";
        }
        return this;
    }

    public MatchQueryBuilder isOnlyOngoing(Boolean isOnlyOngoing) {
        if (isOnlyOngoing != null) {
            if (isOnlyOngoing) {
                query = query + "and m.status = " + Status.ONGOING.getStatus() + " ";
            } else {
                query = query + "and m.status != " + Status.HiDE.getStatus() + " ";
            }
        }

        return this;
    }

    public MatchQueryBuilder offset(Integer offset) {
        if (offset != null) {
            int offsetValue = pageSize * (offset - 1);
            query = query + "order by m.start_date limit " + offsetValue + ", " + pageSize;
        }

        return this;
    }

    public String build() {
        return query;
    }


}


