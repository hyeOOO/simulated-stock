package toyproject.simulated_stock.api.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public String memberMainPage() {
        return "main";
    }

    @RoleUser
    @PatchMapping
    public ResponseEntity<MemberDto> memberEdit(
            @RequestBody @Valid MemberEditRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(memberService.memberEdit(request, principalDetails.getMemberKey()));
    }

    @GetMapping("/asset")
    public String assetManagement () {
        return "member/assetmanagement";
    }
}
