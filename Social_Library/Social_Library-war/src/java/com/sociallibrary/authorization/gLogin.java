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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Костя
 */
public class gLogin implements Command {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String g_id;
    private String page = ConfigurationManager.INDEX_PAGE;
    private String access_token;
    private String data;
    private String code;

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UnsupportedEncodingException {
        response.setContentType("text/html;charset=UTF-8");
        code = request.getParameter("code");
        String req = "https://accounts.google.com/o/oauth2/token";
        URL url = new URL(req);
        URLConnection urlConn = url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        DataOutputStream cgiInput = new DataOutputStream(urlConn.getOutputStream());

        String content = "code=" + code + "&client_id=" +
                Const.G_APP_ID + "&client_secret=" + Const.G_APP_SECRET + "&redirect_uri=" +
                Const.HOST + "tester&grant_type=authorization_code";

        cgiInput.writeBytes(content);
        cgiInput.flush();
        cgiInput.close();

        BufferedReader cgiOutput = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String line = null;
        String lineAll = "";
        while (null != (line = cgiOutput.readLine())) {
            lineAll = lineAll + line;
        }

        try {
            JSONObject json = new JSONObject(lineAll);
            access_token = json.getString("access_token");

        } catch (JSONException e) {
        }
        String urlGet = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + access_token;
        try {
            URL u = new URL(urlGet);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine + "\n");
            }
            in.close();
            data = b.toString();

        } catch (Exception e) {
        }
        try {
            JSONObject json = new JSONObject(data);
            g_id = json.getString("id");
            email = json.getString("email");
            firstName = json.getString("given_name");
            lastName = json.getString("family_name");
            gender = json.getString("gender");
            if (email.equals("")) {
                email = "noEmail" + g_id;
            }
            if (gender.equals("")) {
                gender = "male";
            }
        } catch (JSONException e) {

        }

        //до этого момента точно забирает данные, проверил отдельно, но выкидует дальше на еррор пейдж, точно так же для ФБ работает...
        //create user
        HttpSession session = request.getSession(true);
        User user = new User();
        user = new UsersActions().searchUserByLogin(g_id);
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
            user.setLogin(g_id);
            user.setNotify(false);
            user.setBanned(false);
            user.setEmail(email);
            String pass = SecurityHash.getPass(8, 12);
            if (email.equals("noEmail"+g_id)) {
                user.setPassword(pass);
            } else {
                try {
                    user.setPassword(SecurityHash.getMd5(pass));
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(fbLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                String mailSub = "Registration on Social Library via Facebook";
                String mailText = "Welcom to '" + Const.HOST + "'!\n" +
                        "You have register now using Google+.\n" +
                        "You can login in our site using this data without connection to Google+:\n" +
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
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE + 3);
        } else {
            session.setAttribute("user", user);
            int role = user.getRoles().get(0).getId();
            session.setAttribute("role", role);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE + role);
        }
        return page;
    }
}
