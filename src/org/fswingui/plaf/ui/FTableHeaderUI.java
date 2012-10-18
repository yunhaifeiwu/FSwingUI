/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.JTableHeader;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;

/**
 *
 * @author Administrator
 */
public class FTableHeaderUI extends BasicTableHeaderUI {
    // Shared UI object
    protected static  FTableHeaderUI myUI;
    private AbstractPaint paint;
    private MouseListener myMouseListener;
    // Shared UI object

    
    
    public FTableHeaderUI() {
        super();
    }
    
    public FTableHeaderUI(AbstractPaint paint) {
        super();
        this.paint = paint;
    }  
    
    public static ComponentUI createUI(JComponent c) {
        if(myUI == null) {
            myUI = new FTableHeaderUI();
        }
        return myUI;
    }
    
    @Override
    public void installUI(JComponent c) {
        
         super.installUI(c);
         
         
         myMouseListener = new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                FTableHeaderUI.this.header=  (JTableHeader) e.getSource();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        
        };
        c.addMouseListener(myMouseListener);
    }

    
   
    
    @Override
    public void paint(Graphics g, JComponent c){
        this.header=(JTableHeader) c;
        installKeyboardActions();
        UIEngine.Data d=UIEngine.getData(c);
        
        if ( d!=null && d.subjectEntity!=null ){ 
            UIEngine.paint(g, c, d);
            
        } else if(paint!=null) {
            try {
                paint.paint(g, c);
            } catch (Exception ex) {
                Logger.getLogger(FPanelUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        super.paint(g, c);
    }

    public AbstractPaint getPaint() {
        return paint;
    }

    public void setPaint(AbstractPaint paint) {
        this.paint = paint;
    }
    
}
