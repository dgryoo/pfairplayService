package common.exception.deprecated;


import model.enumfield.DisClosureOption;
import model.enumfield.Position;
import model.post.*;
import model.put.MatchForPut;
import model.put.MemberForPut;
import model.put.NeedTeamArticleForPut;
import model.put.TeamForPut;
import io.micrometer.core.instrument.util.StringUtils;

import java.util.regex.Pattern;

public class EntityFieldValueChecker {

    public static void checkMemberPostFieldValue(MemberForPost memberForPost) {

        // not null, empty value check
        if (StringUtils.isEmpty(memberForPost.getId())) throw new RequiredParamNotFoundException("id를 입력해주세요.");
        if (StringUtils.isEmpty(memberForPost.getPassword()))
            throw new RequiredParamNotFoundException("password를 입력해주세요.");
        if (StringUtils.isEmpty(memberForPost.getName())) throw new RequiredParamNotFoundException("이름을 입력해주세요.");
        if (StringUtils.isEmpty(memberForPost.getBirthday().toString()))
            throw new RequiredParamNotFoundException("생년월일을 입력해주세요.");
        if (StringUtils.isEmpty(memberForPost.getAddress())) throw new RequiredParamNotFoundException("주소를 입력해주세요.");
        if (StringUtils.isEmpty(memberForPost.getPhoneNumber()))
            throw new RequiredParamNotFoundException("핸드폰번호를 입력해주세요.");

        // id
        if (!Pattern.matches("^[A-Za-z0-9]{6,10}", memberForPost.getId()))
            throw new PatternSyntaxNotMatchedException("id는 6 ~ 10 자리 숫자 혹은 문자로 이루어져야 합니다.");

        // password
        if (!Pattern.matches("^[A-Za-z0-9]{6,10}", memberForPost.getPassword()))
            throw new PatternSyntaxNotMatchedException("password는 6 ~ 10 자리 숫자 혹은 문자로 이루어져야 합니다.");

        // name
        if (!Pattern.matches("^[가-힣]{2,10}", memberForPost.getName()))
            throw new PatternSyntaxNotMatchedException("이름은 2 ~ 10 자리 한글만 입력 가능 합니다.");

        // address
        if (!Pattern.matches("^[가-힣]{2,20}", memberForPost.getAddress()))
            throw new PatternSyntaxNotMatchedException("주소는 2 ~ 20 자리 한글만 입력 가능 합니다.");

        // phoneNumber
        if (!Pattern.matches("^[0-9]{11}", memberForPost.getPhoneNumber()))
            throw new PatternSyntaxNotMatchedException("핸드폰번호는 11 자리 숫자만 입력할 수 있습니다.");

        // preferPosition default Value

        if (memberForPost.getPreferPosition() == null) {
            memberForPost.setPreferPosition(Position.NONE);
        }

        // phoneNumberDisclosureOption default Value
        if (memberForPost.getPhoneNumberDisclosureOption() == null) {
            memberForPost.setPhoneNumberDisclosureOption(DisClosureOption.ALL);
        }

    }

    public static void checkMemberPutFieldValue(MemberForPut memberForPut) {

        // address check
        if (StringUtils.isEmpty(memberForPut.getAddress()))
            throw new RequiredParamNotFoundException("주소를 입력해주세요.");
        else if (!Pattern.matches("^[가-힣]{2,20}", memberForPut.getAddress()))
            throw new PatternSyntaxNotMatchedException("주소는 2 ~ 20 자리 한글만 입력 가능 합니다.");

        // phoneNumber check
        if (StringUtils.isEmpty(memberForPut.getPhoneNumber()))
            throw new RequiredParamNotFoundException("핸드폰번호를 입력해주세요.");
        else if (!Pattern.matches("^[0-9]{11}", memberForPut.getPhoneNumber()))
            throw new PatternSyntaxNotMatchedException("핸드폰번호는 11 자리 숫자만 입력할 수 있습니다.");

        // preferPosition check
        if (memberForPut.getPreferPosition() == null)
            throw new RequiredParamNotFoundException("선호포지션을 입력해주세요.");
        else if (memberForPut.getPreferPosition().getPosition() < 0 && memberForPut.getPreferPosition().getPosition() > 4)
            throw new PatternSyntaxNotMatchedException("선호포지션은 0~4 사이 숫자 입니다.");

        // level check
        if (memberForPut.getLevel() == 0)
            throw new RequiredParamNotFoundException("레벨을 입력해주세요.");
        else if (memberForPut.getLevel() < 1 && memberForPut.getLevel() > 5)
            throw new PatternSyntaxNotMatchedException("레벨은 1~5 사이 숫자 입니다.");

        // phoneNumberDisclosureOption check
        if (memberForPut.getPhoneNumberDisclosureOption() == null)
            throw new RequiredParamNotFoundException("핸드폰번호 공개범위를 입력해주세요.");
        else if (memberForPut.getPhoneNumberDisclosureOption().getDisclosureOption() < 0 && memberForPut.getPhoneNumberDisclosureOption().getDisclosureOption() > 2)
            throw new PatternSyntaxNotMatchedException("핸드폰번호 공개범위는 0~2 사이 숫자 입니다.");
    }

    public static void checkTeamPostFieldValue(TeamForPost teamForPost) {

        // teamName
        if (StringUtils.isEmpty(teamForPost.getTeamName()))
            throw new RequiredParamNotFoundException("팀이름을 입력해주세요.");
        else if (!Pattern.matches("^[A-Za-z0-9가-힣]{2,10}", teamForPost.getTeamName()))
            throw new PatternSyntaxNotMatchedException("이름은 2 ~ 10 자리 특수문자를 제외하고 입력 가능 합니다.");

        // address
        if (StringUtils.isEmpty(teamForPost.getActivityAreaAddress()))
            throw new RequiredParamNotFoundException("활동지역을 입력해주세요.");
        else if (!Pattern.matches("^[가-힣]{2,20}", teamForPost.getActivityAreaAddress()))
            throw new PatternSyntaxNotMatchedException("활동지역은 2 ~ 20 자리 한글만 입력 가능 합니다.");

        // registrationDate()
        if (StringUtils.isEmpty(teamForPost.getFoundDate().toString()))
            throw new RequiredParamNotFoundException("둥록일자를 입력해주세요.");

    }

    public static void checkTeamPutFieldValue(TeamForPut teamModifier) {

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

    public static void checkNeedTeamArticlePostFieldValue(NeedTeamArticleForPost needTeamArticleForPost) {

        // writeMemberUid
        if (StringUtils.isEmpty(needTeamArticleForPost.getWriteMemberUid()))
            throw new RequiredParamNotFoundException("uid를 입력해주세요");

        // subject
        if (StringUtils.isEmpty(needTeamArticleForPost.getSubject()))
            throw new RequiredParamNotFoundException("제목을 입력해주세요");
        else if (!Pattern.matches("^[A-Za-z0-9가-힣]{2,20}", needTeamArticleForPost.getSubject()))
            throw new PatternSyntaxNotMatchedException("제목은 2 ~ 20 자리 특수문자를 제외하고 입력 가능 합니다.");

        // detail
        if (StringUtils.isEmpty(needTeamArticleForPost.getSubject()))
            throw new RequiredParamNotFoundException("내용을 입력해주세요");
        else if (needTeamArticleForPost.getDetail().length() < 1 && needTeamArticleForPost.getDetail().length() > 255)
            throw new PatternSyntaxNotMatchedException("내용은 1 ~ 255 자리 입력 가능 합니다.");

        // needPosition
        if (needTeamArticleForPost.getNeedPosition() == null)
            throw new RequiredParamNotFoundException("필요포지션을 입력해주세요.");
        else if (needTeamArticleForPost.getNeedPosition().getPosition() <= 0 && needTeamArticleForPost.getNeedPosition().getPosition() >= 4)
            throw new PatternSyntaxNotMatchedException("필요포지션은 0~4 사이 숫자 입니다.");

    }

    public static void checkNeedTeamArticlePutFieldValue(NeedTeamArticleForPut needTeamArticleForPut) {

        // writeMemberUid
        if (StringUtils.isEmpty(needTeamArticleForPut.getWriteMemberUid()))
            throw new RequiredParamNotFoundException("uid를 입력해주세요");

        // subject
        if (StringUtils.isEmpty(needTeamArticleForPut.getSubject()))
            throw new RequiredParamNotFoundException("제목을 입력해주세요");
        else if (!Pattern.matches("^[A-Za-z0-9가-힣]{2,20}", needTeamArticleForPut.getSubject()))
            throw new PatternSyntaxNotMatchedException("제목은 2 ~ 20 자리 특수문자를 제외하고 입력 가능 합니다.");

        // detail
        if (StringUtils.isEmpty(needTeamArticleForPut.getSubject()))
            throw new RequiredParamNotFoundException("내용을 입력해주세요");
        else if (needTeamArticleForPut.getDetail().length() < 1 && needTeamArticleForPut.getDetail().length() > 255)
            throw new PatternSyntaxNotMatchedException("내용은 1 ~ 255 자리 입력 가능 합니다.");

        // needPosition
        if (needTeamArticleForPut.getNeedPosition() == null)
            throw new RequiredParamNotFoundException("필요포지션을 입력해주세요.");
        else if (needTeamArticleForPut.getNeedPosition().getPosition() <= 0 && needTeamArticleForPut.getNeedPosition().getPosition() >= 4)
            throw new PatternSyntaxNotMatchedException("필요포지션은 0~4 사이 숫자 입니다.");

    }

    public static void checkMatchPostFieldValue(MatchForPost matchForPost) {

        // groundNo
        if (matchForPost.getPlayGroundNo() == 0)
            throw new RequiredParamNotFoundException("groundNo를 입력해 주세요");

        // ownerTeamTid
        if (matchForPost.getOwnerTeamTid().isEmpty())
            throw new RequiredParamNotFoundException("owner의 Tid를 입력해주세요");

        // startDate
        if (StringUtils.isEmpty(matchForPost.getStartDate().toString()))
            throw new RequiredParamNotFoundException("시작일을 입력해주세요.");

        // endDate
        if (StringUtils.isEmpty(matchForPost.getEndDate().toString()))
            throw new RequiredParamNotFoundException("종료일을 입력해주세요.");

    }

    public static void checkMatchPutFieldValue(MatchForPut matchForPut) {

        // groundNo
        if (matchForPut.getPlayGroundNo() == 0)
            throw new RequiredParamNotFoundException("groundNo를 입력해 주세요");

        // startDate
        if (StringUtils.isEmpty(matchForPut.getStartDate().toString()))
            throw new RequiredParamNotFoundException("시작일을 입력해주세요.");

        if (StringUtils.isEmpty(matchForPut.getEndDate().toString()))
            throw new RequiredParamNotFoundException("종료일을 입력해주세요.");

    }

    public static void checkOfferPostFieldValue(OfferForPost offerForPost) {

        // targetMatchNo
        if (offerForPost.getTargetMatchNo() == 0) {
            throw new RequiredParamNotFoundException("매치가 선택되지 않았습니다.");
        }

        // sandTeamTid
        if (offerForPost.getSandTeamTid().isEmpty()) {
            throw new RequiredParamNotFoundException("보내는 팀이 선택되지 않았습니다.");
        }

        // receiveTeamTid
        if (offerForPost.getReceiveTeamTid().isEmpty()) {
            throw new RequiredParamNotFoundException("보내는 팀이 선택되지 않았습니다.");
        }

    }
}