FROM openjdk:11

ADD target/Captcha-2.6.6.jar Captcha.jar

ENTRYPOINT ["java", "-jar", "Captcha.jar"]