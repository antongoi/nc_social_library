/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sociallibrary.registration;

import com.sociallibrary.actions.UsersActions;
import com.sociallibrary.constants.Const;
import com.sociallibrary.controller.Command;
import com.sociallibrary.controller.ConfigurationManager;
import com.sociallibrary.crud.RoleCRUD;
import com.sociallibrary.entity.Role;
import com.sociallibrary.entity.User;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sociallibrary.crud.UserCRUD;
import java.util.List;
import com.sociallibrary.email.EmailSender;
import com.sociallibrary.entity.Gender;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Костя
 */
public class Registration implements Command {

    private User user;
    private RoleCRUD roleCRUD = new RoleCRUD();

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UnsupportedEncodingException {
        response.setContentType("text/html;charset=UTF-8");
        user = new User();
        List<Role> rList = new ArrayList<Role>();
        rList.add(roleCRUD.readRole(3));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setLogin(request.getParameter("login"));
        try {
            user.setPassword(SecurityHash.getMd5(request.getParameter("password")));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        user.setGender(Gender.getGender(Integer.parseInt(request.getParameter("gender"))));
        user.setConfirmed(false);
        user.setBanned(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setRegistrationDate(dateFormat.format(new Date()).toString());
        if (Integer.parseInt(request.getParameter("notify")) == 1) {
            user.setNotify(true);
        }
        else {
            user.setNotify(false);
        }
        user.setRoles(rList);
        UserCRUD userCRUD = new UserCRUD();
        userCRUD.createUser(user);
        UsersActions uAct = new UsersActions();
        User users = uAct.searchUserByLogin(user.getLogin());
        String mailSub = "Registration on Social Library";
        String mailText = "Please copy and use link: '" + Const.HOST + "Controller?users=" + users.getId() + "&command=confirmUser'";
        String mail[] = new String[1];
        mail[0] = user.getEmail();
        try {
            EmailSender.sendEmail(mail, mailSub, mailText);
        } catch (MessagingException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ConfigurationManager.INDEX_PAGE;
    }
}
