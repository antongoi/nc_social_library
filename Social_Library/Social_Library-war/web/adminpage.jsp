<%-- 
    Document   : adminpage
    Created on : 8/1/2014, 4:58:11
    Author     : Антон
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<%@page import="com.sociallibrary.entity.*" %>
<%@page import="com.sociallibrary.icrud.*"%>
<%@page import="com.sociallibrary.crud.*"%>
<%@page import="com.sociallibrary.iactions.*" %>
<%@page import="com.sociallibrary.actions.*" %>
<%@page import="com.sociallibrary.model.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" type="text/css" rel="stylesheet" />

        <title>Global Library</title>
    </head>
    <body>

        <div id="container">

            <div id="header" align="center"><h1>User management page</h1></div>


<div id="wrapper">

<div id="content">
    <%
        User current_user = (User)request.getSession().getAttribute("user");
        AdminPage bizLog = new AdminPage();
        //List<User> users = bizLog.getAllUsers();

        int count_of_pages = 0;//new LibraryActions().countBooksByParameter("WORKFLOW", "4")/10+1;
        List<User> users = (List<User>) request.getAttribute("users_list");
        count_of_pages = (Integer) request.getAttribute("count_of_pages");
        int i=0;
        try {
            i = Integer.parseInt(request.getParameter("page"));
        }
        catch(NumberFormatException e) {
            e.printStackTrace();
        }
        i=i<1?1:i>count_of_pages?count_of_pages-1:i;
/*        boolean isSearch = Boolean.valueOf(request.getParameter("search"));
        if(isSearch)
            users = bizLog.getSearch_users_result();
        else
            users = bizLog.getAllUsers();
*/
        for(User user : users){
    %>
    <table style="width:500px;" cellpadding="0" cellspacing="0" border="1" align="center">
        <tr><td width="70%">
                <table style="margin:10px;" width="90%" cellpadding="0" cellspacing="0" border="0" align="center">
                <tr bgcolor="#ffffd0">
                    <td>First name:&nbsp;</td>
                    <td><%=user.getFirstName()%></td>
                </tr>
                <tr>
                    <td>Last name:&nbsp;</td>
                    <td><%=user.getLastName()%></td>
                </tr>
                <tr bgcolor="#ffffd0">
                    <td>E-mail:&nbsp;</td>
                    <td><%=user.getEmail()%></td>
                </tr>
                <tr>
                    <td>Login:&nbsp;</td>
                    <td><%=user.getLogin()%></td>
                </tr>
                <tr bgcolor="#ffffd0">
                    <td>Gender:&nbsp;</td>
                    <td><%=user.getGender()%></td>
                </tr>
                <tr>
                    <td>Registration date:&nbsp;</td>
                    <td><%=user.getRegistrationDate()%></td>
                </tr>
                <tr bgcolor="#ffffd0">
                    <td>Confirmed:&nbsp;</td>
                    <td><%=user.isConfirmed()?"yes":"no"%></td>
                </tr>
                <tr>
                    <td>Banned:&nbsp;</td>
                    <td><%=user.isBanned()?"yes":"no"%></td>
                </tr>
                <tr bgcolor="#ffffd0">
                    <td>Notify:&nbsp;</td>
                    <td><%=user.isNotify()?"yes":"no"%></td>
                </tr>
            </table>
        </td>
        <td style="padding:10px;" width="">
            <form name="assignNewRole" method="post" action="Controller">
                <input type="hidden" name="user_id" value="<%=user.getId()%>"/><br/>
                <input type="hidden" name="command" value="assignrole"/><br/>
                <input name="administrator" type="checkbox" value="<%=bizLog.isAdmin(user)?"1":"0"%>" <%=bizLog.isAdmin(user)?"checked":""%> onclick="this.checked=<%=bizLog.isAdmin(user)?"true":"false"%>"/>Administrator<br/>
                <input name="moderator" type="checkbox" value="<%=bizLog.isModerator(user)?"1":"0"%>" <%=bizLog.isModerator(user)?"checked":""%> onclick="this.value=this.checked?1:0"/>Moderator<br/>
                <input name="advancedUser" type="checkbox" value="<%=bizLog.isAdvancedUser(user)?"1":"0"%>" <%=bizLog.isAdvancedUser(user)?"checked":""%> onclick="this.value=this.checked?1:0"/>Advanced user<br/>
                <input name="beginnerUser" type="checkbox" value="<%=bizLog.isBeginnerUser(user)?"1":"0"%>" <%=bizLog.isBeginnerUser(user)?"checked":""%> onclick="this.value=this.checked?1:0"/>Beginner user<br/>
                <input align="right" type="submit" value="Assign roles"/>
            </form>
        </td></tr>
    </table>
    <%
        }
    %>

    <center>
        <%
        int min_page = i-4;
        min_page = min_page>0?min_page:1;
        int max_page = i+4;
       // int count_of_pages = new LibraryActions().countBooksByParameter("WORKFLOW", "4")/10+1;
        max_page = max_page<count_of_pages?max_page:count_of_pages;
        for(int k = min_page; k<max_page+1; k++)
        {
            String linkStyle="";
            if(k==i) linkStyle="text-decoration:none; color:#007700;";
        %>
        <a style="<%=linkStyle%>" href="Controller?command=adminpage&page=<%=k%>"><b><%=k%></b></a>
        <%
        }
        %>
        </center>
</div>

      

<div id="rightblock">
    <p>
       <form name="form1" method="post" action="Controller">
        <input type="text" name="search_users" value="">
        <input type="hidden" name="command" value="searchusers"/>
        <input type="submit" value="Search">
       </form>
    </p>
</div>

<div id="footer"><p>Blue One</p></div>

</div>

        </div>
    </body>
</html>
