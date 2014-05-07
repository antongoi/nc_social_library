<%-- 
    Document   : GlobalLibary
    Created on : 5 січ 2014, 0:39:48
    Author     : Pavlova Nastya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<%@page import="com.sociallibrary.entity.*" %>
<%@page import="com.sociallibrary.crud.*"%>
<%@page import="com.sociallibrary.actions.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" type="text/css" rel="stylesheet" />
       
        <title>Global Library</title>
        <script language="javascript">
            function radio_click(n, book_id){
                for(var i = 1; i<6; i++){
                    if(i<=n) document.getElementById("radio_rate_"+i+"_"+book_id).checked=true;//setAttribute("checked", "");
                    else document.getElementById("radio_rate_"+i+"_"+book_id).checked=false; //document.getElementById('radio_rate_'+i).attributes.removeAttribute("checked");
                }
                document.getElementById("rate_value"+"_"+book_id).setAttribute("value", n);
            }
        </script>
    </head>
    <body>

        <div id="container">

            <div id="header" align="center"><h1>Global Library</h1></div>


<div id="wrapper">

<div id="content">
    <p>
    <table border="1" align="center">
        <tbody>
        <tr>
        <th>ISBN:</th>
        <th>Title:</th>
        <th>Cover:</th>
        <th>Description:</th>
        <th>Pages:</th>
        <th>Rating:</th>
        </tr>
        <%
        int i=1;
        try
        {
        i = Integer.parseInt(request.getParameter("i"));
        }
        catch(NumberFormatException e)
        {
           e.printStackTrace();
        }
        LibraryActions ob=new LibraryActions();
        List<Library> libraries = ob.BooksList(i*10, (i+1)*10,4);

        for(Library book:libraries)
        {
        %>
        <tr>
        <td><%=book.getIsbn()%></td>
        <td><%=book.getTitle()%></td>
        <td><%=book.getCover()%></td>
        <td><%=book.getDescription()%></td>
        <td><%=book.getPages()%></td>
        </tr>
        <%
        }
        %>
        </tbody>
        </table>
        </p>
        <center>
        <%
        int min_page = i-4;
        min_page = min_page>0?min_page:1;
        int max_page = i+4;
        int count_of_pages = new LibraryActions().countBooksByParameter("WORKFLOW", "4")/10+1;
        max_page = max_page<count_of_pages?max_page:count_of_pages;
        for(int k = min_page; k<max_page; k++)
        {
            String linkStyle="";
            if(k==i) linkStyle="text-decoration:none; color:#007700;";
        %>
        <a style="<%=linkStyle%>" href="?i=<%=k%>"><b><%=k%></b></a>
        <%
        }
        %>
        </center>
</div>

</div>


<div id="leftblock">
    <br>
    <a href="locallib.jsp?id=<%=request.getParameter("id")%>"> Local library </a>
    <br><a href="dashboard.jsp?id=<%=request.getParameter("id")%>">Dashboard Publish</a>
    <br><a href="dashboardApp.jsp?id=<%=request.getParameter("id")%>">Dashboard Approve </a>
    

</div>

<div id="rightblock">
    <p><form name="form1" method="post" action="AddLibrary">
        <input type="text" name="genre">
	<input name="search" type="button" value="Search">
        </form>
   </p>

</div>

<div id="footer"><p>Blue One</p></div>

</div>
       
        
    </body>
</html>
