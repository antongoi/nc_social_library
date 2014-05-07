<%-- 
    Document   : authentication
    Created on : Dec 25, 2013, 2:17:08 PM
    Author     : Vladimir Ermolenko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Authentication page</title>
    </head>
    <body>
        <h1>Authentication page</h1>
        <form method="POST" action="authentication">
            Enter please your login here:
            <input name="login">
            <br>
            Enter please your password:
            <input name="password">
            <br>
            <input type="Submit">
        </form>
    </body>
</html>
