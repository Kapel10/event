package project.eventregistration.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.eventregistration.Utils.JwtTokenUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static project.eventregistration.Constant.SecurityConstant.Authorization;
import static project.eventregistration.Constant.SecurityConstant.Bearer;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(Authorization);
        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith(Bearer)) {
            jwt = authHeader.substring(7);
            username = jwtTokenUtils.getUsername(jwt);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(jwtTokenUtils.getRole(jwt)));
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username, null, authorities
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);

    }
}
