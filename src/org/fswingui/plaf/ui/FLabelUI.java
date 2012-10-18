/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.plaf.metal.MetalLabelUI;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import sun.swing.SwingUtilities2;

/**
 *
 * @author cloud
 */
public class FLabelUI extends BasicLabelUI{
    public static AbstractPaint root;
    protected static  FLabelUI myUI = new FLabelUI();
    private Rectangle paintIconR = new Rectangle();
    private static Rectangle viewRect = new Rectangle();
    private static Rectangle textRect = new Rectangle();
    private static Rectangle iconRect = new Rectangle();
    private int x,y,w,h;
    /**
     * 必须有createUI方法，否则框架无法调用生成
     */
    public static  FLabelUI createUI(JComponent c) {        
//        initPaint();
        return myUI;
    }
    
        public static void initPaint()  {
        try {
            root=new CrystalPaint();    
            root.addArg("highlightRatio", 0.2f);            
            root.addArg("azimuth", 0);
            root.addArg("ColorSource", Color.GREEN);
            root.addArg("ColorDestination", Color.WHITE);
        } catch (Exception ex) {
            Logger.getLogger(FLabelUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    @Override
    public  void paint(Graphics g,JComponent c) {  
         c.setOpaque(false);
         UIEngine.Data d=UIEngine.getData(c);
//
//        if ( d!=null){
//            UIEngine.paint(g, c, d);
//        }  
         super.paint(g, c);
       
       
     }
      
    
}

