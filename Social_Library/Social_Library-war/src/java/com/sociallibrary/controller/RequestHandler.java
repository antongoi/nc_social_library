package com.sociallibrary.controller;

import com.sociallibrary.commands.DashboardCommand;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import com.sociallibrary.registration.ConfirmUser;
import com.sociallibrary.registration.Registration;
import com.sociallibrary.authorization.SignIn;
import com.sociallibrary.authorization.fbLogin;
import com.sociallibrary.authorization.gLogin;
import com.sociallibrary.authorization.twitterLogin;
import com.sociallibrary.authorization.vkLogin;
import com.sociallibrary.commands.ApproveCommand;
import com.sociallibrary.commands.PublishCommand;
import com.sociallibrary.commands.RejectCommand;

/**
 *
 * @author Pavel
 */
public class RequestHandler {

    private static RequestHandler instance = null;
    HashMap<String, Command> commands = new HashMap<String, Command>();

    public static RequestHandler getInstance() {

        if (instance == null) {
            instance = new RequestHandler();
            return instance;
        } else {
            return instance;
        }

    }
///Associates a key-object, which executes

    private RequestHandler() {
        commands.put("rating", new ScoreCommand());
        commands.put("nocommand", new NoCommand());
        commands.put("registration", new Registration());
        commands.put("confirmUser", new ConfirmUser());
        commands.put("dashboard", new DashboardCommand());
        commands.put("signin", new SignIn());
        commands.put("fbLogin", new fbLogin());
        commands.put("vkLogin", new vkLogin());
        commands.put("gLogin", new gLogin());
        commands.put("twitterLogin", new twitterLogin());
        commands.put("globallib", new GlobalLibCommand());
        commands.put("locallib", new LocalLibCommand());
        commands.put("adminpage", new AdminpageCommand());
        commands.put("searchusers", new SearchUsers());
        commands.put("reject", new RejectCommand());
        commands.put("publish", new PublishCommand());
        commands.put("approve", new ApproveCommand());
        commands.put("add", new add());
        commands.put("status", new changeBookStatus());
        // commands.put("publish", new BookPublish());
        commands.put("locDel",new BoolDelFromLocal());
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");

        Command command = commands.get(action);
        if (command == null) {

            command = new NoCommand();
        }

        return command;


    }
}
