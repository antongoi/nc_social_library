/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.actions;

import com.sociallibrary.entity.Author;
import com.sociallibrary.entity.Library;
import com.sociallibrary.entity.Rating;
import java.util.List;

/**
 *
 * @author Nastya Pavlova
 */
public interface ILibraryActions {

     public List<Library> BooksList(int from, int to,int workflow);
     public void AddToLocal(long book_id, long user_id);
     public void RemoveFromLocal(long book_id, long user_id);
     public boolean CheckLocal(long book_id, long user_id);
     public List<Library> searchBooksByAuthor(String author_name);
     public List<Library> searchBooksByGenre(String genre);
     public List<Library> searchBooksByTitle(String title);
     public List<Library> searchBooksByDescription(String description);
     public List<Author> getAuthorsList(long bookId);
     public List<Rating> getRatingsList(long bookId);
     public int getAverageRate(long bookId);
}
