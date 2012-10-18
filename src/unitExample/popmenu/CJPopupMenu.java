/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.popmenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



/**
 * <p>Copyright: Copyright (c) 2002</p>
 * @author Turbo Chen
 * @version 1.01
 */
public class CJPopupMenu extends JPopupMenu
{

    private static CJPopupMenu popupMenu;
    private static JButton btnMenu = new JButton("ddd"); 
    public void show(Component invoker,int x, int y)
    {
        Point ps = invoker.getLocationOnScreen();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int mw = this.getPreferredSize().width;
        int mh = this.getPreferredSize().height;
        int newX = x;
        int newY = y;
        int aX = ps.x+x+mw;
        int aY = ps.y+y+mh;
        if ( aX>d.width )
            newX = x -(aX - d.width);
        if ( aY>d.height )
            newY = y -(aY - d.height);
        super.show(invoker,newX,newY);
    }
    
    public static void  createPanel(){
        JFrame f = new JFrame("Wallpaper");   
        f.setSize(400, 300);
        JPanel p = (JPanel) f.getContentPane();
//        p.setLayout(new AbsoluteLayout());
        
   
        p.setLayout(null);
        
        popupMenu = new CJPopupMenu();
        btnMenu.setBounds(20, 30, 50, 100);
        f.add(btnMenu);
        JMenu menu0 = new JMenu("所有服务"); 
        JMenu menu1 = new JMenu("工具");
        JMenu menu2 = new JMenu("QQ会员");
        JMenu menu3 = new JMenu("我的QQ中心");   
        popupMenu.add(menu0);
        popupMenu.add(menu1);
        popupMenu.add(menu2);
        popupMenu.add(menu3); 
       
        
        btnMenu.addMouseListener(new MouseAdapter(){//状态选择按钮事件监听  

               
                public void mousePressed(MouseEvent e) {  
                   
                    popupMenu.show(btnMenu, e.getX(), e.getY());
                }   
        }); 

//        p.add(fp,new AbsoluteConstraints(10, 10, 200, 100));
   
        
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
}


