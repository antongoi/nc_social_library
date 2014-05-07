/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.actions;

import com.sociallibrary.entity.*;
import com.sociallibrary.connection.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Nastya Pavlova
 */
public class RolesActions implements IRolesActions
{
    private Connection connection;
    public static final Logger log = Logger.getLogger(RolesActions.class);

    public RolesActions()
    {
        connection = ConnectionProvider.getConnection();
    }

    public List<Role> getAllRoles()
    {
        BasicConfigurator.configure();
        List<Role> roles = new ArrayList<Role>();
        try
        {
            String sqlRequest ="SELECT * FROM Role";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
                roles.add(role);
            }
            ps.close();
            rs.close();
        } 
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return roles;
    }

    public List<Integer> getRolesIdByUserId(long id)
    {
        BasicConfigurator.configure();
        List<Integer> roles = new ArrayList<Integer>();
        try
        {
            String sqlRequest ="SELECT users_roles.role " +
                                "FROM users " +
                                "INNER JOIN users_roles " +
                                "ON users.id=users_roles.users " +
                                "WHERE users.id=? order by users_roles.role ";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                roles.add(rs.getInt("ROLE"));
            }
            ps.close();
            rs.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return roles;
    }

    public void applyRoleToUser(Role role, User user)
    {
        BasicConfigurator.configure();
        try 
        {
            String sqlRequest ="INSERT INTO USERS_ROLES (ID,USERS,ROLE) values(USERS_ROLES_ID.nextval, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);

            ps.setLong(1, user.getId());
            ps.setInt(2, role.getId());
            ps.executeUpdate();

            ps.close();

        } 
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public void dropAllRolesOfUser(User user)
    {
        BasicConfigurator.configure();
        try
        {
            String sqlRequest ="DELETE FROM USERS_ROLES WHERE USERS=?";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);

            ps.setLong(1, user.getId());
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
