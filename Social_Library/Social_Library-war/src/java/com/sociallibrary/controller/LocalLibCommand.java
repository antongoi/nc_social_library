/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.controller;

import com.sociallibrary.controller.Command;
import com.sociallibrary.controller.ConfigurationManager;
import com.sociallibrary.entity.Library;
import com.sociallibrary.entity.User;
import com.sociallibrary.model.LocalLibrary;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Антон
 */
public class LocalLibCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer role = null;
        String page = null;
        LocalLibrary locallib = new LocalLibrary();
        int page_number = Integer.valueOf(request.getParameter("page"));
        User current_user = (User) request.getSession(false).getAttribute("user");
        List<Library> lib = locallib.getPublishedOnPage(current_user.getId(), page_number);
        request.setAttribute("published", lib);
        request.setAttribute("count_of_pages", locallib.countPages(current_user.getId()));


        HttpSession session = request.getSession();



//        if (session.getAttribute("role") != null) {
//            role = (Integer) session.getAttribute("role");
//        } else {
//            role = 4;
//        }
//        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DASHBOARD_PAGE + role);


        page = ConfigurationManager.LOCAL_LIB;

        return page;
    }
}
