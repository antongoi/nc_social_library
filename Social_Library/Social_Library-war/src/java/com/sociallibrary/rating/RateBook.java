/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.rating;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Антон
 */
public class RateBook extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            long book_id = Long.parseLong(request.getParameter("book_id").toString());
            long user_id = Long.parseLong(request.getParameter("user_id").toString());
            short book_rate = Short.parseShort(request.getParameter("rate").toString());

//            RatingActions ratingActions = new RatingActions();
//            Rating rating = ratingActions.getRatingsByBookAndUserIds(user_id, book_id);
            //out.print(rating.getRate()+ " " + rating.getBook()+ " " +rating.getUsers());
//            if(rating != null){
//                Rating ratingNew = new Rating();
//                ratingNew.setId(rating.getId());
//                ratingNew.setBook(rating.getBook());
//                ratingNew.setUsers(rating.getUsers());
//                ratingNew.setRate(rating.getRate());
//                new RatingCRUD().updateRating(rating, ratingNew);
//            } else {
//                new RatingCRUD().createRating(book_id, user_id, book_rate);
//            }

            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RateBook</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RateBook at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
