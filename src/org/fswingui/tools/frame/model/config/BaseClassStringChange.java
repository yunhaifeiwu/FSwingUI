/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model.config;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.utility.Utility;

/**
 *
 * @author cloud
 */
public class BaseClassStringChange {
    public static  Object valueFromString(String className,String value) {
        if(className==null || className.equals("")) return null;
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseClassStringChange.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Object r=null;
        if( className.equals( Integer.class.getName() ))  {
            r=Integer.parseInt(value);
        } else if ( className.equals( Boolean.class.getName() ) )  {
            r=Boolean.valueOf(value);
        } else if ( className.equals( String.class.getName() ) )  {
            r=value;
        } else if ( className.equals(  Float.class.getName() ) )  {
            r=Float.parseFloat(value);
        } else if ( className.equals( Color.class.getName() ) )   {
            r=Utility.fromHexString(value);
        } else if(className.equals(   Font.class.getName() )  )   {
            r=Font.decode(value);
        } else if(className.equals(   ImageIcon.class.getName() )  )   {
            r=new ImageIcon(value);
        }else if(AbstractPaint.class.isAssignableFrom(clazz)  && 
                CurrentData.getPaints()!=null && 
                CurrentData.getPaints().getItems()!=null )  
        {
            r=CurrentData.getPaints().getItems().get(value);
        }
        return r;
        
    }
    
    
    public static String valueToString(Object value) {
        if (value==null) return "";
        String str="";
        String cn=value.getClass().getName();
        if (cn.equals( Color.class.getName() )){ 
            Color c=(Color) value;            
            str=Integer.toHexString(c.getRGB());
        } else if ( AbstractPaint.class.isAssignableFrom(value.getClass())){ 
            AbstractPaint p=(AbstractPaint) value;            
            str=p.getPaintID();
        } else if (cn.equals( ImageIcon.class.getName() )){ 
            ImageIcon icon=(ImageIcon) value;            
            str=icon.getDescription();
        } else {
            str=value.toString();
        }
        return str;
    }
    
}
