package com.store.soattechchallenge.identification.infrastructure.adapters.out.security;

import com.store.soattechchallenge.identification.domain.model.Identification;
import com.store.soattechchallenge.identification.domain.repository.IdentificationRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilterSecurity extends OncePerRequestFilter {

    private final JwtTokenSecurity jwtTokenSecurity;

    private final IdentificationRepository identificationRepository;

    public JwtRequestFilterSecurity(JwtTokenSecurity jwtTokenSecurity, IdentificationRepository identificationRepository) {
        this.jwtTokenSecurity = jwtTokenSecurity;
        this.identificationRepository = identificationRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String cpfOrEmail = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                cpfOrEmail = jwtTokenSecurity.getDocumentOrEmailFromToken(jwtToken);
            } catch (Exception e) {
                logger.error("Não foi possível obter o token", e);
            }
        }

        if (cpfOrEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Identification> user = this.identificationRepository.findByDocumentOrEmail(cpfOrEmail, cpfOrEmail);

            if (user.isPresent() && jwtTokenSecurity.validateToken(jwtToken, user)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                user, null, null);
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
