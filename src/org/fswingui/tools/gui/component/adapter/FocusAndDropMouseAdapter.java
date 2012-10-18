
package org.fswingui.tools.gui.component.adapter;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import java.lang.Math;
import javax.swing.JPanel;
import org.fswingui.tools.gui.component.DivPanel;


/**
 *该鼠标适配器，需传入父JPanle对象，该对象必须使用了绝对布局
 * @author cloud
 */
public class FocusAndDropMouseAdapter  implements MouseListener {
    private JPanel root ; 
    private JPanel movePanel ; 
    private DropMouseAdapter dropMouseAdapter;
    /** 
     * @param root 使用了AbsoluteLayout布局管理器，且是被监听对象的父容器
     */
    public   FocusAndDropMouseAdapter(JComponent root ,JComponent movePanel){        
        this.root=(JPanel) root;
        this.movePanel=(JPanel) movePanel;
        if (this.movePanel!=null) {
            dropMouseAdapter=new DropMouseAdapter (root,movePanel);
            this.movePanel.addMouseMotionListener(dropMouseAdapter);
        }
    }
    public void removeDropMouseAdapter(){
         if (this.movePanel!=null) {
            this.movePanel.removeMouseMotionListener(dropMouseAdapter);
        }
    }
    public void addDropMouseAdapter(){
        if (this.movePanel!=null) {
            this.movePanel.addMouseMotionListener(dropMouseAdapter);
        }
    }
 
    /**
     * 记录鼠标按下时的坐标
     */
    @Override
    public void mousePressed(MouseEvent e){
        Component c1 = e.getComponent();
        
        root.repaint();
        root.revalidate();   
        if ( ( e.getX()>5 &&  e.getX()<(c1.getWidth()-5) )
                && (e.getY()>5 && e.getY()<(c1.getHeight()-5) ) 
                ){//水平与垂直
            c1.setCursor(new Cursor( Cursor.MOVE_CURSOR)); 
        }  
        
    }
    
    /**
     * 鼠标放松时，记下需要移动的位置，并显示
     */
    @Override
    public void mouseReleased(MouseEvent e){
        Component c1 = e.getComponent();
        c1.setCursor(new Cursor( Cursor.DEFAULT_CURSOR));     
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
