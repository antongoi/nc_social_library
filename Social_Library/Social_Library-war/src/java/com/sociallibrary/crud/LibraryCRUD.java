/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sociallibrary.connection.ConnectionProvider;
import com.sociallibrary.entity.*;
import org.apache.log4j.*;

/**
 *
 * @author Pavlova Nastya
 */

public class LibraryCRUD implements ILibraryCRUD {

    private Connection connection;
    //private PreparedStatement selectStmt;
    private PreparedStatement insertStmt;
    //private PreparedStatement updateStmt;
   // private PreparedStatement deleteStmt;
    private User user;
    private BookWorkflow workflow;

    public static final Logger log = Logger.getLogger(LibraryCRUD.class);

    public LibraryCRUD() throws SQLException
    {
        connection = ConnectionProvider.getConnection();
        user=new User();
        workflow=new BookWorkflow();
        insertStmt=connection.prepareStatement("INSERT INTO LIBRARY (ISBN,TITLE,COVER,DESCRIPTION,PAGES,USER,WORKFLOW) " +
                "values(LIBRARY_ID.nextval,'?','?','?','?',?, ?, ?  )");
    }
    
    public void createLibrary(Library library)
    {
        BasicConfigurator.configure();
        try 
        {
            insertStmt.setString(1, library.getIsbn());
            insertStmt.setString(2, library.getTitle());
            insertStmt.setString(3, library.getCover());
            insertStmt.setString(4, library.getDescription());
            insertStmt.setInt(5, library.getPages());
            insertStmt.setLong(6,user.getId() );
            insertStmt.setInt(7, workflow.getId());
            insertStmt.executeUpdate();

            insertStmt.close();

        }
        catch (SQLException e)
        {
            log.error("SQLException:" + e);
            e.printStackTrace();
        }
        
    }

    public Library readLibrary(long id)
    {
         BasicConfigurator.configure();
         ResultSet rs=null;
         Library library = new Library();
         try
         {
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Library where id= ?");
             stmt.setLong(1, id);

             rs = stmt.executeQuery();

             if (rs.next())
             {
                library.setId(rs.getLong("ID"));
                library.setIsbn(rs.getString("ISBN"));
                library.setTitle(rs.getString("TITLE"));
                library.setCover(rs.getString("COVER"));
                library.setDescription(rs.getString("DESCRIPTION"));
                library.setPages(rs.getInt("PAGES"));
                library.setUser(new UserCRUD().readUser(rs.getLong("USERS")));
                library.setWorkflow(new BookWorkflowCRUD().readBookWorkflow(rs.getInt("WORKFLOW")));
            }
            rs.close();
            stmt.close();
        } 
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return library;
    }

    public void updateLibrary(Library library)
    {
        BasicConfigurator.configure();
         try 
         {
             String sqlRequest = "UPDATE Library SET ISBN='?',TITLE='?',COVER='?'," +
                        "DESCRIPTION='?',PAGES=?,USERs=?,WORKFLOW=? WHERE ID=?";
             PreparedStatement ps = connection.prepareStatement(sqlRequest);

            // String[] libraryParams = new String[8];
           //  libraryParams = library.toStringList().toArray(libraryParams);
             //for(int i=1; i < 8; i++)
            //    ps.setString(i, libraryParams[i]);
            //ps.setString(8, libraryParams[0]);

           // ps.executeUpdate();
           // connection.prepareStatement("commit").executeUpdate();

            ps.close();

        } 
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public void deleteLibrary(int id) 
    {
        BasicConfigurator.configure();
        try
        {
            String sqlRequest = "DELETE FROM Library WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);
            ps.setInt(1, id);
            ps.executeUpdate();

            ps.close();

        } 
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

   
}

