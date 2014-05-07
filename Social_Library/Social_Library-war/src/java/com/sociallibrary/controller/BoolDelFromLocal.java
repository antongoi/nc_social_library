/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sociallibrary.controller;

import com.sociallibrary.actions.LibraryActions;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Назар
 */
class BoolDelFromLocal implements Command {

    public BoolDelFromLocal() {
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        long book_id = Integer.parseInt(request.getParameter("book"));
        long user_id = Integer.parseInt(request.getParameter("user"));
        new LibraryActions().removeBookFromLocal(book_id, user_id);
        Integer role = (Integer) session.getAttribute("role");
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE + role);
    }
}
