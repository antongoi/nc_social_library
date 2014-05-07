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
import com.sociallibrary.entity.Role;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
/**
 *
 * @author Антон
 */
public class RoleCRUD implements IRoleCRUD
{

    private Connection connection;
    public static final Logger log = Logger.getLogger(RoleCRUD.class);

    public RoleCRUD() {
        connection = ConnectionProvider.getConnection();
    }

    public Role readRole(int id)
    {
        BasicConfigurator.configure();
        Role role = new Role();
        try {
                String sqlRequest =
                        "SELECT * FROM Role WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
            }
           
            rs.close();
            ps.close();
        }
       catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return role;
    }

    public void createRole(Role role) 
    {
        BasicConfigurator.configure();
        try
        {
                String sqlRequest =
                        "INSERT INTO ROLE (ID, Name)  values(?, '?')";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);

            ps.setInt(1, role.getId());
            ps.setString(2, role.getName());
            ps.executeUpdate();

            ps.close();

        }
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public void deleteRole(int id) 
    {
        BasicConfigurator.configure();
        try
        {
                String sqlRequest = "DELETE FROM Role WHERE id=?";
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

    public void updateRole(Role role) 
    {
        BasicConfigurator.configure();
        try
        {
                String sqlRequest = "UPDATE Role SET NAME='?' WHERE ID=?";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);

            String[] roleParams = new String[2];
            roleParams = role.toStringList().toArray(roleParams);
            ps.setString(1, roleParams[1]);
            ps.setString(2, roleParams[0]);

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
