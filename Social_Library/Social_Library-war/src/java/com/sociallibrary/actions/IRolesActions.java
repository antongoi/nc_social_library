/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.actions;

import com.sociallibrary.entity.Role;
import com.sociallibrary.entity.User;
import java.util.List;

/**
 *
 * @author Nastya Pavlova
 */
public interface IRolesActions {

     public List<Role> getAllRoles();
     public void applyRoleToUser(Role role, User user);
     public void dropAllRolesOfUser(User user);

}
