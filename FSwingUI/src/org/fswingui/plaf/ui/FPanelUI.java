/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.ui;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicPanelUI;
import org.fswingui.plaf.SubjectEntityDoor;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;

/**
 *
 * @author cloud
 */
public class FPanelUI extends BasicPanelUI { 
    // Shared UI object
    protected static  FPanelUI myUI = new FPanelUI();
    private AbstractPaint paint;
    // Shared UI object
    
    public FPanelUI() {
        super();
    }
    
    public FPanelUI(AbstractPaint paint) {
        super();
        this.paint = paint;
    }  
    
    public static ComponentUI createUI(JComponent c) {
        if(myUI == null) {
            myUI = new FPanelUI();
        }
        return myUI;
    }
    
    @Override
    public void installUI(JComponent c) {
         super.installUI(c);
    }

        @Override
    public void paint(Graphics g, JComponent c){
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
