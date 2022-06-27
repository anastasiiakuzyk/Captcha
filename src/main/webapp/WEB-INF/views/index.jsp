<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Captcha</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body {
            margin: 2em;
        }

        .captcha {
            display: flex;
            align-items: center;
            margin-bottom: 1em;
        }

        .btn {
            background-color: DodgerBlue;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 16px;
            cursor: pointer;
            margin-left: 1.5em;
        }

        .btn:hover, input[type=submit]:hover {
            background-color: RoyalBlue;
        }

        p {
            font-family: "Roboto Light", sans-serif;
            color: red;
        }

        .g-recaptcha {
            margin: 1em 0;
        }

        input[type=submit] {
            background-color: DodgerBlue;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 16px;
            cursor: pointer;
            font-weight: bold;
        }

        input[type=text] {
            font-size: 1em;
        }
    </style>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>
<div>
    To print the content of a <b><c:out value="${fileName}"/></b>, please enter a captcha below
</div>
<div class="captcha">
    <img src="getCaptcha/<c:out value='${captcha.id}'/>">
    <button class="btn" onClick="window.location.reload();"><i class="fa fa-refresh"></i></button>
</div>
<form action="${pageContext.request.contextPath}/" method="post">
    <input type="text" name="code" required>
    <c:if test="${captchaError}">
        <p>The input has not match! Try one more time</p>
    </c:if>
    <input type="hidden" name="id" value="${captcha.id}">
    <div class="g-recaptcha" data-sitekey="6Le_UwcgAAAAANAHzThKrUGntngRSQu_RP9l4rkf"></div>
    <c:if test="${reCaptchaError}">
        <p>Please verify you are not a robot!</p>
    </c:if>
    <input type="submit">
</form>
</body>
</html>