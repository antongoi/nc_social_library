/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sociallibrary.registration;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Костя
 */
public class SecurityHash {

    public static String getMd5(String toMd5) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String sbStr = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(toMd5.getBytes("UTF-8"));
            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
                sbStr = sb.toString();
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecurityHash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sbStr;
    }

    public static String getPass(int from, int to) {
        String pass = "";
        Random r = new Random();
        int cntchars = from + r.nextInt(to - from + 1);

        for (int i = 0; i < cntchars; ++i) {
            char next = 0;
            int range = 10;

            switch (r.nextInt(3)) {
                case 0: {
                    next = '0';
                    range = 10;
                }
                break;
                case 1: {
                    next = 'a';
                    range = 26;
                }
                break;
                case 2: {
                    next = 'A';
                    range = 26;
                }
                break;
            }

            pass += (char) ((r.nextInt(range)) + next);
        }

        return pass;
    }
}
