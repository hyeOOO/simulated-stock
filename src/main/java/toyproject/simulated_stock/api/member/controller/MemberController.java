package toyproject.simulated_stock.api.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toyproject.simulated_stock.api.auth.annotation.RoleUser;
import toyproject.simulated_stock.api.auth.dto.model.PrincipalDetails;
import toyproject.simulated_stock.api.member.dto.MemberDto;
import toyproject.simulated_stock.api.member.dto.MemberEditRequest;
import toyproject.simulated_stock.api.member.service.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<MemberDto> memberInfoJson(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("Entered memberInfoJson method");
        log.info("authentication : {}", principalDetails);
        return ResponseEntity.ok(memberService.memberInfo(principalDetails.getMemberKey()));
    }

    @GetMapping("/information/{memberKey}")
    public ResponseEntity<MemberDto> getUserInfo(@PathVariable String memberKey) {
        return ResponseEntity.ok(memberService.memberInfo(memberKey));
    }

    @GetMapping
    public String memberMainPage() {
        return "main";
    }

    @PatchMapping("/{memberKey}")  // memberKey를 path variable로 받도록 수정
    public ResponseEntity<MemberDto> memberEdit(
            @RequestBody @Valid MemberEditRequest request,
            @PathVariable String memberKey) {
        return ResponseEntity.ok(memberService.memberEdit(request, memberKey));
    }

    @GetMapping("/asset")
    public String assetManagement () {
        return "member/assetmanagement";
    }

    @GetMapping("/mypage")
    @PreAuthorize("isAuthenticated()")
    public String mypage(){
        return "mypage/mypage";
    }

    @GetMapping("/rank")
    public String ranking(){
        return "member/ranking";
    }
}
