/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.gui.component.adapter.DivSta;
import org.fswingui.tools.gui.component.adapter.ScaleMouseAdapter;
import org.fswingui.tools.gui.component.adapter.FocusAndDropMouseAdapter;
import org.fswingui.tools.gui.component.adapter.UpDispatchMouseEvent;


public class ComDivPanel extends JPanel implements DivSta {
    //<editor-fold desc="变量">
    private JPanel parentPanel;
    private FocusAndDropMouseAdapter focusAndDropMouseAdapter;
    private ScaleMouseAdapter scaleMouseAdapter;
    private UpDispatchMouseEvent upDispatchMouseEvent;
    private CurrentData currentData;
     //</editor-fold>
    
    public ComDivPanel(JPanel parent,String name){
        super();
        this.parentPanel=parent;
        if (this.parentPanel !=null){            
            focusAndDropMouseAdapter= new FocusAndDropMouseAdapter(this.parentPanel,this);
            this.addMouseListener(focusAndDropMouseAdapter);
            scaleMouseAdapter=new ScaleMouseAdapter(this.parentPanel,this);
            this.addMouseMotionListener(scaleMouseAdapter); 
        }
        
        super.setName(name);
        this.setOpaque(false);
        this.setLayout(null);
//        this.setBorder(BorderFactory.createLineBorder(Color.red));
    }
    
    //<editor-fold desc="事件">

    public CurrentData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(CurrentData currentData) {
        this.currentData = currentData;
    }
    
    
    public void addScaleMouseAdapte() {
         this.addMouseMotionListener(this.scaleMouseAdapter);
    }

 
    public void removeScaleMouseAdapte() {
        this.removeMouseMotionListener(scaleMouseAdapter);
        scaleMouseAdapter=null;
    }

 
    public void addFocusAndDropMouseAdapter() {
        this.addMouseListener(this.focusAndDropMouseAdapter);
        this.focusAndDropMouseAdapter=new FocusAndDropMouseAdapter(this.parentPanel,this) ;
    }


    public void removeFocusAndDropMouseAdapter() {
        focusAndDropMouseAdapter.removeDropMouseAdapter();
        this.removeMouseListener(focusAndDropMouseAdapter); 
    }
    
    public void addUpDispatchMouseEvent(){
        if(upDispatchMouseEvent==null){
            upDispatchMouseEvent=new UpDispatchMouseEvent();
        }    
        this.addMouseListener(upDispatchMouseEvent);
        this.addMouseMotionListener(upDispatchMouseEvent);
    }
    
    public void removeUpDispatchMouseEvent(){
        this.removeMouseListener(upDispatchMouseEvent);
        this.removeMouseMotionListener(upDispatchMouseEvent);
    }
    
    //</editor-fold>

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.getBorder() !=null) 
            this.getBorder().paintBorder(this, g, 0, 0, this.getWidth(), this.getHeight());
    }
    
    //<editor-fold defaultstate="collapsed" desc="demo test "> 
    public static  void createPanel() {
        JFrame f = new JFrame("Wallpaper");   
        f.setSize(400, 300);
        JPanel p = (JPanel) f.getContentPane();
//        p.setLayout(new AbsoluteLayout());        
        ComDivPanel fp=new ComDivPanel(p,"11");
      
        f.add(fp);        
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo (null);
        f.setVisible (true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {      
                createPanel();
            }
        });
    } 
   //</editor-fold>
    
}
