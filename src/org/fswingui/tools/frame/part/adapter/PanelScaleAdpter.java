/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.adapter;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * 针对父容器的布局管理器为bordLayout而设，且它与其子组件的固定间隔至少为3
 * @author cloud
 */
public class PanelScaleAdpter  extends MouseMotionAdapter{
    /**
     * 左边可拖放
     */
    public static final int STYLE_LEFT=0;
    /**
     * 右边可拖放
     */
    public static final int STYLE_RIGHT=1;
    /**
     *上边可拖放
     */
    public static final int STYLE_TOP=2;
    /**
     * 下边可拖放
     */
    public static final int STYLE_BOTTOM=3;
    
    /**
     * 本事件中的组件，与其子组件的间隔设定。默认为3
     */
    private int interval =3;
    
    private int cxt=0;
    private int cxt1=0;
    
    private int dragBl=0;   
    
    private int style;

    public int getDragBl() {
        return dragBl;
    }

    public void setDragBl(int dragBl) {
        this.dragBl = dragBl;
    }
   
    /**
     * 本事件中的组件，与其子组件的间隔设定。默认为3。这里为获得该参数
     */
    public int getInterval() {
        return interval;
    }
    
    /**
     * 本事件中的组件，与其子组件的间隔设定。默认为3。这里为设置参数
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }
    
    
    /**
     * 设置可拖动样式
     */
    public void setStyle(int style) {
        this.style = style;
    }
    
    
    
    public PanelScaleAdpter(){
        
    }
    public PanelScaleAdpter(int style){        
        this.style=style;
    }
 
 
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if(cxt>0){cxt=0;return;}
        cxt++;
        Component c1 = e.getComponent();
        int x,y;
        switch(dragBl){
            case 1://右下角 水平与垂直
                x=e.getX();
                y=e.getY();
                c1.setSize(x,y);  
                c1.setPreferredSize(new Dimension(x,y));
                c1.getParent().repaint();
                c1.getParent().revalidate(); 
            break;
            case 2 ://右边 水平
                x=e.getX();
                y=c1.getHeight();
                c1.setSize(x,y);
                c1.setPreferredSize(new Dimension(x,y));
                c1.getParent().repaint();
                c1.getParent().revalidate();
            break;
            case 3://下边 垂直
                x= c1.getWidth();
                y=e.getY();
                c1.setSize(x,y);
                c1.setPreferredSize(new Dimension(x,y));
                c1.getParent().repaint();
                c1.getParent().revalidate();  
            break;
            case 4://左边 水平
                if (e.getX()==0) break;
                    
                if (e.getX()<0){
                    x=c1.getWidth()-e.getX();
                } else {
                    x=c1.getWidth()-e.getX();
                }
                y=c1.getHeight(); 
                c1.setSize(x,y);
                c1.setPreferredSize(new Dimension(x,y));
               
                c1.getParent().repaint();
                c1.getParent().revalidate();  
            break;
            case 5://上边 垂直                                
                if (e.getY()==0) break;    
                
                if (e.getY()<0){
                    y=c1.getHeight()-e.getY();
                } else {
                    y=c1.getHeight()-e.getY();
                }                
                x=c1.getWidth();
                
                c1.setSize(x,y);
                c1.setPreferredSize(new Dimension(x,y));
                c1.getParent().repaint();
                c1.getParent().revalidate();  
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
        if ( (Math.abs( c1.getWidth()-e.getX())<interval ) 
                && (style==STYLE_LEFT)     ){//右边 水平
            dragBl=2;
            c1.setCursor(new Cursor( Cursor.W_RESIZE_CURSOR)); 
        } else if( (Math.abs(c1.getHeight()-e.getY())<interval)
                && (style==STYLE_TOP)     ){//下边 垂直            
            dragBl=3;
            c1.setCursor(new Cursor( Cursor.S_RESIZE_CURSOR)); 
        }else   if( (Math.abs(e.getX())<interval ) 
                && (style==STYLE_RIGHT)  ){//左边，水平
            //由于鼠标事件，仅能在自身的组件中探测，而该组件又要完全存放其他组件，因此
            //仅能探测到边框的变化，估这里要起作用。该组件与它的子组件要留有边界，至少
            //要大于这里的条件值
            
            dragBl=4;
            c1.setCursor(new Cursor( Cursor.W_RESIZE_CURSOR));
        }else if( (Math.abs(e.getY())<interval )
               && (style==STYLE_BOTTOM)   ){//上边， 垂直 
            dragBl=5;
            c1.setCursor(new Cursor( Cursor.S_RESIZE_CURSOR));
        }else {
            c1.setCursor(new Cursor( Cursor.DEFAULT_CURSOR)); 
            
        }
       
    }
}
