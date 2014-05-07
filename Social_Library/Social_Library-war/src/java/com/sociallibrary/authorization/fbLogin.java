/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sociallibrary.authorization;

import com.sociallibrary.actions.UsersActions;
import com.sociallibrary.constants.Const;
import com.sociallibrary.controller.Command;
import com.sociallibrary.controller.ConfigurationManager;
import com.sociallibrary.crud.RoleCRUD;
import com.sociallibrary.crud.UserCRUD;
import com.sociallibrary.email.EmailSender;
import com.sociallibrary.entity.Gender;
import com.sociallibrary.entity.Role;
import com.sociallibrary.entity.User;
import com.sociallibrary.registration.SecurityHash;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Костя
 */
public class fbLogin implements Command {

    private String code;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String fb_id;
    private String page = ConfigurationManager.INDEX_PAGE;

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UnsupportedEncodingException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            code = request.getParameter("code");
            //token
            String token = null;
            try {
                String g = "https://graph.facebook.com/oauth/access_token?client_id=" + Const.FB_APP_ID +
                        "&redirect_uri=" + URLEncoder.encode(Const.HOST + "Controller?command=fbLogin",
                        "UTF-8") + "&client_secret=" + Const.FB_APP_SECRET + "&code=" + code;
                URL u = new URL(g);
                URLConnection c = u.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
                String inputLine;
                StringBuffer b = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    b.append(inputLine + "\n");
                }
                in.close();
                token = b.toString();
                if (token.startsWith("{")) {
                    throw new Exception("error on requesting token: " + token + " with code: " + code);
                }
            } catch (Exception e) {
                // an error occurred, handle this
            }
            //Graph API
            String graph = null;
            try {
                String g = "https://graph.facebook.com/me?" + token;
                URL u = new URL(g);
                URLConnection c = u.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuffer b = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    b.append(inputLine + "\n");
                }
                in.close();
                graph = b.toString();

            } catch (Exception e) {
                // an error occurred, handle this
            }
            //Take data from FB for user
            try {
                JSONObject j = new JSONObject(graph);
                fb_id = j.getString("id");
                firstName = j.getString("first_name");
                lastName = j.getString("last_name");
                email = j.getString("email");
                gender = j.getString("gender");
                if (email.equals("")) {
                    email = "noEmail" + fb_id;
                }
                if (gender.equals("")) {
                    gender = "male";
                }
            } catch (org.json.JSONException ex) {
                Logger.getLogger(fbLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

            //create user
            HttpSession session = request.getSession(true);
            User user = new User();
            user = new UsersActions().searchUserByLogin(fb_id);
            if (user == null) {
                user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                if (gender.equals("male")) {
                    user.setGender(Gender.getGender(1));
                } else {
                    user.setGender(Gender.getGender(0));
                }
                user.setLogin(fb_id);
                user.setNotify(false);
                user.setBanned(false);
                String pass = SecurityHash.getPass(8, 12);
                if (email.equals("noEmail"+fb_id)) {
                    user.setPassword(pass);
                } else {
                    try {
                        user.setPassword(SecurityHash.getMd5(pass));
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(fbLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String mailSub = "Registration on Social Library via Facebook";
                    String mailText = "Welcom to '" + Const.HOST + "'!\n" +
                            "You have register now using Facebok.\n" +
                            "You can login in our site using this data without connection to Facebook:\n" +
                            "Login: " + user.getLogin() + " \n" +
                            "Password: " + pass + " \n";
                    String mail[] = new String[1];
                    mail[0] = user.getEmail();
                    try {
                        EmailSender.sendEmail(mail, mailSub, mailText);
                    } catch (MessagingException ex) {
                        Logger.getLogger(fbLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                user.setConfirmed(true);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                user.setRegistrationDate(dateFormat.format(new Date()).toString());
                List<Role> rList = new ArrayList<Role>();
                rList.add(new RoleCRUD().readRole(3));
                user.setRoles(rList);
                new UserCRUD().createUser(user);
                user = new UsersActions().searchUserByLogin(user.getLogin());

                session.setAttribute("user", user);
                int role = rList.get(0).getId();
                session.setAttribute("role", role);
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE + role);
            } else {
                session.setAttribute("user", user);
                int role = user.getRoles().get(0).getId();
                session.setAttribute("role", role);
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE + role);
            }
        } finally {
            return page;
        }
    }
}
