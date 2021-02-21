package com.example.pfairplayservice.common.exception;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.model.Member;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.regex.Pattern;

public class MyExceptionHandler {

    public static void MemberPostExceptionHandler(Member member) {

        // null check
        if (member.getId() == null) throw new RequiredParamNotFoundException("id를 입력해주세요.");
        if (member.getPassword() == null) throw new RequiredParamNotFoundException("password를 입력해주세요.");
        if (member.getName() == null) throw new RequiredParamNotFoundException("이름을 입력해주세요.");
        if (member.getBirthday() == null) throw new RequiredParamNotFoundException("생년월일을 입력해주세요.");
        if (member.getAddress() == null) throw new RequiredParamNotFoundException("주소를 입력해주세요.");
        if (member.getPhoneNumber() == null) throw new RequiredParamNotFoundException("핸드펀번호를 입력해주세요.");

        // id
        if (member.getId().length() < 6 || member.getId().length() > 10)
            throw new PatternSyntaxNotMatchedException("id는 6 ~ 10 자리 사이여야 합니다.");
        if (!Pattern.matches("^[A-Za-z0-9]{6,10}", member.getId()))
            throw new PatternSyntaxNotMatchedException("id는 숫자 혹은 문자로만 이루어져야 합니다.");

        // password
        if (member.getPassword().length() < 6 || member.getPassword().length() > 10)
            throw new PatternSyntaxNotMatchedException("password는 6 ~ 10 자리 사이여야 합니다.");
        if (!Pattern.matches("^[A-Za-z0-9]{6,10}", member.getPassword()))
            throw new PatternSyntaxNotMatchedException("password는 숫자 혹은 문자로만 이루어져야 합니다.");

        // name
        if (member.getName().length() < 2 || member.getName().length() > 10)
            throw new PatternSyntaxNotMatchedException("이름은 2 ~ 10 자리 사이여야 합니다.");
        if (!Pattern.matches("^[가-힣]{2,10}", member.getName()))
            throw new PatternSyntaxNotMatchedException("이름은 한글만 입력 가능 합니다.");

        // birtday
        if (member.getBirthday() < 10000000 || member.getBirthday() > 99999999)
            throw new PatternSyntaxNotMatchedException("생년월일은 8 자리입니다.");
        if (!Pattern.matches("^[0-9]{8}", member.getBirthday().toString()))
            throw new PatternSyntaxNotMatchedException("생년월일은 숫자만 입력할 수 있습니다.");

        // address
        if (!Pattern.matches("^[가-힣]{2,20}", member.getAddress()))
            throw new PatternSyntaxNotMatchedException("주소는 한글만 입력 가능 합니다.");

        // phoneNumber
        if (member.getPhoneNumber().length() != 11 )
            throw new PatternSyntaxNotMatchedException("핸드폰번호는 11자리 입니다.");
        if (!Pattern.matches("^[0-9]{11}", member.getPhoneNumber()))
            throw new PatternSyntaxNotMatchedException("핸드폰번호는 숫자만 입력할 수 있습니다.");
    }

}
