/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.crud;

import com.sociallibrary.entity.*;

/**
 *
 * @author mazafaka
 */
public interface ILibraryCRUD {

     public void createLibrary(Library library);
     public Library readLibrary(long id);
     public void updateLibrary(Library library);
     public void deleteLibrary(int id);

}
