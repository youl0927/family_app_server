package com.family.api.jwt;

import com.family.api.domain.AppUser;
import com.family.api.dto.CustomUserDetails;
import com.family.api.repository.AppUserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //헤더에서 access토큰 빼옴
        String accessToken = request.getHeader("access");

        //토큰이 없다면 다음 필터로 넘김
        if(accessToken == null){
            filterChain.doFilter(request, response);
            return;
        }

        //토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        boolean expired = isExpired(accessToken, response);
        if (expired == false) return;

        //토큰이 access인지 refresh인지 확인
        boolean category = isCategory(accessToken, response);
        if(category == false) return;

        String username = jwtUtil.getUsername(accessToken);

        AppUser appUser = appUserRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("user not found"));
        CustomUserDetails customUserDetails = new CustomUserDetails(appUser);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }

    private boolean isExpired(String accessToken, HttpServletResponse response) throws IOException {
        try{
            jwtUtil.isExpired(accessToken);
        }catch (ExpiredJwtException e){
            PrintWriter writer = response.getWriter();
            writer.println("access token expired");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private boolean isCategory(String accessToken, HttpServletResponse response) throws IOException{
        String category = jwtUtil.getCategory(accessToken);

        if(!category.equals("access")){
            //response body
            PrintWriter writer = response.getWriter();
            writer.println("invalid access token");

            //response tatus code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
