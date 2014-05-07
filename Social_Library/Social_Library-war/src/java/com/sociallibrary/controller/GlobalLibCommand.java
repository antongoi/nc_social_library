/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.controller;

import com.sociallibrary.controller.Command;
import com.sociallibrary.controller.ConfigurationManager;
import com.sociallibrary.entity.Library;
import com.sociallibrary.model.Dashboard;
import com.sociallibrary.model.GlobalLibrary;
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
public class GlobalLibCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer role = null;
        String page = null;
        GlobalLibrary globallib = new GlobalLibrary();
        int page_number = Integer.valueOf(request.getParameter("page"));
        List<Library> lib = globallib.getPublishedOnPage(page_number);
        request.setAttribute("published", lib);
        request.setAttribute("count_of_pages", globallib.countPages());


        HttpSession session = request.getSession();



//        if (session.getAttribute("role") != null) {
//            role = (Integer) session.getAttribute("role");
//        } else {
//            role = 4;
//        }
//        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DASHBOARD_PAGE + role);


        page = ConfigurationManager.GLOBAL_LIB;
        
        return page;
    }
}
