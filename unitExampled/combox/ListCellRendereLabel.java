/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.combox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

/**
 * 实现选中与非选中变色，或隔行变色
 * @author cloud
 */
public class ListCellRendereLabel  extends JLabel implements ListCellRenderer {
  
    @Override
    public Component getListCellRendererComponent(JList list, 
        Object value, int index, boolean isSelected, 
        boolean cellHasFocus) 
    {
         
        if ( (index%2)==0){
            this.setBackground(Color.pink);
        } else {
            this.setBackground(Color.ORANGE);
        }
      
        if (isSelected){
            this.setBackground(Color.red);
        }
        this.setOpaque(true);
        this.setText((String)value);
        return this;
    }
    
    
 
    public static void createFrame(){
        JFrame f = new JFrame("Wallpaper");
        f.getContentPane().setLayout(new BorderLayout()); 
        
        
        String [] dd={"aa","bb","dd","ee","ff","gg","hh",};
        JComboBox cb=(JComboBox) new JComboBox(dd);
        cb.setRenderer(new ListCellRendereLabel());
        cb.setPreferredSize(new Dimension(100,40));
        cb.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) {
               JComboBox c= (JComboBox) e.getSource();
                String s=    (String) c.getSelectedItem();
                JOptionPane.showMessageDialog(null,s);
            }
        });
    


        f.add( cb,BorderLayout.NORTH);
        
        
        f.setSize(400, 300);
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo (null);
        f.setVisible (true);
        
    }
    public static void main(String args[]){
        createFrame();
    }
}
