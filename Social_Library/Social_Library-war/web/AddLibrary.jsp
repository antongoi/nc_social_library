<%--
    Document   : AddLibrary
    Created on : 17 січ 2014, 13:57:30
    Author     : mazafaka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.google.api.client.googleapis.javanet.GoogleNetHttpTransport "%>
<%@page import="com.google.api.client.json.JsonFactory"%>
<%@page import="com.google.api.client.json.jackson2.JacksonFactory"%>
<%@page import="com.google.api.services.books.Books"%>
<%@page import="com.google.api.services.books.BooksRequestInitializer"%>
<%@page import="com.google.api.services.books.Books.Volumes.List"%>
<%@page import="com.google.api.services.books.model.Volume"%>
<%@page import=" com.google.api.services.books.model.Volumes"%>
<%@page import="java.io.IOException"%>
<%@page import="com.sociallibrary.entity.*" %>
<%@page import="com.sociallibrary.crud.*"%>
<%@page import="com.sociallibrary.actions.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
          <div id="content">
              Maybe you meaned:
              <br>
              <script>
              function handleResponse(response) {
              for (var i = 0; i < response.items.length; i++) {
              var item = response.items[i];
              document.getElementById("content").innerHTML += "<br>" + item.volumeInfo.title ;
              }
              }
              </script>
              <script src="https://www.googleapis.com/books/v1/volumes?q=<%=request.getParameter("title")%>&callback=handleResponse"></script>
          </div>


        <form action="AddLibrary.jsp" method="POST">
            <p>Title||Author||ISBN:
            <input type="text" name="title"/>
            </p>
            <button type="submit">Search</button>
            </form>
        <table border="1" align="center">
        <tbody>
        <tr>
        <th>Title:</th>
        <th>ISBN_10:</th>
        <th>ISBN_13:</th>
        <th>Pages:</th>
        <th>Genre</th>
        <th>Cover:</th>
        <th>Author</th>
        <th>Description:</th>
        <th>Add:</th>
        </tr>
        <tr>
    <%
       String APPLICATION_NAME = "Social_Library";
       String API_KEY ="AIzaSyCZsI9e4CfhOOOKQrBXaYB3OkXdu_Qq-3Q";

       JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
       String prefix = null;
       String query = "--title:"+" "+request.getParameter("title");
       if ("--title".equals(query))
        {
          prefix = "intitle:";
        }
        else if ("--author".equals(query))
        {
          prefix = "inauthor:";
        }
       else if ("--isbn".equals(query))
        {
          prefix = "isbn:";
        }
        if (prefix != null)
        {
        query = prefix + query;
        }
        try
        {
        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
        .setApplicationName(APPLICATION_NAME)
        .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
        .build();

        List volumesList = books.volumes().list(query);
        Volumes volumes = volumesList.execute();

        for (Volume volume : volumes.getItems())
        {
            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            %>
            <td><%=volumeInfo.getTitle() %></td>
            <td><%=volume.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier() %></td>
            <td><%=volume.getVolumeInfo().getIndustryIdentifiers().get(1).getIdentifier() %></td>
            <td><%=volumeInfo.getPageCount() %></td>
            <td><%=volumeInfo.getCategories() %></td>
            <td><img src="http://bks3.books.google.com/books?id=<%=volume.getId()%>&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api/SmallThumbnail.jpg%>"/></td>
            <td>
            <%
            java.util.List<String> authors = volumeInfo.getAuthors();
            if (authors != null && !authors.isEmpty())
            {
                for (int i = 0; i < authors.size(); ++i)
                {
                    %>
                    <%=authors.get(i)%>
                    <%
                }
            }
            if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0)
            {
                %>
                </td><td><%=volumeInfo.getDescription() %></td>

                <%

            }
            %>
            </tr>
            <%
            //code for add book to library
            ILibraryCRUD ob=new LibraryCRUD();
            Library library=new Library();
            User user=new User();
            IUserCRUD ob1=new UserCRUD();

            //install values for book workflow
            BookWorkflow workflow=new BookWorkflow();
            IBookWorkflowCRUD ob2=new BookWorkflowCRUD();
            workflow.setId((int)library.getId());
            workflow.setWorkflow("New");
            ob2.createBookWorkflow(workflow);

            library.setTitle(volumeInfo.getTitle());
            library.setIsbn(volume.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier());
            library.setCover("http://bks3.books.google.com/books?id=volume.getId()&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api/SmallThumbnail.jpg");
            library.setDescription(volumeInfo.getDescription());
            library.setPages(volumeInfo.getPageCount());
            library.setUser(ob1.readUser(user.getId()));
            library.setWorkflow(ob2.readBookWorkflow(workflow.getId()));
            ob.createLibrary(library);
            //set author
            Author author=new Author();
            IAuthorCRUD ob3=new AuthorCRUD();
            author.setAuthor(authors.get(0));
            ob3.createAuthor(author);

            IBookAuthorCRUD ob4=new BookAuthorCRUD();
            BookAuthor bookauthor=new BookAuthor();
            bookauthor.setBook_id(library.getId());
            bookauthor.setAuthor_id(author.getId());
            ob4.createBookAuthor(bookauthor);
            //set genre
            Genre genre=new Genre();
            IGenreCRUD ob5=new GenreCRUD();
            genre.setGenre(volumeInfo.getCategories().get(0));
            ob5.createGenre(genre);

            IBookGenreCRUD ob6=new BookGenreCRUD();
            BookGenre bookgenre=new BookGenre();
            bookgenre.setBook_id(library.getId());
            bookgenre.setGenre_id(genre.getId());
            ob6.createBookGenre(bookgenre);

            return;

        }

        }
        catch (IOException e)
        {
        System.err.println(e.getMessage());
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    %>

    </tbody>
        </table>

    </body>
</html>