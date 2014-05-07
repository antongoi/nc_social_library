/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.crud;

import com.sociallibrary.entity.*;
import org.apache.log4j.*;
import com.sociallibrary.connection.ConnectionProvider;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author mazafaka
 */
public class BookAuthorCRUD implements IBookAuthorCRUD
{
    private PreparedStatement insertStmt;
    private Connection connection;
    Library book;
    Author author;
    public static final Logger log = Logger.getLogger( BookAuthorCRUD.class);

    public BookAuthorCRUD()
            throws SQLException
    {
        connection = ConnectionProvider.getConnection();
        book=new Library();
        author=new Author();
        insertStmt=connection.prepareStatement("INSERT INTO BOOK_AUTHOR (BOOK,AUTHOR) " +
                "values(?,? )");
    }

    public void createBookAuthor(BookAuthor bookauthor)
    {
        BasicConfigurator.configure();
        try
        {
            insertStmt.setLong(1,book.getId());
            insertStmt.setLong(2, author.getId());
            insertStmt.executeUpdate();

            insertStmt.close();

        }
        catch (SQLException e)
        {
            log.error("SQLException:" + e);
            e.printStackTrace();
        }

    }
}
