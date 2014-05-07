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
public class Role {
    private int id;
    private String name;

    @Override
    public String toString(){
        return id + " " + name;
    }

    public List<String> toStringList(){
        List<String> result = new ArrayList<String>();
        result.add(String.valueOf(id));
        result.add(name);

        return result;
    }

    public Role() {
    }

    public Role(int id) {
        this.id = id;
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
