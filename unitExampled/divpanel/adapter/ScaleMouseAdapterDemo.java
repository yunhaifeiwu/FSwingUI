/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.divpanel.adapter;

import org.fswingui.tools.gui.component.adapter.*;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author cloud
 */
public class ScaleMouseAdapterDemo  extends MouseMotionAdapter {
    private Point p;
    private JPanel root ; 
    private JPanel movePanel ; 
    private int dragBl=0;
    private int cxt=0;
    private int cxt1=0;

    public   ScaleMouseAdapterDemo(JComponent root ,JComponent movePanel){  
        super();
        this.root=(JPanel) root;
        this.movePanel=(JPanel) movePanel;
    }
        
  

    @Override
    public void mouseDragged(MouseEvent e) {
        if(cxt>0){cxt=0;return;}
        cxt++;
        Component c1 = e.getComponent();
        int x,y;
        switch(dragBl){
            case 1://水平与垂直
                x=e.getX();
                y=e.getY()-c1.getHeight()+movePanel.getHeight();
                movePanel.setSize(x,y);                
                root.repaint();
                root.revalidate(); 
            break;
            case 2://水平
                x=e.getX();
                y=movePanel.getHeight();
                movePanel.setSize(x,y);
                root.repaint();
                root.revalidate();
            break;
            case 3://垂直
                x=movePanel.getWidth();
                y=e.getY()-c1.getHeight()+movePanel.getHeight();
                movePanel.setSize(x,y);
                root.repaint();
                root.revalidate();  
            break;
        }
                
   
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(cxt1>0){cxt1=0;return;}
        cxt1++;
        Component c1 = e.getComponent();
        Point p1=new Point(e.getX(),e.getY());   
        dragBl=0;
        if ( (Math.abs( c1.getWidth()-e.getX())<5  )
                && (Math.abs(c1.getHeight()-e.getY())<5 ) 
                ){//水平与垂直
            dragBl=1;
            c1.setCursor(new Cursor( Cursor.NW_RESIZE_CURSOR)); 
            
        } else if (Math.abs( c1.getWidth()-e.getX())<5 ){//水平
             dragBl=2;
             c1.setCursor(new Cursor( Cursor.W_RESIZE_CURSOR)); 
        } else if(Math.abs(c1.getHeight()-e.getY())<5){//垂直            
             dragBl=3;
            c1.setCursor(new Cursor( Cursor.S_RESIZE_CURSOR)); 
        } else   {
             c1.setCursor(new Cursor( Cursor.DEFAULT_CURSOR)); 
        }
    }
}
