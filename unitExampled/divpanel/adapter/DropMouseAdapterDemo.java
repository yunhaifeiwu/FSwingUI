
package unitExample.divpanel.adapter;

import org.fswingui.tools.gui.component.adapter.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import java.lang.Math;
import javax.swing.JPanel;


/**
 *该鼠标适配器，需传入父JPanle对象，该对象必须使用了绝对布局
 * @author cloud
 */
public class DropMouseAdapterDemo  extends MouseAdapter {
    private Point p;
    private JPanel root ; 
    private JPanel movePanel ; 
    private int cxt=0;
    private int cxt1=0;
    /**
     * 
     * @param root 使用了AbsoluteLayout布局管理器，且是被监听对象的父容器
     */
    public   DropMouseAdapterDemo(JComponent root ,JComponent movePanel){
        
        this.root=(JPanel) root;
        this.movePanel=(JPanel) movePanel;
    }
 
    /**
     * 记录鼠标按下时的坐标
     */
    @Override
    public void mousePressed(MouseEvent e){
        if(cxt>0){cxt=0;return;}
        cxt++;
        Component c1 = e.getComponent();
        c1.setFocusable(true);
        p=new Point(e.getX(),e.getY());
    }
    
    /**
     * 鼠标放松时，记下需要移动的位置，并显示
     */
    @Override
    public void mouseReleased(MouseEvent e){
        if(cxt1>0){cxt1=0;return;}
        cxt1++;
        
        Component c1 = e.getComponent();        
        Color cc=Color.red;
        Point p1=new Point(e.getX(),e.getY());     
       
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
        root.add(movePanel, 0);

        
        
        //重新显示后的样子
        root.repaint();
        root.revalidate();         
        
    }
}
