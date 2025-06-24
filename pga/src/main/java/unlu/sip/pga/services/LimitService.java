package unlu.sip.pga.services;


import org.springframework.security.core.Authentication;

public interface LimitService {
    boolean  canCreateCourse(Authentication auth);

}
