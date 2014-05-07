/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.crud;

import com.sociallibrary.entity.*;

/**
 *
 * @author Nastya Pavlova
 */
public interface IAuthorCRUD {

     public void createAuthor(Author author);
     public Author readAuthor(int id);
     public void updateAuthor(Author authorOld, Author authorNew);
     public void deleteAuthor(Author author);
}
