/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.search;

import java.util.ArrayList;

/**
 *
 * @author Антон
 */
public class BookEntity {

    public static ArrayList<BookEntity> books;

    private int id;
    private int rate = 0;
    private String title;
    private String cover;
    private String description;
    private String author;
    private boolean isInLocallib = false;

    public BookEntity() {
        books = new ArrayList<BookEntity>();

        for(int i = 0; i < 10; i++)
            books.add(new BookEntity(i, "Title_" + i, "Cover_" + i, "Description_" + i, "Author_" + i));
    }

    public BookEntity(int id, String title, String cover, String description, String author) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.description = description;
        this.author = author;
    }



    public String getAuthor() {
        return author;
    }

    public String getCover() {
        return cover;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIsInLocallib() {
        return isInLocallib;
    }

    public void setIsInLocallib(boolean isInLocallib) {
        this.isInLocallib = isInLocallib;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate>5?5:rate<1?1:rate;
    }

    public static BookEntity getBookById(int id){
        for(BookEntity be : books)
            if(be.getId() == id) return be;
        return null;
    }

}
