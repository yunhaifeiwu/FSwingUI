/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.gui.component.adapter;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.gui.component.ComDivPanel;
import org.fswingui.tools.gui.component.extra.ComDiv;
import org.fswingui.tools.gui.component.extra.Div;

/**
 *
 * @author cloud
 */
public class UpDispatchMouseEvent implements MouseMotionListener,MouseListener
        
{
    
    @Override
    public void mouseDragged(MouseEvent e) {
        upMouseEvent(e);      
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        upMouseEvent(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        upMouseEvent(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        upMouseEvent(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        upMouseEvent(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        upMouseEvent(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
         upMouseEvent(e);
    }
    
    private void upMouseEvent(MouseEvent e){
        Component c1 = e.getComponent();
        BaseData rbd= CurrentData.getRootBaseData(c1.getName());
        Div rdiv=rbd.getDiv();
        
        if (rdiv.getClass().isAssignableFrom(ComDiv.class)){
            ComDivPanel rview=(ComDivPanel) rdiv.getView();
            Div currDiv=CurrentData.getDiv(c1.getName());
            String rootName=rdiv.getName();
            
           
            BaseData bd=currDiv.getCurrentData().getBaseData(c1.getName());
            if (bd==null) return;
            
            BaseData pcd=bd.getParent();                     
            Component parent=bd.getParentDivView();
            while (parent!=null && !parent.getName().equals(rootName) ){
                e=SwingUtilities.convertMouseEvent(e.getComponent(),e, parent);                 
                parent=bd.getParentDivView();
                bd=pcd;
                if(bd!=null)pcd=bd.getParent();
                
            }
 
            rview.dispatchEvent(
                SwingUtilities.convertMouseEvent(e.getComponent(),e, rview)
            );
 
            
        }
    }
}
