package project.pfairplay.api.controller.mysql;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.pfairplay.api.common.exception.deprecated.EntityFieldValueChecker;
import project.pfairplay.api.common.exception.deprecated.RequiredParamNotFoundException;
import project.pfairplay.api.common.exception.deprecated.SourceNotFoundException;
import project.pfairplay.api.common.filter.FilterManager;
import project.pfairplay.api.model.get.MemberForGet;
import project.pfairplay.api.model.post.MemberForPost;
import project.pfairplay.api.model.put.MemberForPut;
import project.pfairplay.storage.mysql.model.MemberEntity;
import project.pfairplay.storage.mysql.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Operation(summary = "멤버 조회")
    @GetMapping("/member/{uid}")
    public ResponseEntity<MemberForGet> findByUid(@PathVariable String uid) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(uid);

        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", uid));
        }
        return ResponseEntity.status(HttpStatus.OK).body(MemberForGet.from(memberEntity.get()));
    }

    @Operation(summary = "멤버 등록")
    @PostMapping("/member")
    public ResponseEntity<Void> createMember(@RequestBody MemberForPost memberForPost) {
        EntityFieldValueChecker.checkMemberPostFieldValue(memberForPost);
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberId(memberForPost.getId());
        if (memberEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        memberRepository.save(memberForPost.toMemberEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "멤버 수정")
    @PutMapping("/member/{uid}")
    public ResponseEntity<Void> updateByUid(@PathVariable String uid, @RequestBody MemberForPut memberForPut) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(uid);
        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("uid{%s} not found", uid));
        }

        EntityFieldValueChecker.checkMemberPutFieldValue(memberForPut);

        if (memberEntity.get().getAddress() != memberForPut.getAddress())
            memberRepository.updateAddressByUid(uid, memberForPut.getAddress());
        if (memberEntity.get().getPhoneNumber() != memberForPut.getAddress())
            memberRepository.updatePhoneNumberByUid(uid, memberForPut.getPhoneNumber());
        if (memberEntity.get().getPreferPosition() != memberForPut.getPreferPosition().getPosition())
            memberRepository.updatePreferPositionByUid(uid, memberForPut.getPreferPosition().getPosition());
        if (memberEntity.get().getLevel() != memberForPut.getLevel())
            memberRepository.updateLevelByUid(uid, memberForPut.getLevel());
        if (memberEntity.get().getPhoneNumberDisclosureOption() != memberForPut.getPhoneNumberDisclosureOption().getDisclosureOption())
            memberRepository.updatePhoneNumberDisclosureOptionByUid(uid, memberForPut.getPhoneNumberDisclosureOption().getDisclosureOption());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "멤버 삭제")
    @DeleteMapping("/member/{uid}")
    public ResponseEntity<Void> deleteByUid(@PathVariable String uid) {

        Optional<MemberEntity> member = memberRepository.findById(uid);

        if (!member.isPresent()) {
            throw new RequiredParamNotFoundException(String.format("uid{%s} not found", uid));
        }
        memberRepository.deleteById(uid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "특정 팀에 등록된 멤버 조회")
    @GetMapping("/member/team/{tid}")
    public ResponseEntity<List<MemberForGet>> findMemberListByTid(@PathVariable String tid) {
        List<MemberEntity> memberEntityList = memberRepository.findByMemberTeamIdTid(tid);

        if (memberEntityList == null)
            throw new SourceNotFoundException(String.format("member not found registered in tid{%s})", tid));

        List<MemberForGet> memberList = memberEntityList.stream().map(FilterManager::teamMemberFilter).map(MemberForGet::from).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(memberList);
    }


}
