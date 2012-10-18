
package org.fswingui.tools.gui.component.adapter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import java.lang.Math;
import javax.swing.JPanel;


/**
 *该鼠标适配器，需传入父JPanle对象，该对象必须使用了绝对布局
 * @author cloud
 */
public class DropMouseAdapter  implements MouseMotionListener {
    private Point p;
    private JPanel root ; 
    private JPanel movePanel ; 
    /**
     * 
     * @param root 使用了AbsoluteLayout布局管理器，且是被监听对象的父容器
     */
    public   DropMouseAdapter(JComponent root ,JComponent movePanel){ 
        this.root=(JPanel) root;
        this.movePanel=(JPanel) movePanel;
    }
 
    /**
     * 记录鼠标按下时的坐标
     */
    @Override
    public void mouseDragged(MouseEvent e){
        
        Component c1 = e.getComponent();
//        movePanel.setFocusable(true);
        if ( !(( e.getX()>5 &&  e.getX()<(c1.getWidth()-5) )
                && (e.getY()>5 && e.getY()<(c1.getHeight()-5) ) 
               ) ){//水平与垂直
            return;
        } 
        Point p1=new Point(e.getX()+movePanel.getLocation().x,e.getY()+movePanel.getLocation().y); 
        if (p!=null && !p.equals(p1)){

            int x= movePanel.getLocation().x+p1.x-p.x;
            int y=movePanel.getLocation().y+p1.y-p.y;

            int intv =5;//越界后，最少能让用户操作的像素值
            //坐标超越左边界检查
            if (x+c1.getWidth()<0  ){
                x=intv-c1.getWidth();
            }
            //坐标超越上边界检查
            if (y+c1.getHeight()<0  ){
                y=intv-c1.getHeight();
            }
            //坐标超越右边界检查
            if (x>root.getWidth()  ){
                x=root.getWidth()-intv;
            }
            //坐标超越下边界检查
            if (y>root.getHeight()  ){
                y=root.getHeight()-intv;
            }

            movePanel.setBounds(x, y, movePanel.getWidth(),movePanel.getHeight());
//            root.add(movePanel, 0);
        }
        
              
        
        //重新显示后的样子
        root.repaint();
        root.revalidate(); 
        
        p=p1;
    }
    

    @Override
    public void mouseMoved(MouseEvent e){
        
        Component c1 = e.getComponent();
        if ( ( e.getX()>5 &&  e.getX()<(c1.getWidth()-5) )
                && (e.getY()>5 && e.getY()<(c1.getHeight()-5) ) 
                ){//水平与垂直
            p=null;
        }  
                
        if (  p==null){
            c1.setCursor(new Cursor( Cursor.DEFAULT_CURSOR)); 
        }
            
        

           
        
    }
}
