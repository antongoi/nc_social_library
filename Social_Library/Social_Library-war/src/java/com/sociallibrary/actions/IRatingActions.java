/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.actions;

import com.sociallibrary.entity.Rating;
import java.util.List;

/**
 *
 * @author Nastya Pavlova
 */
public interface IRatingActions {

    public List<Rating> getRatingsByBookId(long id);
    public Rating getRatingByBookAndUserId(long userId, long bookId);

}
