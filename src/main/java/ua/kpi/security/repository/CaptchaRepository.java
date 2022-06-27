package ua.kpi.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.security.entity.Captcha;

@Repository
public interface CaptchaRepository extends JpaRepository<Captcha, Integer> {
}
