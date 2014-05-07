/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sociallibrary.registration;

import com.sociallibrary.controller.Command;
import com.sociallibrary.controller.ConfigurationManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sociallibrary.crud.UserCRUD;
import com.sociallibrary.entity.User;

/**
 *
 * @author Костя
 */
public class ConfirmUser implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        User user = new UserCRUD().readUser(Integer.parseInt(request.getParameter("users")));
        user.setConfirmed(true);
        new UserCRUD().updateUser(user);
        return ConfigurationManager.INDEX_PAGE;
    }
}