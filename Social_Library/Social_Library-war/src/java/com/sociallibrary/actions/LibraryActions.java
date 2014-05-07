/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.actions;

import com.sociallibrary.entity.*;
import com.sociallibrary.crud.*;
import org.apache.log4j.*;
import com.sociallibrary.connection.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anton
 */
public class LibraryActions implements ILibraryActions


{

    public static String workflow = "workflow";
    public static String workflowInprogres = "1";
    public static int workflowPublished = 4;
    private static Connection connection;
    public static final Logger log = Logger.getLogger(LibraryActions.class);

    public LibraryActions()
    {
        connection = ConnectionProvider.getConnection();
    }

     public List<Library> getAllBooks(int from, int to)
             throws SQLException
     {
         List<Library> libraries = new ArrayList<Library>();
         for(int i = from; i<to; i++)
         {
            LibraryCRUD library=new LibraryCRUD();
            libraries.add(library.readLibrary(i));
            library=null;
         }
        return libraries;
    }

     public List<Author> getAuthorsOfBook(long book_id)
     {
         List<Author> authors = new ArrayList<Author>();
         BasicConfigurator.configure();
        String selectParametr = "select author.id from author " +
                                    "inner join book_author on book_author.author=author.id " +
                                    "inner join library on library.id=book_author.book " +
                                    "where library.id == ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, book_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Author author = new AuthorCRUD().readAuthor(rs.getInt("id"));
                authors.add(author);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return authors;
    }

     public boolean addBookToLocal(long book_id, long user_id)
     {
     try
     {
        Library book = new LibraryCRUD().readLibrary(book_id);
        User user = new UserCRUD().readUser(user_id);
        if(book!=null)
            if(user!=null){
                Catalog catalog = new Catalog();
                catalog.setBook(book);
                catalog.setUser(user);
                catalog.setStatus(new BookStatusCRUD().readBookStatus(0));
                catalog.setEventTime(new Timestamp(new java.util.Date().getTime()));
                new CatalogCRUD().createCatalog(catalog);

                return true;
            }
     }
     catch(SQLException e)
     {
         e.printStackTrace();
         log.error("SQLException:" + e);
     }
        return false;
    }

     public boolean checkStatus (long book_id, long user_id, int status)
     {
                 boolean result = true;
        BasicConfigurator.configure();
        String selectParametr = "SELECT id FROM CATALOG"+
                "WHERE status=? AND users=? AND book=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setInt(1, status);
            stmt.setLong(2, user_id);
            stmt.setLong(3, book_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                result = true;
            } else {
                result = false;
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }

        return result;
     }

     public boolean updateBookFromLocal(long book_id, long user_id, int status)
     {
        boolean result = true;
        BasicConfigurator.configure();
        String selectParametr = "UPDATE Catalog " +
                "SET STATUS=? " +
                "WHERE users=? AND book=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setInt(1, status);
            stmt.setLong(2, user_id);
            stmt.setLong(3, book_id);
            stmt.executeQuery();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
                result = false;
        }

        return result;
    }

     public boolean removeBookFromLocal(long book_id, long user_id)
     {
        boolean result = true;
        BasicConfigurator.configure();
        String selectParametr = "SELECT id FROM Catalog WHERE users=? AND book=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, user_id);
            stmt.setLong(2, book_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Catalog catalog = new CatalogCRUD().readCatalog(rs.getInt("id"));
                new CatalogCRUD().deleteCatalog(catalog);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
                result = false;
        }
        
        return result;
    }

     public boolean isBookInLocalLibraryOfUser(long book_id, long user_id)
     {
        boolean result = false;
        BasicConfigurator.configure();
        String selectParametr = "SELECT count(id) FROM Catalog WHERE users=? AND book=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, user_id);
            stmt.setLong(2, book_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1) > 0;
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
                result = false;
        }
        
        return result;
    }

     public String getBookAuthors(long book_id)
     {
        BasicConfigurator.configure();
        List<Author> authors = new ArrayList<Author>();
        String selectParametr = "select author.id from author " +
                                    "inner join book_author on book_author.author=author.id " +
                                    "inner join library on library.id=book_author.book " +
                                    "where library.id == ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, book_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Author author = new AuthorCRUD().readAuthor(rs.getInt("id"));
                authors.add(author);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        String result = "";
        for(Author a : authors) result+=a.getAuthor();
        return result;
    }

     public List<Library> getAllBooksByWorkflow(int workflow)
     {
        BasicConfigurator.configure();
        Library library = new Library();
        List<Library> libraries = new ArrayList<Library>();
        String selectParametr = "select id from library where workflow = ? order by id";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setInt(1, workflow);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                library = new LibraryCRUD().readLibrary(rs.getInt("id"));
                libraries.add(library);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }

        return libraries;
    }

     public long countAllBooksByWorkflow(int workflow)
     {
        BasicConfigurator.configure();
        long count = 0;
        String selectParametr = "select count(id) from library where workflow = ? order by id";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setInt(1, workflow);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getLong(1);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }

        return count;
    }

     public List<Library> searchBooksByAuthor(String author_name)
     {
        BasicConfigurator.configure();
        Library library = new Library();
        List<Library> libraries = new ArrayList<Library>();
        String selectParametr = "select library.id from library " +
                                    "inner join book_author on book_author.book=library.id " +
                                    "inner join author on author.id=book_author.author " +
                                    "where upper(author.author) like upper('%'||?||'%');";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setString(1, author_name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                library = new LibraryCRUD().readLibrary(rs.getLong("id"));
                libraries.add(library);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
       
        return libraries;
    }

     public List<Library> searchBooksByGenre(String genre)
     {
        BasicConfigurator.configure();
        Library library = new Library();
        List<Library> libraries = new ArrayList<Library>();
        String selectParametr = "select library.id from library " +
                                    "inner join book_genre on book_genre.book=library.id " +
                                    "inner join genre on genre.id=book_genre.genre " +
                                    "where upper(genre.genre) like upper('%'||?||'%')";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                library = new LibraryCRUD().readLibrary(rs.getLong("id"));
                libraries.add(library);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return libraries;
    }

     public List<Library> searchBooksByTitle(String title)
     {
        BasicConfigurator.configure();
        Library library = new Library();
        List<Library> libraries = new ArrayList<Library>();
        String selectParametr = "select id from library where upper(title) like upper(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setString(1, "%"+title+"%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                library = new LibraryCRUD().readLibrary(rs.getLong("id"));
                libraries.add(library);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return libraries;
    }

     public List<Library> searchBooksByDescription(String description)
     {
        BasicConfigurator.configure();
        //Library library = new Library();
        List<Library> libraries = new ArrayList<Library>();
        String selectParametr = "select id from library where upper(description) like upper(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setString(1, "%"+description+"%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Library library = new LibraryCRUD().readLibrary(rs.getLong("id"));
                libraries.add(library);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return libraries;
    }

     public List<Library> getAllLocalBooksByUser(long user_id)
     {
        BasicConfigurator.configure();
        Library library = new Library();
        List<Library> libraries = new ArrayList<Library>();
        String selectParametr = "SELECT Book FROM Catalog WHERE users=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                //System.out.println("!@# "+rs.getLong("id"));
                library = new LibraryCRUD().readLibrary(rs.getInt("book"));
                libraries.add(library);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }

        return libraries;
    }

     public long countAllLocalBooksByUser(long user_id)
     {
        BasicConfigurator.configure();
        long count = 0;
        String selectParametr = "SELECT count(Book) FROM Catalog WHERE users=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, user_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getLong(1);
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }

        return count;
    }

    public List<Library> searchBooksByParameter(String where, String what) 
    {
        BasicConfigurator.configure();
        Library library = new Library();
        List<Library> libraries = new ArrayList<Library>();
        String selectParametr = "select id  from library where "+where+" = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setString(1, what);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                 ILibraryCRUD ilibrary = new LibraryCRUD();
                library = ilibrary.readLibrary(rs.getInt("id"));
                libraries.add(library);
            }
            rs.close();
            stmt.close();
        } 
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(LibraryActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        
        return libraries;
    }

    public int countBooksByParameter(String where, String what)
    {
        BasicConfigurator.configure();
        int result = 0;
        String selectParametr = "select count(id) PARAM from library where "+where+" = "+what;
        //String selectParametr = "select count(id) \"param\" from library where ? = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
//            stmt.setString(1, where);
//            stmt.setInt(2, what);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("PARAM");
            }
            rs.close();
            stmt.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return result;
    }
    
    public List<Library> searchBooksByStringMask(String where, String what)
    {
        BasicConfigurator.configure();
        Library library = new Library();
        List<Library> libraries = new ArrayList<Library>();
        String selectParametr = "select *  from library where "+where+" like '"+what+"'";
        try {
             PreparedStatement stmt = connection.prepareStatement(selectParametr);
//            stmt.setString(1, where);
//            stmt.setString(2, what);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                library = new LibraryCRUD().readLibrary(rs.getInt("id"));
                libraries.add(library);
            }
            rs.close();
            stmt.close();
        } 
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
       
        return libraries;
    }

    public List<Author> getAuthorsList(long bookId)
    {
        BasicConfigurator.configure();
        List<Author> authors = new ArrayList<Author>();
        String selectParametr = "select * from book_author where book = ?";
        try {

            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                authors.add(new AuthorCRUD().readAuthor(rs.getInt("author")));
            }
            rs.close();
            stmt.close();
        } 
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return authors;
    }

    public List<Rating> getRatingsList(long bookId)
    {
        BasicConfigurator.configure();
        List<Rating> rating = new ArrayList<Rating>();
        String selectParametr = "select *  from rating where book= ?";
        try {

            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rating.add(new RatingCRUD().readRating(rs.getInt("id")));
            }
            rs.close();
            stmt.close();
        } 
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
       
        return rating;
    }

    public int getAverageRate(long bookId)
    {
        int rate=0;
        List<Rating> ratings = getRatingsList(bookId);
        for(Rating rating1 : ratings)
            rate+=rating1.getRate();

        if(ratings.size()>0)
            return rate/ratings.size();
        return 0;
    }

    public List<Library> BooksList(int from, int to, int workflow) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void AddToLocal(long book_id, long user_id) {
        try {
            Library lib = new LibraryCRUD().readLibrary(book_id);
            User user = new UserCRUD().readUser(user_id);
            lib.setUser(user);
            new LibraryCRUD().createLibrary(lib);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LibraryActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void RemoveFromLocal(long book_id, long user_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean CheckLocal(long book_id, long user_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
       public boolean changeWorkflow(int workflow, String id)  {
        boolean check = true;

        BasicConfigurator.configure();

        String updateworkflow = "update  Library SET WORKFLOW=? where ID = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(updateworkflow);
            stmt.setInt(1, workflow);
            stmt.setString(2, id);
            ResultSet rs = stmt.executeQuery();

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQLException:" + e);
            check = false;
        }
        finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(LibraryActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }


        return check;

    }


     
}
