/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.controller;

import com.sociallibrary.actions.LibraryActions;
import com.sociallibrary.entity.Library;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Антон
 */
public class SearchInGlobalLib implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        long user_id = Long.parseLong(request.getParameter("user_id"));
//        long book_id = Long.parseLong(request.getParameter("book_id"));
        String search_request = request.getParameter("search_request");//.toUpperCase();
        
        List<Library> founded_books = new ArrayList<Library>();
//        List<Library> founded = new LibraryActions().searchBooksByAuthor(search_request);
//        for(Library library : founded)
//            founded_books.add(library);
//        founded = new LibraryActions().searchBooksByDescription(search_request);
//        for(Library library : founded)
//            founded_books.add(library);
//        founded = new LibraryActions().searchBooksByTitle(search_request);
//        for(Library library : founded)
//            founded_books.add(library);
//        founded = new LibraryActions().searchBooksByGenre(search_request);
//        for(Library library : founded)
//            founded_books.add(library);
        
        founded_books.addAll(new LibraryActions().searchBooksByAuthor(search_request));
        founded_books.addAll(new LibraryActions().searchBooksByDescription(search_request));
        founded_books.addAll(new LibraryActions().searchBooksByTitle(search_request));
        founded_books.addAll(new LibraryActions().searchBooksByGenre(search_request));

//        for(Library l : founded_books){
//        int books_count = founded_books.size();
//        for(int i = 0; i<books_count; i++){
//            if(founded_books.get(i).getWorkflow().getId()!=4){
//                founded_books.remove(i);
//                books_count--;
//            }
//            Library l = founded_books.get(i);
//            while(founded_books.indexOf(l)!=founded_books.lastIndexOf(l)){
//                founded_books.remove(i);
//                books_count--;
//            }
//        }

        request.getSession().setAttribute("founded_books", founded_books);

//       return ConfigurationManager.SEARCH_PAGE;
        return null;
    }

}
