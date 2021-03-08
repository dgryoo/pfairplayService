package com.example.pfairplayservice.common.exception;

import com.example.pfairplayservice.model.Member;
import io.micrometer.core.instrument.util.StringUtils;

import java.util.regex.Pattern;

public class EntityExceptionHandler {

    public static void MemberPostExceptionHandler(Member member) {

        // null, empty string check
        if (StringUtils.isEmpty(member.getId())) throw new RequiredParamNotFoundException("id를 입력해주세요.");
        if (StringUtils.isEmpty(member.getPassword())) throw new RequiredParamNotFoundException("password를 입력해주세요.");
        if (StringUtils.isEmpty(member.getName())) throw new RequiredParamNotFoundException("이름을 입력해주세요.");
        if (StringUtils.isEmpty(member.getBirthday().toString())) throw new RequiredParamNotFoundException("생년월일을 입력해주세요.");
        if (StringUtils.isEmpty(member.getAddress())) throw new RequiredParamNotFoundException("주소를 입력해주세요.");
        if (StringUtils.isEmpty(member.getPhoneNumber())) throw new RequiredParamNotFoundException("핸드폰번호를 입력해주세요.");

        // id
        if (!Pattern.matches("^[A-Za-z0-9]{6,10}", member.getId()))
            throw new PatternSyntaxNotMatchedException("id는 6 ~ 10 자리 숫자 혹은 문자로 이루어져야 합니다.");

        // password
        if (!Pattern.matches("^[A-Za-z0-9]{6,10}", member.getPassword()))
            throw new PatternSyntaxNotMatchedException("password는 6 ~ 10 자리 숫자 혹은 문자로 이루어져야 합니다.");

        // name
        if (!Pattern.matches("^[가-힣]{2,10}", member.getName()))
            throw new PatternSyntaxNotMatchedException("이름은 2 ~ 10 자리 한글만 입력 가능 합니다.");

        // address
        if (!Pattern.matches("^[가-힣]{2,20}", member.getAddress()))
            throw new PatternSyntaxNotMatchedException("주소는 2 ~ 20 자리 한글만 입력 가능 합니다.");

        // phoneNumber
        if (!Pattern.matches("^[0-9]{11}", member.getPhoneNumber()))
            throw new PatternSyntaxNotMatchedException("핸드폰번호는 11 자리 숫자만 입력할 수 있습니다.");
    }

}