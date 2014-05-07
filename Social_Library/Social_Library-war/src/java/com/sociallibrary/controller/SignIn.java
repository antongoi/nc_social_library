/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.controller;
import com.sociallibrary.actions.UsersActions;
import com.sociallibrary.entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Антон
 */
public class SignIn implements Command {

//    public static void main(String[] args){
//        User user = new UsersActions().getUser("Quaecte", "Ie5Eequaemai");
//        System.out.println(user.getEmail());
//    }

     public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
//        System.out.println("#@!^&*U");
//        System.out.println(login);
//        System.out.println(password);

        User user = new UsersActions().searchUserByLogin(login);
        if(user.getPassword().equals(password))
            request.getSession(true).setAttribute("user", user);
        else return ConfigurationManager.ERROR_PAGE;

        return ConfigurationManager.LOCAL_LIB;
    }

}
