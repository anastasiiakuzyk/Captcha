package ua.kpi.security.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Entity
public class Captcha {
    @Id
    @GeneratedValue
    private Integer id;

    private String code;

    @Lob
    private byte[] picture;
}
