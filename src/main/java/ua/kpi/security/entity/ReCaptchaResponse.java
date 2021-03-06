package ua.kpi.security.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReCaptchaResponse {
    @Getter
    @Setter
    private boolean success;

    @JsonAlias("error-codes")
    @Getter
    @Setter
    private Set<String> errorCodes;
}
