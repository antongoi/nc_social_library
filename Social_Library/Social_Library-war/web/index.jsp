

<%--
    Document   : template
    Created on : 03.01.2014, 22:59:34
    Author     : Pavel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.sociallibrary.constants.*" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="signin.css" rel="stylesheet">
        <title>TEMPLATE Page</title>
    </head>
    <body>
        <div class="container" >



            <!-- Static navbar -->
            <div class="navbar navbar-default" role="navigation">
                <div class="navbar-header">

                    <a class="navbar-brand" href="<%=Const.HOST%>" >Social Libary</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="index.jsp">Sign In</a></li>
                        <li><a href="Registration.jsp">Registration</a></li>
                        <li><a href="AddLibrary.jsp">Add</a></li>


                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li >  <form class="navbar-form navbar-left" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search">
                                </div>
                                <button type="submit" class="btn btn-primary" class="btn btn-default">Search</button>
                            </form></li>
                    </ul>


                </div><!--/.nav-collapse -->
            </div>
            <%
            session.setAttribute("role", 4);
            %>


                        <form class="form-signin" role="form"  action="Controller" method="POST" style="margin-top: 200px;">

                            <input type="hidden" name="command" value="signin"/>
                            <table border="0">
                                <tr><td colspan="4">
                                        <input type="text"  name="login" class="form-control" style="width:300px"placeholder="Login" onfocus="if (this.value == 'Login')
                                            {this.value = ''; this.style.color = '#000';}" onblur="
                                            if (this.value == '') {this.value = 'Login';
                                                this.style.color = '#777';}" required >
                                    </td></tr>
                                <tr><td colspan="4">
                                        <input type="password"  name="password" class="form-control" value="Password" onfocus="if (this.value == 'Password')
                                            {this.value = ''; this.style.color = '#000';}" onblur="
                                            if (this.value == '') {this.value = 'Password';
                                                this.style.color = '#777';}" placeholder="Password" required>
                                    </td></tr>
                                <tr><td colspan="4">
                                        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                                    </td></tr>
                                <tr>
                                    <td>
                                        <a href="https://www.facebook.com/dialog/oauth?client_id=<%=Const.FB_APP_ID%>&redirect_uri=<%=Const.HOST%>Controller?command=fbLogin&response_type=code">
                                            <img src="images/facebook.png"/>
                                        </a>
                                    </td>
                                    <td>
                  <a href="https://accounts.google.com/o/oauth2/auth?redirect_uri=<%=Const.HOST%>Controller?command=gLogin&response_type=code&client_id=<%=Const.G_APP_ID%>&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile">
                                            <img src="images/googleplus.png"/>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="#">
                                            <img src="images/twitter.png"/>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="http://oauth.vk.com/authorize?client_id=4114473&redirect_uri=<%=Const.HOST%>Controller?command=vkLogin&response_type=code" title="vk">
                                            <img src="images/vk.png"/>
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </form>
  



        </div>
    </body>
</html>
