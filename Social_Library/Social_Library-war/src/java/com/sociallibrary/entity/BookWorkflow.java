/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Антон
 */
public class BookWorkflow
{

    private int id;
    private String workflow;

    public BookWorkflow() {
    }

    public List<String> toStringList(){
        List<String> result = new ArrayList<String>();
        result.add(String.valueOf(id));
        result.add(workflow);

        return result;
    }

    public BookWorkflow(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    
}
