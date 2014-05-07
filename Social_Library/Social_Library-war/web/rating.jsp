<%-- 
    Document   : new
    Created on : 22.12.2013, 21:33:33
    Author     : П
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Rating Page</h1>
         
        <form name="forma" action="Controller" method="POST">
           
            <select name="mark">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
             <input type="submit" value="OK" name="submit" />
             <input type="hidden" value="rating" name="command" />
            
        </form>
        <hr>
        <jsp:expression>( request.getParameter("mark")!=null)?
(String)request.getParameter("mark"):"you have not made your choice yet!"
        </jsp:expression>
        <hr>
        <% 
            out.print(session.getAttribute("name"));
        
        %>
        
       <hr>
       
    </body>
</html>
