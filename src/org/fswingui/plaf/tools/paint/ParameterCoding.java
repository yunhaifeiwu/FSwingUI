/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.tools.paint;

/**
 *为ParameterCoding扩展，方便把相关的参数类型转成字符串表达，也提供把字符串
 * 解码成原有对象。供参数在SWING组件中显示编辑用
 *  
 */
public interface ParameterCoding {
    public void setParameter(Parameter param);
     /**
     *从字符串解码成value值。注：当SWING 组件显示后，有可能需要类型转换
     */
     public Object valueFromString(String str);
     /**
     * 把value编码成字符串。注：当SWING组件显示时，有可能需要把转换成String
     */
     public String valueToString(Object value) ;
}
