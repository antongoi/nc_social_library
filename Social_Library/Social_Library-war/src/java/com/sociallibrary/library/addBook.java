/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sociallibrary.library;

import com.sociallibrary.controller.Command;
import com.sociallibrary.controller.ConfigurationManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Назар
 */
public class addBook implements Command {

    private String page;
    private String book;
    private String title;
    private String description;
    private String pageCount;
    private String cover;

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        try {
            String g = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine + "\n");
            }
            in.close();
            book = b.toString();

        } catch (Exception e) {
            // an error occurred, handle this
            }
        try {
            JSONObject j = new JSONObject(book);
            if (j.getString("totalItems").equals("0")) {
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE + request.getParameter("role"));
            } else {
                title = j.getString("title");
                String authors = j.getString("authors");
                description = j.getString("description");
                pageCount = j.getString("pageCount");
                cover = j.getString("thumbnail");
            }
        } catch (org.json.JSONException ex) {
            Logger.getLogger(addBook.class.getName()).log(Level.SEVERE, null, ex);
        }

        return page;
    }
}
