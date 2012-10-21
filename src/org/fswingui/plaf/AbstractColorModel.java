/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf;

import java.awt.Color;
import java.util.Map;

/** *
 * 本抽象类。规约了：新旧色系以 指定的主色为主，且新旧都各自仅有一个主色。
 * 
 */
public abstract class AbstractColorModel implements ColorModel{
    private Color oldMainColor;
    private Color newMainColor;
    
    AbstractColorModel(){}

    public AbstractColorModel(Color oldMainColor, Color newMainColor) {
        this.oldMainColor = oldMainColor;
        this.newMainColor = newMainColor;
    }
    

    public Color getOldMainColor() {
        return oldMainColor;
    }

    public void setOldMainColor(Color oldMainColor) {
        this.oldMainColor = oldMainColor;
    }

    public Color getNewMainColor() {
        return newMainColor;
    }

    public void setNewMainColor(Color newMainColor) {
        this.newMainColor = newMainColor;
    }
    
    
    @Override
    public Color getColorFromChange(Color c){
        return getColorFromChangeImpl(c);
    }
    
     
    
    /**
     * 子类必须实现。一个色，从旧的主色所在色系，变换成新的主色所在所系。
     * @param c  转换前的色
     * @return  转换后的色
     */
    protected abstract Color getColorFromChangeImpl(Color c);
    
}
