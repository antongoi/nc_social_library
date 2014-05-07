/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.entity;

/**
 *
 * @author Антон
 */
public class Rating {
    private long id;
    private int rate;
    private User user;
    private Library book;

    public Rating() {
    }

    public Rating(long id) {
        this.id = id;
    }

    public Library getBook() {
        return book;
    }

    public void setBook(Library book) {
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
