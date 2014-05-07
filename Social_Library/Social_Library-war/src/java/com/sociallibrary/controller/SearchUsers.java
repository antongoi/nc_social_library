/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.controller;

import com.sociallibrary.entity.User;
import com.sociallibrary.model.AdminPage;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Антон
 */
public class SearchUsers implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String search_request = request.getParameter("search_users");
        new AdminPage().searchUsers(search_request);
        List<User> users = AdminPage.getSearch_users_result();
        request.setAttribute("users_list", users);
        request.setAttribute("count_of_pages", 1);
        

//        return ConfigurationManager.ADMIN_SEARCH_PAGE;
        return ConfigurationManager.ADMIN_PAGE_USER_MANAGE;
    }

}
