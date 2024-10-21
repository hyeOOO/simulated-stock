package toyproject.simulated_stock.api.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject.simulated_stock.api.auth.dto.model.PrincipalDetails;
import toyproject.simulated_stock.api.auth.service.TokenService;
import toyproject.simulated_stock.api.member.dto.MemberDto;
import toyproject.simulated_stock.api.member.service.MemberService;

@RequiredArgsConstructor
@Slf4j
@Controller
public class AuthController {

    private final TokenService tokenService;
    private final MemberService memberService;

    @GetMapping("/")
    public String home(){
        return "main";
    }

    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }
    @GetMapping("/mainHard")
    public String mainHard(){
        return "main_hardcoding";
    }

    @GetMapping("/login")
    public String loginPage(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails!=null){
            log.info(userDetails.getUsername() + "님이 로그인 페이지로 이동을 시도함. -> index 페이지로 강제 이동 함.");
            return "redirect:/main";
        }
        return "login";
    }

    @GetMapping("/mypage")
    @PreAuthorize("isAuthenticated()")
    public String mypage(){
        return "mypage/mypage";
    }

    @GetMapping("/auth/success")
    public String loginSuccess(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        MemberDto memberInfo = memberService.memberInfo(principalDetails.getMemberKey());
        model.addAttribute("memberInfo", memberInfo);
        return "redirect:/main";
    }
    @GetMapping("/auth/logout")
    public String logout(@AuthenticationPrincipal UserDetails userDetails, @CookieValue(value="Authorization", defaultValue = "", required = false)Cookie jwtCookie, HttpServletResponse response){
        log.info("jwt Cookie = {}", jwtCookie);
        jwtCookie.setValue(null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");

        response.addCookie(jwtCookie);
        tokenService.deleteRefreshToken(userDetails.getUsername());
        return "redirect:/login";
    }
}