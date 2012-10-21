/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.tools.paint;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cloud
 */
public class Parameter  implements Cloneable{
    public Class  type=null;
    public Object value=null;
    public Object defaultValue=null;
    public String title=null;
    public boolean allowNull=false;  
    public ParameterCoding coding=new BaseParameterCoding(this);
    public Parameter clone(){
        Parameter p=null;
        try {
            p=(Parameter) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Parameter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
}
