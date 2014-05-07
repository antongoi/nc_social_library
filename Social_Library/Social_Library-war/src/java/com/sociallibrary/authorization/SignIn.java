package com.sociallibrary.authorization;

import com.sociallibrary.actions.UsersActions;
import com.sociallibrary.controller.Command;
import com.sociallibrary.controller.ConfigurationManager;
import com.sociallibrary.entity.Role;
import com.sociallibrary.entity.User;
import com.sociallibrary.registration.SecurityHash;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author РљРѕСЃС‚СЏ
 */
public class SignIn implements Command {


    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UnsupportedEncodingException {
        String page = null;
        Integer role = null;
        response.setContentType("text/html;charset=UTF-8");
        User user = new UsersActions().searchUserByLogin(request.getParameter("login"));
        try {
            if (user != null) {
                if (user.getPassword().equals(SecurityHash.getMd5(request.getParameter("password"))) && user.isConfirmed() && !user.isBanned()) {

                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    List<Role> roles = user.getRoles();
                    session.setAttribute("role", roles.get(0).getId());
                    role = (Integer) session.getAttribute("role");
                    page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE + role);
                
            } else {
                page = ConfigurationManager.INDEX_PAGE;
            }
            }
        } catch (NoSuchAlgorithmException ex) {

            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error_log", ex);

        }

        return page;
    }
}
