<%--
    Document   : menu_advanced
    Created on : 11.01.2014, 22:16:20
    Author     : П
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="signin.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>


        <div class="container">



            <!-- Static navbar -->
            <div class="navbar navbar-default" role="navigation">
                <div class="navbar-header">

                    <a class="navbar-brand" href="mod_global.jsp" >Social Library</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                         <li><a href="Controller?command=nocommand">Pending to approve</a></li>
                        <li><a href="Controller?command=dashboard">Dashboard</a></li>
                        <li><a href="mod_add.jsp">Add</a></li>
                        <li><a href="mod_mylibrary.jsp">My library</a></li>

                    </ul>


                    <ul class="nav navbar-nav navbar-right">

                        <li > <form class="navbar-form navbar-left" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search">
                                </div>
                                <button type="submit" class="btn btn-primary" class="btn btn-default">Search</button>
                            </form></li>
                        <li ><a href= "index.jsp">Sign out</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        