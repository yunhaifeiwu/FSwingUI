/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame;

import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.frame.part.CreateMenuBar;

/**
 *创建主框架的主要部份的接口。至少需要注入一个来自主框架的DataBus.
 * @author cloud
 */
public interface CreatePart {
    /**
     * 创建指定部份的部件
     * @param partName 要创建的部份
     */
 
    public final static String NORTH_PANEL="northPanel";
    public final static String SOUTH_PANEL="southPanel";
    public final static String WEST_PANEL="westPanel";
    public final static String EAST_PANEL="eastPanel";
    public final static String CENTER_PANEL="centerPanel";
    public final static String MENU_BAR="menuBar";
    
    public Object create(String partName);
    public void setDataBus(DataBus dataBus);
    public DataBus getDataBus();
    public void add(AbstractCreatePart createPart);
    public void remover(String key);
   
}
