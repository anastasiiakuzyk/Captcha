package ua.kpi.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.security.entity.Captcha;
import ua.kpi.security.repository.CaptchaRepository;
import java.util.List;

@Service
public class CaptchaService {
    private final CaptchaRepository captchaRepository;

    @Autowired
    public CaptchaService(CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    public Captcha getAnyCaptcha() {
        List<Captcha> captchas = captchaRepository.findAll();
        int randomCaptcha = (int) (Math.random() * captchas.size());
        return captchas.get(randomCaptcha);
    }

    public Captcha getById(int id) {
        return captchaRepository.getById(id);
    }
}
