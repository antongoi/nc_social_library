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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class vkLogin implements Command {

    private String page = null;
    private String token;
    private String code;
    private String access_token;
    private Integer vk_id;
    private int gender;
    private String firstName;
    private String lastName;

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UnsupportedEncodingException {
        try {
            code = request.getParameter("code");
            String g = "https://oauth.vk.com/access_token?" +
                    "client_id=" + Const.VK_APP_ID +
                    "&client_secret=" + Const.VK_APP_SECRET +
                    "&code=" + code +
                    "&redirect_uri=" + Const.HOST + "Controller?command=vkLogin";
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
        try {
            JSONObject json = new JSONObject(token);
            access_token = json.getString("access_token");
            vk_id = json.getInt("user_id");

        } catch (JSONException e) {
        }
        String graph = null;
        try {
            String gg = "https://api.vk.com/method/users.get?user_id=" +
                    vk_id + "&fields=first_name,last_name,sex&v=5.5&access_token=" + access_token;
            URL u = new URL(gg);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(),"UTF-8"));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine + "\n");
            }
            in.close();
            graph = b.toString();
            graph = graph.substring(13);

        } catch (Exception e) {
            // an error occurred, handle this
            }

        try {
            JSONObject j = new JSONObject(graph);
            firstName = j.getString("first_name");
            lastName = j.getString("last_name");
            gender = j.getInt("sex");
            //TODO bad bad bad
            if (gender == 0) {
                gender = 2;
            }
            if (gender == 2) {
                gender = 1;
            } else {
                gender = 0;
            }
        } catch (org.json.JSONException ex) {
            Logger.getLogger(fbLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

        //create user
        HttpSession session = request.getSession(true);
        User user = new User();
        user = new UsersActions().searchUserByLogin(vk_id.toString());
        if (user == null) {
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail("noEmail" + vk_id.toString());
            user.setGender(Gender.getGender(gender));
            user.setLogin(vk_id.toString());
            user.setNotify(false);
            user.setBanned(false);
            user.setPassword(SecurityHash.getPass(8, 12));
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
        return page;
    }
}
