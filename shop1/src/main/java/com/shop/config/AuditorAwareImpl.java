package com.shop.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Log4j2
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        log.info("==========================================");
        log.info("Current user: " + authentication.getName());  // Auditing 기능을 사용하기 위해서 Config 파일을 생성
        log.info("==========================================");

        String userId = "";

        if(authentication != null){
            userId = authentication.getName();
        }
        return Optional.of(userId);
    }
}
