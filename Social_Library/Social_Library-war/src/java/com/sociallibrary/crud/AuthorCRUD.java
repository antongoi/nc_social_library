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
 * @author Nastya Pavlova
 */
public class AuthorCRUD implements IAuthorCRUD {

    private Connection connection;
    public static final Logger log = Logger.getLogger(AuthorCRUD.class);
    private static final String selectQuery = "SELECT * FROM author WHERE id=?";
    private static final String deleteQuery = "DELETE FROM author WHERE id =?";
    private static final String updateAuthorQuery = "UPDATE author SET author=? WHERE id=?";
    private PreparedStatement insertStmt;
    
    public  AuthorCRUD()
            throws SQLException
    {
        connection = ConnectionProvider.getConnection();
        insertStmt=connection.prepareStatement("INSERT INTO AUTHOR (AUTHOR) " +
                "values('?' )");
    }

    public void createAuthor(Author author) {
        BasicConfigurator.configure();
            try {
                insertStmt.setString(1, author.getAuthor());
                insertStmt.executeUpdate();
                insertStmt.close();
            }

          catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public Author readAuthor(int id)
    {
        BasicConfigurator.configure();
        Author author = new Author();
            try
            {
                PreparedStatement stmt = connection.prepareStatement(selectQuery);

                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();

                while (rs.next())
                {
                    author.setId(rs.getLong(1));
                    author.setAuthor(rs.getString(2));
                }
                rs.close();
                stmt.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                log.error("SQLException:" + e);
            }
            
        return author;
    }

    public void updateAuthor(Author authorOld, Author authorNew)
    {
        BasicConfigurator.configure();
            try
            {
                PreparedStatement pstmt = connection.prepareStatement(updateAuthorQuery);

                pstmt.setString(1, authorNew.getAuthor());
                pstmt.setLong(2, authorOld.getId());

                pstmt.executeUpdate();

                pstmt.close();
            }
             catch (SQLException e)
            {
                e.printStackTrace();
                log.error("SQLException:" + e);
            }
            
    }

    public void deleteAuthor(Author author)
    {
        BasicConfigurator.configure();
            try
            {
                PreparedStatement stmt = connection.prepareStatement(deleteQuery);

                stmt.setLong(1, author.getId());

                stmt.executeUpdate();

                stmt.close();
            }
             catch (SQLException e)
            {
                e.printStackTrace();
                log.error("SQLException:" + e);
            }
            
    }
} 
