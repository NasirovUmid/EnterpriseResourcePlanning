package com.pm.EnterpriseResourcePlanning.filter;

import com.pm.EnterpriseResourcePlanning.configuration.CustomUserDetails;
import com.pm.EnterpriseResourcePlanning.dao.impl.AlertSystemDaoImpl;
import com.pm.EnterpriseResourcePlanning.usecases.CustomUserDetailsUseCase;
import com.pm.EnterpriseResourcePlanning.usecases.JwtUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUseCase jwtUseCase;
    private final CustomUserDetailsUseCase customUserDetailsUseCase;
    private final AlertSystemDaoImpl alertSystemDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUseCase.getTokenFromRequest(request);
        System.out.println("Path: " + request.getServletPath() + " | Token: " + token);

        if (token == null) {
            System.out.println("No token, skipping filter...");
            filterChain.doFilter(request, response);
            return;
        }


        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {

            if (jwtUseCase.validateJwtToken(token)) {

                String username = jwtUseCase.getEmailFromToken(token);
                setCustomUserDetailsToSecurityContextHolder(username);
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            alertSystemDao.alert(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private void setCustomUserDetailsToSecurityContextHolder(String email) {

        CustomUserDetails usersDetails = (CustomUserDetails) customUserDetailsUseCase.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                usersDetails, null, usersDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
