/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.crud;

import com.sociallibrary.entity.*;

/**
 *
 * @author mazafaka
 */
public interface IUserCRUD {

     public void createUser(User user);
     public User readUser(long id);
     public void updateUser(User user);
     public void deleteUser(int id);

}
