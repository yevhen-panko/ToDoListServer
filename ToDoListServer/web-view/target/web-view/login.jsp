<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="/pages/css/loginFormStyle.css">
    <script type="text/javascript" src="/pages/scripts/lib/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="pages/scripts/DataModel.js"></script>
    <script type="text/javascript" src="pages/scripts/Utils.js"></script>
    <script type="text/javascript" src="pages/scripts/AjaxAPI.js"></script>
    <script type="text/javascript" src="pages/scripts/LoginPage.js"></script>
    <title>ToDoList</title>
</head>
<body>
<div class="login" id="Login">
    <h1>Login to ToDoList App</h1>
    <form method="POST" action="/j_spring_security_check">
        <p id="loginError"></p>
        <p id="registrationSuccess"></p>
        <p><input type="text" name="j_username" value="" placeholder="Email" id="LoginEmail"></p>
        <p><input type="password" name="j_password" value="" placeholder="Password" id="LoginPassword"></p>
        <p class="remember_me">
            <label>
                <input type="checkbox" name="j_spring_security_remember_me" id="remember_me">
                Remember me on this computer
            </label>
        </p>
        <p class="submit"><input type="submit" name="commit" value="Login"></p>
        <p class="submit">If you don't have account press: <input type="button" value="Registration" id="RegistrationButton"></p>
    </form>
</div>
<div class="login" id="Registration">
    <h1>Registration</h1>
        <p id="registrationError"></p>
        <p><input type="text" value="" placeholder="Enter your email" id="RegistrationEmail"></p>
        <p><input type="password" value="" placeholder="Enter password" id="RegistrationPassword"></p>
        <p class="submit"><input type="button" value="Registration" id="ConfirmRegistration" disabled></p>
</div>
</body>
</html>