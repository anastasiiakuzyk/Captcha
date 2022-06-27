<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Success</title>
    <style>
        img {
            width: 250px;
            margin-bottom: 1em;
        }

        .success {
            display: flex;
            flex-direction: column;
            font-family: "Roboto Light", sans-serif;
            margin: 2em;
            align-items: center;
            text-align: center;
            width: 300px;
        }
    </style>
</head>
<body>
<div class="success">
    <img src="images/correct.svg">
    Your answer is correct!<br>
    "Print" function is available now!<br>
    <hr/>
    <b>
    <c:out value="${fileText}"/>
    </b>
    <hr/>

</div>
</body>
</html>
