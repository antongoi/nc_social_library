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
public interface ICatalogCRUD {

     public void createCatalog(Catalog catalog);
     public Catalog readCatalog(int id);
     public void updateCatalog(Catalog catalogOld, Catalog catalogNew);
     public void deleteCatalog(Catalog catalog);

}
