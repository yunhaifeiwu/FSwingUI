/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.MenuItemUI;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.JTableHeader;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;

/**
 *
 * @author cloud
 */
public class FSwingLF extends MetalLookAndFeel 
    implements java.io.Serializable
{
    public static final int DEFALT_LOAD=1;
    public static final int SET_LOAD=0;
    private static UIDefaults myTable;
    private static int initStyle=0;
//    private NimbusLookAndFeel nlf=new NimbusLookAndFeel();
    
    @Override
    public String getID() { return "FSwingLF"; }
    @Override
    public String getName() { return "FSwingUI Look and Feel"; }
    @Override
    public String getDescription() { return "主题式皮肤"; }
    @Override
    public boolean isNativeLookAndFeel() { return false; }
    @Override
    public boolean isSupportedLookAndFeel() { return true; }    
   
    public void setCurrentStyle(String styleid){
        UIManager.put(UIEngine.CURRENT_STYLEID,styleid);
    }

    /**
     * 加载方式，直接加载程序写死的参数，还是写配置文件中的参数。<br>
     * DEFALT_LOAD-- ---加载程序中写死的参数<br>
     * SET_LOAD--------加程配置文件中的参数<br>
     */
    public static int getInitStyle() {
        return initStyle;
    }

    /**
     * 加载方式，直接加载程序写死的参数，还是写配置文件中的参数。<br>
     * DEFALT_LOAD-- ---加载程序中写死的参数<br>
     * SET_LOAD--------加程配置文件中的参数<br>
     */
    public static void setInitStyle(int initStyle) {
        FSwingLF.initStyle = initStyle;
    }
    
    
   
    @Override
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);       
        
        table.put("ButtonUI", FButtonUI.class.getName());   
        table.put("PanelUI", FPanelUI.class.getName());   
        table.put("ListUI", FListUI.class.getName());  
        table.put("TreeUI", FTreeUI.class.getName());  
        table.put("LabelUI", FLabelUI.class.getName());  
        table.put("TableHeaderUI", FTableHeaderUI.class.getName());  
        table.put("MenuBarUI", FMenuBarUI.class.getName());  
        UIManager.put("MenuBarUI",  FMenuBarUI.class.getName());
        
//        table.put("MenuItemUI", FMenuItemUI.class.getName());  
        
//        table.put("MenuUI", FMenuUI.class.getName());  
    }

    @Override
    protected void initComponentDefaults(UIDefaults table) {        
        super.initComponentDefaults(table);
        boolean initBL=UIEngine.init(initStyle);        
        FSwingLF.myTable=table;
    }     
 
    public static Object getUIOfType(ComponentUI ui, Class klass) {
        if (klass.isInstance(ui)) {
            return ui;
        }
        return null;
    }
}
