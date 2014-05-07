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
class add implements Command {

    public add() {
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        long user_id = Long.parseLong(request.getParameter("user"));
        long book_id = Long.parseLong(request.getParameter("book"));
        new LibraryActions().addBookToLocal(book_id, user_id);
        Integer role = (Integer) session.getAttribute("role");
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE + role);
    }
}
