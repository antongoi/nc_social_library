/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.controller;

//import com.sociallibrary.actions.LibraryActions;
//import com.sociallibrary.entity.Library;
import com.sociallibrary.model.AdminPage;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import sun.util.BuddhistCalendar;

/**
 *
 * @author Антон
 */
public class AssignRole implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String is0 = request.getParameter("administrator");
        is0=is0==null?"0":is0;
        String is1 = request.getParameter("moderator");
        is1=is1==null?"0":is1;
        String is2 = request.getParameter("advancedUser");
        is2=is2==null?"0":is2;
        String is3 = request.getParameter("beginnerUser");
        is3=is3==null?"0":is3;
        boolean isAdmin = Integer.valueOf(is0)==1;
        boolean isModer = Integer.valueOf(is1)==1;
        boolean isAdvanced = Integer.valueOf(is2)==1;
        boolean isBeginner = Integer.valueOf(is3)==1;

        ArrayList<Integer> role_ids = new ArrayList<Integer>();
        if(isAdmin) role_ids.add(0);
        if(isModer) role_ids.add(1);
        if(isAdvanced) role_ids.add(2);
        if(isBeginner) role_ids.add(3);

        long user_id = Long.valueOf(request.getParameter("user_id"));
        new AdminPage().assignRolesToUser(user_id, role_ids);

//        return ConfigurationManager.ADMIN_PAGE;
        return null;
    }

}
