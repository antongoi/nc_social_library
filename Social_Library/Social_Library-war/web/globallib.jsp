<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<%@page import="com.sociallibrary.entity.*" %>
<%@page import="com.sociallibrary.crud.*"%>
<%@page import="com.sociallibrary.actions.*" %>
<%@page import="com.sociallibrary.model.*" %>





<table   class="table table-hover">
    <thead>
        <tr>
            <th>ISBN</th>
            <th>TITLE</th>
            <th>DISCRIPTION</th>
            <th>PAGES</th>
            <th>RATING</th>
            <th>MY RATING</th>
            <th>ADD TO LOCAL</th>

        </tr>
    </thead>
    <tbody>
        <%
            User current_user = (User) request.getSession().getAttribute("user");
            long user_id = current_user.getId();
            List<Library> lib = new LibraryActions().searchBooksByParameter("workflow", "4");
            for (Library temp : lib) {
        %>
        <tr>
            <td><%out.print(temp.getIsbn());%></td>
            <td><%out.print(temp.getTitle());%></td>
            <td><%out.print(temp.getDescription());%></td>
            <td><%out.print(temp.getPages());%></td>
            <td>
                <%out.print(new RatingActions().getAverageRatingByBookId(temp.getId()));%>
            </td>
            <td>
                <form action="Controller" method="POST">
                    <input type="hidden" name="command" value="rating">
                    <input type="hidden" name="book" value="<%out.print(temp.getId());%>" />
                    <input type="hidden" name="user" value="<%out.print(user_id);%>"/>
                    <input type="radio" name="rate" value="1" onclick="this.form.submit()">
                    <input type="radio" name="rate" value="2" onclick="this.form.submit()">
                    <input type="radio" name="rate" value="3" onclick="this.form.submit()">
                    <input type="radio" name="rate" value="4" onclick="this.form.submit()">
                    <input type="radio" name="rate" value="5" onclick="this.form.submit()">
                </form>
            </td>
            <td>
                <form action="Controller" method="POST">
                <input type="hidden" name="command" value="add">
                <input type="hidden" name="book" value="<%out.print(temp.getId());%>" />
                <input type="hidden" name="user" value="<%out.print(user_id);%>"/>
                <input type="submit" class="btn btn-success" value="ADD!" >
                </form>
            </td>
        </tr>
        <%}%>
    </tbody>
</table>
