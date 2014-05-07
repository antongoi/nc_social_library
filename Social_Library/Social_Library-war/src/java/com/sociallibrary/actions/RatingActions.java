/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.actions;

import com.sociallibrary.entity.*;
import com.sociallibrary.crud.*;
import org.apache.log4j.*;
import com.sociallibrary.connection.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Антон
 */
public class RatingActions implements IRatingActions
{
    private Connection connection;
    public static final Logger log = Logger.getLogger(RatingActions.class);

    public RatingActions()
    {
        connection = ConnectionProvider.getConnection();
    }
    
    public List<Rating> getRatingsByBookId(long id)
    {
        BasicConfigurator.configure();
        List<Rating> lList = new ArrayList<Rating>();
        String selectParametr = "select id from rating where book=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                lList.add(new RatingCRUD().readRating(rs.getInt("id")));
               
            }
            rs.close();
            stmt.close();
        } 
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return lList;
    }

    public float getAverageRatingByBookId(long book_id){
        float result = 0;
        List<Rating> ratings = new RatingActions().getRatingsByBookId(book_id);
        for(Rating r : ratings)
            result += r.getRate();
        if(ratings.size() > 0) return result/ratings.size();

        return 0;
    }

    public Rating getRatingByBookAndUserId(long book_id, long user_id)
    {
        BasicConfigurator.configure();
        Rating rating = new Rating();
        String selectParametr = "select id from rating where book=? AND users=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, book_id);
            stmt.setLong(2, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                rating = new RatingCRUD().readRating(rs.getInt("id"));
            }
            rs.close();
            stmt.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return rating;
    }

    public int countRatingsByBookAndUserId(long book_id, long user_id)
    {
        BasicConfigurator.configure();
        int rating = 0;
        String selectParametr = "select count(id) from rating where book=? AND users=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(selectParametr);
            stmt.setLong(1, book_id);
            stmt.setLong(2, user_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                rating = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return rating;
    }

     public boolean addRating(long book_id, long user_id, int rate)
             
     {
     try
     {
        Library book = new LibraryCRUD().readLibrary(book_id);
        User user = new UserCRUD().readUser(user_id);
        if(book!=null)
            if(user!=null){
                Rating rating = new Rating();
                rating.setBook(book);
                rating.setUser(user);
                rating.setRate(rate);

                //System.out.print("%^$"+countRatingsByBookAndUserId(book_id, user_id));
                if(new RatingActions().countRatingsByBookAndUserId(book_id, user_id) == 0)
                    new RatingCRUD().createRating(rating);
                else {
                    Rating oldRating = new RatingActions().getRatingByBookAndUserId(book_id, user_id);
                    Rating newRating = new RatingActions().getRatingByBookAndUserId(book_id, user_id);
                    newRating.setRate(rate);
                    new RatingCRUD().updateRating(oldRating, newRating);
                }

                return true;
            }
     }
     catch(SQLException e)
     {
         e.printStackTrace();
         log.error("SQLException:" + e);
     }
        return false;
    }

}
