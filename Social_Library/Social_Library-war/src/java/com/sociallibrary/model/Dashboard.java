/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sociallibrary.model;

import com.sociallibrary.actions.LibraryActions;
import com.sociallibrary.entity.Library;
import java.util.List;

/**
 *
 * @author Pavel
 */
public class Dashboard {

    public List<Library> getInProgress() {

        List<Library> libs =
                (List<Library>) new LibraryActions().searchBooksByParameter(LibraryActions.workflow,
                LibraryActions.workflowInprogres);

        return libs;

    }

    public boolean changeReject(String id) {
       

   boolean temp = new LibraryActions().changeWorkflow(2, id);
       

        return temp;

    }

    public boolean changeApproved(String id) {

  boolean temp = new LibraryActions().changeWorkflow(3, id);
        

        return temp;

    }

     public boolean changePublished(String id){

      boolean temp =  new LibraryActions().changeWorkflow(4,id);
       

        return temp;

    }
}
