<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form method="POST" action="/auth/login">
    <label>Username</label>
    <input type="text" name="username"/>

    <br>

    <label>Password</label>
    <input type="password" name="password"/>

    <p>${param.error}</p> <!-- ETL -->

    <button>Login</button>
</form>

</body>
</html>
