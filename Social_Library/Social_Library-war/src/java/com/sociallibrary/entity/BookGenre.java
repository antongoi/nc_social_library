/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.entity;

/**
 *
 * @author mazafaka
 */
public class BookGenre {
    long id;
    long book_id;
    long genre_id;

    public BookGenre() {
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public long getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(long genre_id) {
        this.genre_id = genre_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
}
