/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.CellRendererPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.plaf.multi.MultiListUI;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import sun.swing.DefaultLookup;

/**
 *
 * @author cloud
 */
public class FListUI extends BasicListUI {
    public static AbstractPaint root; 
    protected static  FListUI myUI ;
    private MouseListener myMouseListener;
    private ListSelectionListener mySelectionListener;
     
    private JList list1;
    /**
     * 必须有createUI方法，否则框架无法调用生成
     */
    public static  FListUI createUI(JComponent c) {    
//        initPaint();
        if(myUI==null)myUI = new FListUI();
        return myUI;
    }
    
//    protected void installDefaults(){
//        super.installDefaults();
//    }
//    protected void uninstallDefaults(){
//        super.uninstallDefaults();
//    }
    public void installUI(JComponent c)
    {    
        super.installUI(c);     
        list=(JList) c;
        list1=list;
        list.setOpaque(true);
        mySelectionListener =new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                FListUI.this.list1=(JList) e.getSource();
                 list=list1;
                 FListUI.super.updateLayoutState();
            }
        
        };
        list.addListSelectionListener(mySelectionListener);
         
        myMouseListener = new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                 FListUI.this.list1=(JList) e.getSource();
                 list=list1;
                 FListUI.super.updateLayoutState();
            }

            @Override
            public void mouseExited(MouseEvent e) {  }
        
        };
        c.addMouseListener(myMouseListener);
       
//        LookAndFeel.installProperty(list, "opaque", Boolean.FALSE);
        
        ListCellRenderer renderer = list.getCellRenderer();
       
        
  
        JComponent c1 = (JComponent) renderer.getListCellRendererComponent(list,
             null, 
             0, 
             false, 
             false
        );

        c1.setOpaque(false) ;
  
        
         
        
    }
    public void uninstallUI(JComponent c)
    {
        if(myMouseListener!=null){
            c.removeMouseListener(myMouseListener);
            myMouseListener=null;
        }
        if(mySelectionListener!=null){
            list.removeListSelectionListener(mySelectionListener);
            mySelectionListener=null;
        } 
        super.uninstallUI(c);
    }
//    public FListUI(){
//        super();
//    }
    public static void initPaint()  {
        try {
            root=new CrystalPaint(); 
            
            root.addArg("azimuth", 0);
            root.addArg("ColorSource", Color.GREEN.darker());
        } catch (Exception ex) {
            Logger.getLogger(FListUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   
    
    @Override
    public  void paint(Graphics g,JComponent c) {  
        super.list=(JList) c;
//        super.list=list1;
        
        UIEngine.Data d=UIEngine.getData(c);

        if ( d!=null){
            UIEngine.paint(g, c, d);
        }  
    
//     
//         ListCellRenderer renderer = list.getCellRenderer();
         super.updateLayoutStateNeeded=1;
//         super.updateLayoutState();//等同上面的属性设置。
         
         super.paint(g, list);
     }

  
    
 
    
   
}
