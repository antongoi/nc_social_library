/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.model;

import com.sociallibrary.actions.LibraryActions;
import com.sociallibrary.actions.RatingActions;
import com.sociallibrary.entity.Library;
import java.util.List;

/**
 *
 * @author Антон
 */
public class LocalLibrary {

    public List<Library> getPublished(long user_id){

        LibraryActions lib = new LibraryActions();

        List<Library> libs= new LibraryActions().getAllLocalBooksByUser(user_id);

        return libs ;

    }

    public List<Library> getPublishedOnPage(long user_id, int page){

        LibraryActions lib = new LibraryActions();

        List<Library> libs= getPublished(user_id);
        int from=0,to=0;
        from = (page-1)*10;
        to=page*10;
        to=to<libs.size()?to:libs.size()-1;
        return libs.subList(from, to);

    }

    public long countPages(long user_id){

        LibraryActions lib = new LibraryActions();

        long count = lib.countAllLocalBooksByUser(user_id);
        count = count/10 + count%10>0?1:0;

        return  count;

    }

    public float getAverageRatingByBookId(long book_id) {
        return new RatingActions().getAverageRatingByBookId(book_id);
    }

    public int getRatingByBookAndUserId(long book_id, long user_id) {
        return new RatingActions().getRatingByBookAndUserId(book_id, user_id).getRate();
    }

}