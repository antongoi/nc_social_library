/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * @author Антон
 */
public class Catalog {
    private long id;
    private User user;
    private Library book;
    private Timestamp eventTime;
    private BookStatus status;

    public Catalog() {
    }

    public Catalog(long id) {
        this.id = id;
    }

    public Library getBook() {
        return book;
    }

    public void setBook(Library book) {
        this.book = book;
    }

    public Timestamp getEventTime() {
        return eventTime;
    }

    public void setEventTime(Timestamp eventTime) {
        this.eventTime = eventTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
