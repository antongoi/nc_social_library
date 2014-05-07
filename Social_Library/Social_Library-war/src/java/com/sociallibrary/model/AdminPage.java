/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.model;

import com.sociallibrary.actions.UsersActions;
import com.sociallibrary.crud.RoleCRUD;
import com.sociallibrary.crud.UserCRUD;
import com.sociallibrary.entity.Role;
import com.sociallibrary.entity.User;
import com.sun.jmx.snmp.UserAcl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Антон
 */
public class AdminPage {

    private static List<User> last_search_users_result = null;

    public void assignRolesToUser(long user_id, List<Integer> role_ids){
        User user = new UserCRUD().readUser(user_id);
        user.freeRoles();
        for(int role_id : role_ids){
            Role role = new RoleCRUD().readRole(role_id);
            if(!user.getRoles().contains(role)) user.getRoles().add(role);
        }
        new UserCRUD().updateUser(user);
    }

    public void searchUsers(String search_request){
        last_search_users_result = new ArrayList<User>();
        last_search_users_result.addAll(new UsersActions().searchUsersByParam(UsersActions.firstName_param, search_request));
        last_search_users_result.addAll(new UsersActions().searchUsersByParam(UsersActions.lastName_param, search_request));
        last_search_users_result.addAll(new UsersActions().searchUsersByParam(UsersActions.login_param, search_request));
    }

    public static List<User> getSearch_users_result() {
        return last_search_users_result;
    }

//    public List<User> getAllUsers(){
//        return new UsersActions().getAllUsers();
//    }

    public List<User> getUsersFromTo(long id_from, long id_to){
        return new UsersActions().getUsersFromTo(id_from, id_to);
    }

    public List<User> getUsersOnPage(int page){
        long id_from = (page-1)*10;
        long id_to = page*10;
        long count = new UsersActions().countAllUsers();
        id_to=id_to<count?id_to:count-1;
        return new UsersActions().getUsersFromTo(id_from, id_to);
    }

    public int countPages(){
        long count_users = new UsersActions().countAllUsers();
        int count_pages = (int) count_users/10;
        count_pages = count_pages + (count_users%10>0?1:0);
        return count_pages;
    }


    public boolean isAdmin(User user){
        for(Role role : user.getRoles())
            if(role.getId()==0) return true;

        return false;
    }

    public boolean isModerator(User user){
        for(Role role : user.getRoles())
            if(role.getId()==1) return true;

        return false;
    }

    public boolean isAdvancedUser(User user){
        for(Role role : user.getRoles())
            if(role.getId()==2) return true;

        return false;
    }

    public boolean isBeginnerUser(User user){
        for(Role role : user.getRoles())
            if(role.getId()==3) return true;

        return false;
    }

}
