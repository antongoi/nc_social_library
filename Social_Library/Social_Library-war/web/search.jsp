<%--
    Document   : newjsp
    Created on : 5 січ 2014, 0:39:48
    Author     : mazafaka
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

        <title>Search results</title>
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

            <div id="header" align="center"><h1>Search results</h1></div>


<div id="wrapper">

<div id="content">
    <p>
        <%
        User current_user = (User) request.getSession().getAttribute("user");
        long user_id = current_user.getId();
/*        User current_user = new UserCRUD().readUsers(5);
        long user_id = 5;
*/        int count_of_pages = new LibraryActions().countBooksByParameter("WORKFLOW", "4")/10+1;
        List<Library> all_required_books = (List<Library>) request.getSession().getAttribute("founded_books");
        //request.getSession().removeAttribute("founded_books");
        count_of_pages = all_required_books.size()/10+1;
        int i=0;
        try {
            i = Integer.parseInt(request.getParameter("i"));
        }
        catch(NumberFormatException e) {
            e.printStackTrace();
        }
        i=i<1?1:i>count_of_pages?count_of_pages-1:i;

        int from = (i-1)*10;
        int to = i*10;
        to=to<all_required_books.size()?to:all_required_books.size();
        List<Library> libraries = all_required_books.subList(from, to);
        //List<Library> libraries = ob.getBooksByIdInInterval(10*i, 10*(i+1));
        out.println("Hello, "+current_user.getFirstName()+"!");
        for(Library book:libraries)
        {
           //if(book.getWorkflow().getId()!=4) continue;
        %>
    <table cellpadding="0" cellspacing="0" width="300" border="1" align="center">
        <tr><td>
                <table cellpadding="0" cellspacing="0" border="0" align="left">
                <tr>
                    <td><%=book.getTitle()%>&nbsp;&nbsp;&nbsp;<%=book.getPages()%>&nbsp;pages</td>
                </tr>
                <tr>
                    <td><%=new LibraryActions().getBookAuthors(book.getId())%></td>
                </tr>
            </table>
        </td></tr>
        <tr><td>
            <table cellpadding="2" cellspacing="2" border="0" align="left">
                <tr>
                    <td width="30%"><img src="images/book_cover.png" alt="COVER" width="50"></td>
                    <td width="70%" valign="top"><%=book.getDescription()%></td>
                </tr>
            </table>
        </td></tr>
        <tr><td>
            <table cellpadding="0" cellspacing="0" border="0" align="left">
                <tr>
                    <td width="50%">
                        <form name="rateBook" method="post" action="Controller">
                            <input type="hidden" name="book_id" value="<%=book.getId()%>"/>
                            <input type="hidden" name="user_id" value="<%=user_id%>"/>
                            <input type="hidden" id="rate_value_<%=book.getId()%>" name="rate" value=""/>
                            <input type="hidden" name="redirect" value="globallib.jsp"/>
                            <input type="hidden" name="command" value="rating"/>
                            <div style="z-index:1; width:125px; position:absolute;">
                            <%
                            float average_rate = new RatingActions().getAverageRatingByBookId(book.getId());
                            int my_rating = new RatingActions().getRatingByBookAndUserId(book.getId(), user_id).getRate();
                            String checked = "";
                                for(int r = 1; r<6; r++){
                                    if(r < my_rating+1) checked = "checked";
                                 //   out.println("<input onclick=\"radio_click("+r+")\" id=\"radio_rate_"+r+"\" name=\"radio_rate_"+
                                   //     r+"\" type=\"radio\" value=\""+r+"\"" +checked+ "/>");
                            %>
                            <input onclick="radio_click(<%=r%>,<%=book.getId()%>)" id="radio_rate_<%=r%>_<%=book.getId()%>" name="radio_rate_<%=r%>_<%=book.getId()%>" type="radio" value="<%=r%>" <%=checked%> />
                     <%--       <input onclick="radio_click(1)" id="radio_rate_1" name="radio_rate_1" type="radio" value="1" checked />
                            <input onclick="radio_click(2)" id="radio_rate_2" name="radio_rate_2" type="radio" value="2" />
                            <input onclick="radio_click(3)" id="radio_rate_3" name="radio_rate_3" type="radio" value="3" />
                            <input onclick="radio_click(4)" id="radio_rate_4" name="radio_rate_4" type="radio" value="4" />
                            <input onclick="radio_click(5)" id="radio_rate_5" name="radio_rate_5" type="radio" value="5" />
                     --%>   <%
                                    checked = "";
                                }
                            %>
                            </div>
                            <div style="background-color:green; height:20px; position:relative; z-index:0; width:<%=Math.round((120/5)*average_rate)%>px;">&nbsp;</div>
                            <input name="float_value" value="<%=/*Math.round(*/average_rate/**100)/100.0*/%>" type="text" readonly style="border:0px; width:50px;" />
                            <input align="right" type="submit" value="   Ok   " />
                        </form>
                    </td>
                    <td width="50%">
                        <form name="addToLocal" method="post" action="Controller">
                            <input type="hidden" name="book_id" value="<%=book.getId()%>"/>
                            <input type="hidden" name="user_id" value="<%=user_id%>"/>
                            <input type="hidden" name="command" value="addtolocal"/>
                            <input align="right" type="submit" value="Add to my library" />
                        </form>
                    </td>
                </tr>
            </table>
        </td></tr>
    </table>
    <%
    }
    %>
</p>
      <center>
        <%
        int min_page = i-4;
        min_page = min_page>0?min_page:1;
        int max_page = i+4;
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
    <br/>
    <a href="locallib.jsp"> Local library </a><br/><br/>
    <a href="globallib.jsp">Global library</a><br/><br/>
    <%
        boolean isModerator = false;
        boolean isAdvanced = false;
        for(Role r : current_user.getRoles())
            if(r.getId()==1) isModerator = true;
            else if(r.getId()==2) isAdvanced = true;
    %>

    <%
        if(isAdvanced||isModerator){
    %>
    <a href="dashboard.jsp">Dashboard Publish</a><br/><br/>
    <%
        }
        if(isModerator){
    %>
    <a href="dashboardApp.jsp">Dashboard Approve </a>
    <%
        }
    %>
    <%
        if(new AdminPage().isAdmin(current_user)){
    %>
    <br/><br/><a href="adminpage.jsp">Admin page</a>
    <%
        }
    %>


</div>

<div id="rightblock">

</div>

<div id="footer"><p>Blue One</p></div>

</div>


    </body>
</html>