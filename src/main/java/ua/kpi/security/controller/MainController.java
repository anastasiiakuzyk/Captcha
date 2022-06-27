package ua.kpi.security.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.kpi.security.console.Runner;
import ua.kpi.security.entity.Captcha;
import ua.kpi.security.entity.ReCaptchaResponse;
import ua.kpi.security.service.CaptchaService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;

@Controller
public class MainController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    private final CaptchaService captchaService;

    private final RestTemplate restTemplate;

    @Autowired
    public MainController(CaptchaService captchaService, RestTemplate restTemplate) {
        this.captchaService = captchaService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("captcha", captchaService.getAnyCaptcha());
        model.addAttribute("fileName", Runner.getInstance().getFileName());
        return "index";
    }

    @PostMapping("/")
    public String checkCaptcha(@RequestParam Integer id, @RequestParam String code,
                               @RequestParam("g-recaptcha-response") String gCaptchaResponse,
                               RedirectAttributes redirectModel, Model model) {
        Captcha captcha = captchaService.getById(id);
        String url = String.format(CAPTCHA_URL, secret, gCaptchaResponse);
        ReCaptchaResponse reCaptchaResponse =
                restTemplate.postForObject(url, Collections.emptyList(), ReCaptchaResponse.class);

        if (!captcha.getCode().equals(code)) {
            redirectModel.addFlashAttribute("captchaError", true);
            return "redirect:/";
        }
        if (!reCaptchaResponse.isSuccess()) {
            redirectModel.addFlashAttribute("reCaptchaError", true);
            return "redirect:/";
        }
        Runner.getInstance().setCaptchaPassed(true);
        model.addAttribute("fileText", Runner.getInstance().getFileText());
        return "success";
    }

    @RequestMapping("/getCaptcha/{id}")
    public void getStudentPhoto(HttpServletResponse response, @PathVariable("id") int id) throws Exception {
        response.setContentType("image/jpeg");
        byte[] picture = captchaService.getById(id).getPicture();
        InputStream inputStream = new ByteArrayInputStream(picture);
        IOUtils.copy(inputStream, response.getOutputStream());
    }
}
