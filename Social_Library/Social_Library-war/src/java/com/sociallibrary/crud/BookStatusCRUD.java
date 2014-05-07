package com.sociallibrary.crud;

import com.sociallibrary.entity.*;
import org.apache.log4j.*;
import com.sociallibrary.connection.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookStatusCRUD implements IBookStatusCRUD {

    private Connection connection;
    private static final String selectQuery = "SELECT * FROM Book_Status WHERE id=?";
    private static final String deleteQuery = "DELETE FROM Book_Status WHERE id =?";
    private static final String insertBookStatusQuery = "INSERT INTO Book_Status VALUES(BookStatus_id.nextval, ?)";
    private static final String updateBookStatusQuery = "UPDATE Book_Status SET Status =? where id=?";
    public static final Logger log = Logger.getLogger(BookStatusCRUD.class);

    public BookStatusCRUD()
    {
        connection = ConnectionProvider.getConnection();
    }

    public BookStatus readBookStatus(int id)
    {
        BasicConfigurator.configure();
        BookStatus bookStatus = new BookStatus();
        try {
            PreparedStatement stmt = connection.prepareStatement(selectQuery);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookStatus.setId(rs.getShort(1));
                bookStatus.setStatus(rs.getString(2));
            }
            rs.close();
            stmt.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return bookStatus;
    }

    public void createBookStatus(BookStatus BookStatus) {
        BasicConfigurator.configure();
        try {
            PreparedStatement pstmt = connection.prepareStatement(insertBookStatusQuery);

            pstmt.setString(1, BookStatus.getStatus());

            pstmt.executeUpdate();

            pstmt.close();

        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public void deleteBookStatus(BookStatus BookStatus) {
        BasicConfigurator.configure();
        try {
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);

            stmt.setInt(1, BookStatus.getId());

            stmt.executeUpdate();

            stmt.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public void updateBookStatus(BookStatus BookStatusNew, BookStatus BookStatusOld) {
        BasicConfigurator.configure();
        try {
            PreparedStatement pstmt = connection.prepareStatement(updateBookStatusQuery);

            pstmt.setString(1, BookStatusNew.getStatus());
            pstmt.setInt(2, BookStatusOld.getId());

            pstmt.executeUpdate();

            pstmt.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }
}
