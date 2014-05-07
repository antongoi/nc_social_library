/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.entity;

/**
 *
 * @author Антон
 */
public enum Gender {
    male{
        public boolean toBoolean(){return true;}
        public int toInt() {return 1;}
    }, 
    female{
        public boolean toBoolean(){return false;}
        public int toInt() {return 0;}
    };

    public static Gender getGender(boolean b)
    {
        return b?male:female;
    }

    public static Gender getGender(int i)
    {
        if(i > 1) return null;
        return (i==1)?male:female;
    }

    public abstract boolean toBoolean();
    public abstract int toInt();

}
