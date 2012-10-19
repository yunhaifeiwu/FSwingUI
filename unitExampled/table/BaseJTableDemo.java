/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import unitExample.divpanel.DivPanelDemo;

/**
 *
 * @author cloud
 */
public class BaseJTableDemo {
     public static  void createPanel() {
        JFrame f = new JFrame("Wallpaper");
        f.getContentPane().setLayout(new FlowLayout());
        Object[] heads = {"d","s","dd"};  
        
         
        DefaultTableModel model = new  DefaultTableModel  (heads,5);         
        JTable t=new JTable();         
        t.setModel(model);
        
       
        //用JScrollPane 能正常顺利显示处JTable
        final JScrollPane scrollPane = new JScrollPane(); 
        scrollPane.setViewportView(t);
        f.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        f.setSize(400, 300);
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo (null);
        Toolkit kit = Toolkit.getDefaultToolkit();//就靠它了
        Image image=kit.createImage("src/unitExample/table/form.png"); 
        f.setIconImage(image);//设置自己的图标
        
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
