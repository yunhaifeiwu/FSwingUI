/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.ui;

 
import java.awt.BasicStroke;
import sun.awt.AppContext;

 
 
import javax.swing.*;
import javax.swing.border.*;
 
import java.awt.event.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.Composite; 
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.text.View;
import javax.swing.AbstractButton;
 
import javax.swing.plaf.metal.MetalButtonUI;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;


/**
 *
 * @author cloud
 */
public class FButtonUI extends  BasicButtonUI {
     protected static  FButtonUI myUI = new FButtonUI();
     private MouseListener mouselistener;
     private BasicButtonListener listener ;
    @Override
    public void installUI(JComponent c) {
         super.installUI(c);
    
         
         
         mouselistener=new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if( ! AbstractButton.class.isAssignableFrom(
                        e.getSource().getClass())
                ) return;                 
                AbstractButton b = (AbstractButton) e.getSource();
                ButtonModel model = b.getModel();
                model.setPressed(true);
                model.setArmed(true);
                b.requestFocus();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if( ! AbstractButton.class.isAssignableFrom(
                        e.getSource().getClass())
                ) return;                 
                AbstractButton b = (AbstractButton) e.getSource();
               
                ButtonModel model = b.getModel();
                model.setPressed(false);
            
                model.setArmed(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {    
                if( ! AbstractButton.class.isAssignableFrom(
                        e.getSource().getClass())
                ) return;               
                AbstractButton b = (AbstractButton) e.getSource();
                ButtonModel model = b.getModel();
                model.setRollover(true);
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if( ! AbstractButton.class.isAssignableFrom(
                        e.getSource().getClass())
                ) return;
                AbstractButton b = (AbstractButton) e.getSource();
                ButtonModel model = b.getModel();
                model.setRollover(false);
            }
        };
        c.addMouseListener(mouselistener);
    }
     
 
    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        c.removeMouseListener(mouselistener);
        
    }
    /**
     * 必须有createUI方法，否则框架无法调用生成
     */
     
    public static  FButtonUI createUI(JComponent c) {    
        return myUI;
    }
    
    @Override
    public void paint(Graphics g, JComponent c){
        AbstractButton b1 = (AbstractButton) c;
        ButtonModel model = b1.getModel();    
        
        int h = c.getHeight();
        int w = c.getWidth();
        Border br=c.getBorder();
 
        UIEngine.Data d=UIEngine.getData(c);

        if ( d!=null){
            if (model.isRollover())
               d.isBright=true;
            UIEngine.paint(g, c, d);
        }  
 
       if ( model.isRollover()){
            c.setBorder(BorderFactory.createLineBorder(c.getBackground().brighter()));
            b1.getBorder().paintBorder(c, g, 3, 3, w-6, h-6);
       } 
       if ( model.isArmed()){
            c.setBorder(BorderFactory.createLineBorder(c.getBackground().brighter(),2,true));
            b1.getBorder().paintBorder(c, g, 3, 3, w-6, h-6);
       } 
      
        
       c.setBorder(BorderFactory.createLineBorder(c.getBackground().darker().darker()));
       b1.getBorder().paintBorder(c, g, 1, 1, w-2, h-2);
       b1.setBorder(br);
       super.paint(g, c);
       
        
    }

    @Override
    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
        super.paintFocus(g, b, viewRect, textRect, iconRect);
        int h = b.getHeight();
        int w = b.getWidth();
        Border br=b.getBorder();      
        b.setBorder(BorderFactory.createLineBorder(b.getBackground().brighter()));        
        b.getBorder().paintBorder(b, g, 2, 2, w-4, h-4);
        b.setBorder(br);
    }

  
  
 
     

}
