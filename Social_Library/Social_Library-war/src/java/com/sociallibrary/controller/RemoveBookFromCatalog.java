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

/**
 *
 * @author Антон
 */
public class RemoveBookFromCatalog implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long user_id = Long.parseLong(request.getParameter("user_id"));
        long book_id = Long.parseLong(request.getParameter("book_id"));
        try {
            new LibraryActions().removeBookFromLocal(book_id, user_id);
        } finally {

        }
        return ConfigurationManager.LOCAL_LIB;
    }

}
