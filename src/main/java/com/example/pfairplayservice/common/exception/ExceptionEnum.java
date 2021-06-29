package com.example.pfairplayservice.common.exception;

public enum ExceptionEnum {
    UNKNOWN("unknown", "알 수 없는 오류 입니다."),
    MONTH01("month","월은 1 ~ 12 사이의 값 이여야 합니다."),
    DOW01("dayOfWeek", "요일은 (일, 월, 화, 수, 목, 금, 토) 의 값 중 하나 입니다."),
    STATE01("state", "도시를 선택해 주세요"),
    CITY01("city", "시군구를 선택해 주세요"),
    MINSH01("minStartHour", "최소시작시간은 0 ~ 23 사이의 값 이여야 합니다."),
    MAXSH01("minStartHour", "최대시작시간은 0 ~ 23 사이의 값 이여야 합니다."),
    MINL01("minLevel", "최소레벨은 1 ~ 5 사이의 값 이여야 합니다."),
    MAXL01("maxLevel", "최대레벨은 1 ~ 5 사이의 값 이여야 합니다."),
    PGN01("playGroundNo", "경기장 번호를 입력해 주세요."),
    PGN02("playGroundNo", "경기장 번호는 1이상 의 값 입니다."),
    PRICE01("price", "경기비용을 입력해 주세요"),
    OTT01("ownerTeamTid", "주최팀 식별자를 입력해 주세요"),
    SD01("startDate", "시작일시를 입력해 주세요"),
    ED01("endDate", "종료일시를 입력해 주세요"),
    MSG01("message", "메세지를 입력해 주세요"),
    MSG02("message", "메세지는 1 ~ 255 글자 사이로 입력해 주세요");




    private String fieldName;
    private String message;

    ExceptionEnum(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

    public static ExceptionEnum valueOfCode(String code) {
        for (ExceptionEnum ee : ExceptionEnum.values()) {
            if(ee.fieldName.equals(code)) {
                return ee;
            }
        }
        return null;
    }
}
