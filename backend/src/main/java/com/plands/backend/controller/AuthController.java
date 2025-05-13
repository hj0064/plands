package com.plands.backend.controller;

import com.plands.backend.auth.CookieUtils;
import com.plands.backend.auth.JwtToken;
import com.plands.backend.auth.JwtTokenProvider;
import com.plands.backend.dto.MemberDto;
import com.plands.backend.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberServiceImpl memberService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDto signUpRequest) {
        System.out.println("회원가입 요청 들어옴");
        MemberDto memberDto = memberService.saveMember(signUpRequest);
        System.out.println(memberDto);

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(memberDto);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authenticationToken);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        // RefreshToken을 사용하여 새로운 AccessToken을 생성
        String newAccessToken = jwtTokenProvider.regenerateAccessToken(refreshToken);

        // 새로운 AccessToken 반환
        return ResponseEntity.ok(new JwtToken("Bearer", newAccessToken, refreshToken));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto) {
        MemberDto user = memberService.findByEmail(memberDto.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!memberService.matchesPassword(memberDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(memberDto);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authenticationToken);

        // 발급된 JWT 토큰을 응답에 포함하여 반환
        return ResponseEntity.ok(jwtToken);
    }

    private static UsernamePasswordAuthenticationToken getAuthenticationToken(MemberDto memberDto) {
        String role = "ROLE_USER";
        if (memberDto.getRole() != null) {
            role = memberDto.getRole().name();  // 만약 다른 역할이 있으면 그 역할을 사용
        }

        // 권한 정보를 포함한 UsernamePasswordAuthenticationToken 생성
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role));

        // JWT 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                memberDto.getEmail(), "", authorities);
        return authenticationToken;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // 클라이언트가 보내는 Authorization 헤더에서 JWT 추출
        String token = jwtTokenProvider.resolveAccessToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            // JWT가 유효하다면, 블랙리스트에 추가하거나 로그 기록
            // 예를 들어, 블랙리스트에 추가 (optional)
            //jwtTokenProvider.addToBlacklist(token);  // 블랙리스트 추가 (이 부분은 구현에 따라 다름)
        }

        // 클라이언트의 쿠키에서 JWT 삭제
        CookieUtils.deleteCookie(request, response, "accessToken");
        CookieUtils.deleteCookie(request, response, "refreshToken");

        // 로그아웃 완료
        return ResponseEntity.ok().build();
    }
}
