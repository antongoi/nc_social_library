/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 *
 * @author Костя
 */
public interface testLocalHome extends EJBLocalHome {
    
    test.testLocal create()  throws CreateException;

}
