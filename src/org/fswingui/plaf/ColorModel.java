/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf;

import java.awt.Color;

/**
 *
 * @author cloud
 */
public interface ColorModel {
    /**
     * 本接口，为系统得到指定主色 色系变换而作。<br>
     * 当需要其它诸如HSB什么的参数，请自行转换。附：java自身可在rgb与hsv中转换。
     * @param c  转换前的色
     * @return  转换后的色
     */
    public Color getColorFromChange(Color c);
}
