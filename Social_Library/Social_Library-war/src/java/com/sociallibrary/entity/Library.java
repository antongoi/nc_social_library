/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.entity;

/**
 *
 * @author Anton
 */

public class Library {
    private long id;
    private String isbn;
    private String title;
    private String cover;
    private String description;
    private int pages;
    private User user;
    private BookWorkflow workflow;

    public Library() {
    }

   // @Override
 //   public String toString(){
  //      return id + " " + isbn + " " + title + " " + cover + " " +
  //              description + " " + pages;// + " "+user;//+" "+workflow;
   // }

    /*public List<String> toStringList(){
        List<String> result = new ArrayList<String>();
        result.add(String.valueOf(id));
        result.add(isbn);
        result.add(title);
        result.add(cover);
        result.add(description);
        result.add(String.valueOf(pages));
        //result.add(String.valueOf(user.getId()));
       // result.add(String.valueOf(workflow.getId()));

        return result;
    }*/

    //public Library(long id) {
    //    this.id = id;
    //}


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookWorkflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(BookWorkflow workflow) {
        this.workflow = workflow;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user=user;
    }
}
