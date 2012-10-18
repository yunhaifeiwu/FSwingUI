/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.metal.MetalComboBoxEditor;
import org.fswingui.pubcomponent.JFontChooser;
import org.fswingui.utility.SpringUtilities;
 
public class MainColortPanel extends JPanel {
   
    
    protected JLabel styleLbl;
    protected JLabel colorLbl;
    protected JComboBox<String> styleBox;
    protected JTextField  colorText;
    protected JButton colorButton;
    protected  Vector styleVector=new Vector();
   
    
    public JPanel init(){ 
        
        styleLbl=new JLabel("当前风格");
        colorLbl=new JLabel("当前主色");
        styleBox=new JComboBox(styleVector) ;
        
       
          
        styleBox.setEditable(true);
        colorText=new JTextField();
        colorButton=new JButton("选色") ;
        JPanel p=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(colorButton);
        styleBox.setSize(100, 25);
        styleBox.setMinimumSize(new Dimension(100,25));
        styleBox.setPreferredSize(new Dimension(100,25));
        colorText.setSize(100, 15);
        colorText.setMinimumSize(new Dimension(100,15));
        colorText.setPreferredSize(new Dimension(100,15));
        
        this.setLayout(new SpringLayout());
        this.add(styleLbl);
        this.add(styleBox);
        Component c1=Box.createVerticalGlue();
        this.add(c1);
        this.add(colorLbl);
        this.add(colorText);
        this.add(colorButton); 
//        colorButton.setAutoscrolls(false);
         
        SpringUtilities.makeCompactGrid(this,
                                        2,3, //rows, cols
                                        15,15,        //initX, initY
                                        10, 10);       //xPad, yPad
//        this.setPreferredSize(new Dimension(200,100));
        return this;
    }
    
    //<editor-fold defaultstate="collapsed" desc="test"> 
    public static void createPanel(){

        
        JFrame f = new JFrame("Wallpaper");
       
        //用JScrollPane 能正常顺利显示处JTable
        MainColortPanel  panel = new MainColortPanel( ); 
        panel.init(); 
        f.add(panel);
        //        javax.swing.JColorChooser.showDialog(scrollPane, VIEWPORT, Color.yellow);
        f.setSize(300, 120);
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
