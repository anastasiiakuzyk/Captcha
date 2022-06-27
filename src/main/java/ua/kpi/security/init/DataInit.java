package ua.kpi.security.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.kpi.security.entity.Captcha;
import ua.kpi.security.repository.CaptchaRepository;

@Component
public class DataInit implements ApplicationRunner {
    private final CaptchaRepository captchaRepository;

    @Autowired
    public DataInit(CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 20; i++) {
            Captcha captcha = new Captcha();
            CaptchaCreator captchaCreator = new CaptchaCreator();
            captchaCreator.create();
            captcha.setCode(captchaCreator.getCode());
            captcha.setPicture(captchaCreator.getPicture());
            captchaRepository.save(captcha);
        }
    }
}
