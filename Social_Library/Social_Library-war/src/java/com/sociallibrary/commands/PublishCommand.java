/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.commands;

import com.sociallibrary.actions.LibraryActions;
import com.sociallibrary.controller.Command;
import com.sociallibrary.controller.ConfigurationManager;
import com.sociallibrary.entity.Library;
import com.sociallibrary.model.Dashboard;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author П
 */
public class PublishCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Integer role = null;
        String page = null;
        Dashboard dashboard = new Dashboard();
//        new LibraryActions().changeWorkflow(4, request.getParameter("book"));
        dashboard.changePublished((String)request.getParameter("publish"));
        List<Library> libs = dashboard.getInProgress();
        request.setAttribute("inprogress", libs);


        HttpSession session = request.getSession();



        if (session.getAttribute("role") != null) {
            role = (Integer) session.getAttribute("role");
        } else {
            role = 4;
        }
         page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DASHBOARD_PAGE + role);


        return page ;
    }


}
