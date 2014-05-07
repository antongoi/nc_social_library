/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Антон
 */
public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
    private Gender gender;
    private boolean confirmed;
    private boolean banned;
    private String registrationDate;
    private boolean notify;
    private List<Role> roles;

    private String getAllRoles(){
        if(roles.size()<1) return "";
        String rolesString = "";
        for (Role r : roles) rolesString+=r.getName()+" & ";
        return rolesString.substring(0, rolesString.length()-3);
    }

    @Override
    public String toString(){
        return id + " " + firstName + " " + lastName + " " + email + " " + login + " " + password + " " + gender
                + " " + confirmed + " " + banned + " " + registrationDate + " " + notify + " " + getAllRoles();
    }

    public List<String> toStringList(){
        List<String> result = new ArrayList<String>();
        result.add(String.valueOf(id));
        result.add(firstName);
        result.add(lastName);
        result.add(email);
        result.add(login);
        result.add(password);
        result.add(String.valueOf(gender.toInt()));
        result.add(confirmed?"1":"0");
        result.add(banned?"1":"0");
        result.add(registrationDate);
        result.add(notify?"1":"0");
        result.add(getAllRoles());

        return result;
    }

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate.substring(0, 10);
    }

//    public void setRegistrationDate(String mask, String registrationDate) {
//        this.registrationDate = registrationDate.substring(0, 10);
//    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void freeRoles() {
        this.roles = new ArrayList<Role>();
    }

    //private
//    public static String applyMaskToDate(String mask, String date){
//        int y = 0, m = 0, d = 0;
//        char separator;
//        for(int i = 0; i < mask.length(); i++){
//            char c = mask.charAt(i);
//            switch(c){
//                case 'y': y++;
//                case 'd': d++;
//                case 'm': m++;
//                default: separator = c;
//            }
//        }
//    }

}
