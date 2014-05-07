/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Назар
 */
class BookPublish implements Command{

    String page=null;
    public BookPublish() {
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        return page;
    }

}
