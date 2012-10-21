/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.AbstractLayoutCache;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.ui.modify.DefaultTreeCellRendererM;


/**
 *
 * @author cloud
 */
public class FTreeUI extends BasicTreeUI{    
    public static AbstractPaint root; 
    private TreeCellRenderer oldRenderer=null;
    private TreeCellRenderer newRenderer=null;
    protected static  FTreeUI myUI = new FTreeUI();
    private Graphics myg;
    private TreeModel oldModel=null;
    private  AbstractLayoutCache  oldTreeState=null;
    private MouseListener myMouseListener;
    
    /**
     * 必须有createUI方法，否则框架无法调用生成
     */
    public static  FTreeUI createUI(JComponent c) {   
        return myUI;
    }
    
    public FTreeUI(){
        super();
    }
 
    public void installUI(JComponent c)
    {    
        super.installUI(c);     
        oldRenderer=tree.getCellRenderer();
        newRenderer=new DefaultTreeCellRendererM();
        tree.setCellRenderer(newRenderer);
        myMouseListener = new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                if(tree.isEditable() || tree.isEditing()) return;
                treeState = createLayoutCache();
                configureLayoutCache();
            }

            @Override
            public void mouseExited(MouseEvent e) {  }
        
        };
        c.addMouseListener(myMouseListener);
            
        
    }
    public void uninstallUI(JComponent c)
    {   
        
        newRenderer =null;
        tree.setCellRenderer(oldRenderer);
        if(myMouseListener!=null){
            c.removeMouseListener(myMouseListener);
            myMouseListener=null;
        }
        super.uninstallUI(c);
    } 
   
    public void repaint(){
        if(myg==null ) return;
        treeState = createLayoutCache();
        configureLayoutCache();
        paint(myg,tree);

    }
    @Override
    public  void paint(Graphics g,JComponent c) {  
        this.myg=g;
        
         
//        treeState = createLayoutCache();
//        configureLayoutCache();
        
        UIEngine.Data d=UIEngine.getData(c);
        
        if ( d!=null){
            UIEngine.paint(g, c, d);
        }  
        
        super.paint(g, c);
     }
}
