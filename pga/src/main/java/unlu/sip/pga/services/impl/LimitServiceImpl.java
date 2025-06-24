package unlu.sip.pga.services.impl;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import unlu.sip.pga.services.*;
import unlu.sip.pga.repositories.CursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
@Service
public class LimitServiceImpl implements LimitService {
    private static final int MAX_BASIC = 3;

    @Autowired
    private CursoRepository cursoRepo;

    @Override
    public boolean canCreateCourse(Authentication auth) {
        var jwt = (JwtAuthenticationToken) auth;
        // si es premium, lo deja pasar
        if (verificarPremium(jwt)) return true;
        // sino cuenta cuantos creo
        String userId = jwt.getToken().getSubject();
        long creados = cursoRepo.countByCreatedBy(userId);
        return creados < MAX_BASIC;
    }

    private boolean verificarPremium(JwtAuthenticationToken jwt) {
        return jwt.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("premiumUser"));
    }
}
