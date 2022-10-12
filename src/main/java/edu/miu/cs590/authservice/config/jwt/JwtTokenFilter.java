package edu.miu.cs590.authservice.config.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    //    private final JwtU
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!hasAuthorizationBearToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }


        String token = getToken(request);
        try{
            DecodedJWT jwt = jwtTokenUtil.validateToken(token);

            setAuthenticationContext(jwt, request);
            filterChain.doFilter(request, response);

        }catch (JWTVerificationException ex){
            System.out.println(ex.getMessage());
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));

        }

    }


    private boolean hasAuthorizationBearToken(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer"))
            return false;
        return true;


    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header.split(" ")[1].trim();
    }

    private void setAuthenticationContext(DecodedJWT jwt, HttpServletRequest request) {

       List<String> authorities = jwt.getClaim("roles").asList(String.class);
        Set<GrantedAuthority> grantedAuthorities = authorities.stream().map(it -> new SimpleGrantedAuthority(it)).collect(Collectors.toSet());


        Authentication authentication = new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
