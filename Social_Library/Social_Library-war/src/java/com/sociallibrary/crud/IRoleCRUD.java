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
public interface IRoleCRUD {

     public Role readRole(int id);
     public void createRole(Role role);
     public void deleteRole(int id);
     public void updateRole(Role role);

}
