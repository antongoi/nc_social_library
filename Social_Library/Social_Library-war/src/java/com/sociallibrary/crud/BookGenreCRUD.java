/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.crud;

import com.sociallibrary.entity.*;
import org.apache.log4j.*;
import com.sociallibrary.connection.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mazafaka
 */
public class BookGenreCRUD implements IBookGenreCRUD{
    private Connection connection;
    private PreparedStatement insertStmt;
    private Library book;
    private Genre genre;
    public static final Logger log = Logger.getLogger(BookGenreCRUD.class);

    public BookGenreCRUD()
            throws SQLException
    {
        connection = ConnectionProvider.getConnection();
        book=new Library();
        genre=new Genre();
        insertStmt=connection.prepareStatement("INSERT INTO BOOK_GENRE (BOOK,GENRE) " +
                "values(?,?)");
    }

    public void createBookGenre(BookGenre bookgenre)
    {
        BasicConfigurator.configure();
        try {
            insertStmt.setLong(1, book.getId());
            insertStmt.setLong(2, genre.getId());
            insertStmt.executeUpdate();
            insertStmt.close();

        }
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
    }
}
