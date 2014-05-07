/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.crud;

/**
 *
 * @author Антон
 */

import com.sociallibrary.actions.RolesActions;
import com.sociallibrary.actions.UsersActions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sociallibrary.connection.ConnectionProvider;
import com.sociallibrary.entity.Gender;
import com.sociallibrary.entity.Role;
import com.sociallibrary.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class UserCRUD implements IUserCRUD
{

    private Connection connection;
    public static final Logger log = Logger.getLogger(UserCRUD.class);

    public UserCRUD() {
        connection = ConnectionProvider.getConnection();
    }

    public void createUser(User user) {
        BasicConfigurator.configure();
        try {
                String sqlRequest =
                        "INSERT INTO Users (ID,FIRST_NAME,LAST_NAME,EMAIL,LOGIN,PASSWORD," +
                        "GENDER,CONFIRMED,BANNED,REGISTRATION_DATE,NOTIFY) " +
                        "values(USERS_ID.nextval,?,?,?,?,?,?, ?, ?, TO_DATE(?,'yyyy-mm-dd'), ?)";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);


            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getGender().toInt());
            ps.setInt(7, (user.isConfirmed())?1:0);
            ps.setInt(8, (user.isBanned())?1:0);
            ps.setString(9, user.getRegistrationDate());
            ps.setInt(10, (user.isNotify())?1:0);
            ps.executeUpdate();
            User u= new UsersActions().searchUserByLogin(user.getLogin());
            

            for(Role r : user.getRoles()) new RolesActions().applyRoleToUser(r, u);

            ps.close();

        }
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public User readUser(long id) {
        BasicConfigurator.configure();
        User user = new User();
        try {
                String sqlRequest =
                        "SELECT * FROM Users WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setLogin(rs.getString("LOGIN"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setGender(Gender.getGender(rs.getInt("GENDER")));
                user.setConfirmed(rs.getInt("CONFIRMED")==1);
                user.setBanned(rs.getInt("BANNED")==1);
                //String[] regDate = rs.getString("REGISTRATION_DATE").split(".");
                //user.setRegistrationDate(Date.valueOf(regDate[2]+"-"+regDate[1]+"-"+regDate[0]));
                user.setRegistrationDate(rs.getString("REGISTRATION_DATE"));
                user.setNotify(rs.getInt("NOTIFY")==1);
                List<Role> roles = new ArrayList<Role>();
                List<Integer> integers = new RolesActions().getRolesIdByUserId(user.getId());
                for(int i : integers)
                    roles.add(new RoleCRUD().readRole(i));
                user.setRoles(roles);
                //user.setRole(new Role(1, "Administrator"));
            }

            rs.close();
            ps.close();

        }
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return user;
    }

    public void updateUser(User user) {
        BasicConfigurator.configure();
        try {
                String sqlRequest = "UPDATE Users SET FIRST_NAME=?, LAST_NAME=?, " +
                        "EMAIL=?, LOGIN=?, PASSWORD=?, GENDER=?, CONFIRMED=?, " +
                        "BANNED=?, REGISTRATION_DATE=TO_DATE(?,'yyyy-mm-dd'), NOTIFY=? WHERE ID=?";
            PreparedStatement ps = connection.prepareStatement(sqlRequest);

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getGender().toInt());
            ps.setInt(7, user.isConfirmed()?1:0);
            ps.setInt(8, user.isBanned()?1:0);
            ps.setString(9, user.getRegistrationDate());
            ps.setInt(10, user.isNotify()?1:0);
            ps.setLong(11, user.getId());

            new RolesActions().dropAllRolesOfUser(user);
            for(Role r : user.getRoles()) new RolesActions().applyRoleToUser(r, user);

            ps.executeUpdate();
            //connection.prepareStatement("commit").executeUpdate();

            ps.close();

        }
        catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public void deleteUser(int id) {
        BasicConfigurator.configure();
        try {
                String sqlRequest = "DELETE FROM users WHERE id=?";
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