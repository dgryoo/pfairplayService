package com.example.pfairplayservice.common.exception;


import com.example.pfairplayservice.model.modifier.MemberModifier;
import com.example.pfairplayservice.model.modifier.TeamModifier;
import com.example.pfairplayservice.model.origin.Member;
import com.example.pfairplayservice.model.origin.Team;
import io.micrometer.core.instrument.util.StringUtils;

import java.util.regex.Pattern;

public class EntityFieldValueChecker {

    public static void checkMemberPostFieldValue(Member member) {

        // not null, empty value check
        if (StringUtils.isEmpty(member.getId())) throw new RequiredParamNotFoundException("id를 입력해주세요.");
        if (StringUtils.isEmpty(member.getPassword())) throw new RequiredParamNotFoundException("password를 입력해주세요.");
        if (StringUtils.isEmpty(member.getName())) throw new RequiredParamNotFoundException("이름을 입력해주세요.");
        if (StringUtils.isEmpty(member.getBirthday().toString()))
            throw new RequiredParamNotFoundException("생년월일을 입력해주세요.");
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

    public static void checkMemberPutFieldValue(MemberModifier memberModifier) {

        // address check
        if (StringUtils.isEmpty(memberModifier.getAddress()))
            throw new RequiredParamNotFoundException("주소를 입력해주세요.");
        else if (!Pattern.matches("^[가-힣]{2,20}", memberModifier.getAddress()))
            throw new PatternSyntaxNotMatchedException("주소는 2 ~ 20 자리 한글만 입력 가능 합니다.");

        // phoneNumber check
        if (StringUtils.isEmpty(memberModifier.getPhoneNumber()))
            throw new RequiredParamNotFoundException("핸드폰번호를 입력해주세요.");
        else if (!Pattern.matches("^[0-9]{11}", memberModifier.getPhoneNumber()))
            throw new PatternSyntaxNotMatchedException("핸드폰번호는 11 자리 숫자만 입력할 수 있습니다.");

        // preferPosition check
        if (memberModifier.getPreferPosition() < 0 && memberModifier.getPreferPosition() > 7)
            throw new PatternSyntaxNotMatchedException("선호포지션은 0~7 사이 숫자 입니다.");

        // level check
        if (memberModifier.getLevel() == 0)
            throw new RequiredParamNotFoundException("레벨을 입력해주세요.");
        else if (memberModifier.getLevel() < 1 && memberModifier.getLevel() > 5)
            throw new PatternSyntaxNotMatchedException("레벨은 1~5 사이 숫자 입니다.");

        // phoneNumberDisclosureOption check
        if (memberModifier.getPhoneNumberDisclosureOption() == 0)
            throw new RequiredParamNotFoundException("핸드폰번호 공개범위를 입력해주세요.");
        else if (memberModifier.getPhoneNumberDisclosureOption() < 1 && memberModifier.getPhoneNumberDisclosureOption() > 3)
            throw new PatternSyntaxNotMatchedException("핸드폰번호 공개범위는 1~3 사이 숫자 입니다.");
    }

    public static void checkTeamPostFieldValue(Team team) {

        // teamName
        if (StringUtils.isEmpty(team.getTeamName()))
            throw new RequiredParamNotFoundException("팀이름을 입력해주세요.");
        else if (!Pattern.matches("^[A-Za-z0-9가-힣]{2,10}", team.getTeamName()))
            throw new PatternSyntaxNotMatchedException("이름은 2 ~ 10 자리 특수문자를 제외하고 입력 가능 합니다.");

        // address
        if (StringUtils.isEmpty(team.getActivityAreaAddress()))
            throw new RequiredParamNotFoundException("활동지역을 입력해주세요.");
        else if (!Pattern.matches("^[가-힣]{2,20}", team.getActivityAreaAddress()))
            throw new PatternSyntaxNotMatchedException("활동지역은 2 ~ 20 자리 한글만 입력 가능 합니다.");

        // registrationDate()
        if (StringUtils.isEmpty(team.getFoundDate().toString()))
            throw new RequiredParamNotFoundException("둥록일자를 입력해주세요.");

    }

    public static void checkTeamPutFieldValue(TeamModifier teamModifier) {

        // teamName
        if (StringUtils.isEmpty(teamModifier.getTeamName()))
            throw new RequiredParamNotFoundException("팀이름을 입력해주세요.");
        else if (!Pattern.matches("^[A-Za-z0-9가-힣]{2,10}", teamModifier.getTeamName()))
            throw new PatternSyntaxNotMatchedException("이름은 2 ~ 10 자리 특수문자를 제외하고 입력 가능 합니다.");

        // address
        if (StringUtils.isEmpty(teamModifier.getActivityAreaAddress()))
            throw new RequiredParamNotFoundException("활동지역을 입력해주세요.");
        else if (!Pattern.matches("^[가-힣]{2,20}", teamModifier.getActivityAreaAddress()))
            throw new PatternSyntaxNotMatchedException("활동지역은 2 ~ 20 자리 한글만 입력 가능 합니다.");

        // registrationDate()
        if (StringUtils.isEmpty(teamModifier.getFoundDate().toString()))
            throw new RequiredParamNotFoundException("둥록일자를 입력해주세요.");

    }

}