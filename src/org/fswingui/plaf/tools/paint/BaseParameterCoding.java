/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.tools.paint;

import java.awt.Color;
import java.awt.Font;
import org.fswingui.utilities.Utility;
import org.fswingui.plaf.tools.paint.Parameter;
import org.fswingui.plaf.tools.paint.ParameterCoding;

/**
 *
 * @author cloud
 */
public class BaseParameterCoding implements ParameterCoding {
    
    private Parameter param;

    public BaseParameterCoding(){}
    public BaseParameterCoding(Parameter param) {
        this.param = param;
    }
    
    
    @Override
    public Object valueFromString(String str) {
        if ( param==null) return null;
        Object r=null;
        if( param.type.getName().equals(
                Integer.class.getName() ))
        {
            r=Integer.parseInt(str);
        } else if ( param.type.getName().equals(
                String.class.getName() ) )  
        {
            r=str;
        } else if ( param.type.getName().equals(
                Float.class.getName() ) )  
        {
            r=Float.parseFloat(str);
        } else if ( param.type.getName().equals(
                Color.class.getName() ) )  
        {
            r=Utility.fromHexString(str);
        } else if(param.type.getName().equals(
                Font.class.getName() )  )
        {
            r=Font.decode(str);
        }
        return r;
    }

    @Override
    public String valueToString(Object value) {
        if (value==null) return "";
        String str="";
        if ( param.type.getName().equals(
                Color.class.getName() ))
        { 
            Color c=(Color) value;
            
            str=Integer.toHexString(c.getRGB());
        } else {
            str=value.toString();
        }
        return str;
    }

    @Override
    public void setParameter(Parameter param) {
        this.param=param;
    }
    
}
