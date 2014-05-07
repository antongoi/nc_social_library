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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
/**
 *
 * @author Антон
 */
public class BookWorkflowCRUD implements IBookWorkflowCRUD{
    
    private Connection connection;
    private PreparedStatement insertStmt;
    public static final Logger log = Logger.getLogger(BookWorkflowCRUD.class);

    public BookWorkflowCRUD() 
            throws SQLException
    {
        connection = ConnectionProvider.getConnection();
        insertStmt=connection.prepareStatement("INSERT INTO Book_Workflow (workflow)  values('?')");
    }

    public void createBookWorkflow(BookWorkflow bookWorkflow) 
    {
        BasicConfigurator.configure();
        try 
        {
            insertStmt.setString(1, bookWorkflow.getWorkflow());
            insertStmt.executeUpdate();
            insertStmt.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public BookWorkflow readBookWorkflow(int id) 
    {
        BasicConfigurator.configure();
        BookWorkflow bookWorkflow = new BookWorkflow();
        try 
        {
            String sqlRequest ="SELECT * FROM Book_Workflow WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bookWorkflow.setId(rs.getInt("id"));
                bookWorkflow.setWorkflow(rs.getString("workflow"));
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return bookWorkflow;
    }

    public void updateBookWorkflow(BookWorkflow bookWorkflow) 
    {
        BasicConfigurator.configure();
        try {
            String sqlRequest = "UPDATE Book_Workflow SET WORKFLOW='?' WHERE ID=?";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);

            String[] bookWorkflowParams = new String[2];
            bookWorkflowParams = bookWorkflow.toStringList().toArray(bookWorkflowParams);
            ps.setString(1, bookWorkflowParams[1]);
            ps.setString(2, bookWorkflowParams[0]);

            ps.executeUpdate();

            ps.close();

        }  catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public void deleteBookWorkflow(int id) 
    {
        BasicConfigurator.configure();
        try {
            String sqlRequest = "DELETE FROM Book_Workflow WHERE id=?";
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
