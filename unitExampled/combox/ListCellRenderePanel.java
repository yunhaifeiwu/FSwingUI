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
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import java.awt.GridLayout;

/**
 * JcomboBox 显示三列。整个效果犹如table
 * @author cloud
 */
public class ListCellRenderePanel  extends JPanel implements ListCellRenderer {

    
    public static class Man implements Serializable {
        public String no;
        public String name;
        public String class1;

        public Man(String no, String name, String class1) {
            this.no = no;
            this.name = name;
            this.class1 = class1;
        }
        
        @Override
        public String toString(){
            return name;
        }  
 
    }
    
    private JLabel label1=new JLabel();
    private JLabel label2=new JLabel();
    private JLabel label3=new JLabel();
    
    public ListCellRenderePanel(){
        this.setLayout(new GridLayout(1,3));
        this.add(label1);
        this.add(label2);
        this.add(label3);
//        this.setPreferredSize(new Dimension(200,30));
    }
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
        Man man=(Man) value;
       

        label1.setText(man.no);
        label2.setText(man.name);
        label3.setText(man.class1);
        

        return this;
    }
    
    
 
    public static void createFrame(){
        JFrame f = new JFrame("Wallpaper");
        f.getContentPane().setLayout(new BorderLayout()); 
        
        
        
        JComboBox cb=(JComboBox) new JComboBox();
      
        cb.addItem(new Man("001","张三","1班"));
        cb.addItem(new Man("002","李四","3班"));
        cb.addItem(new Man("003","王五","2班"));
        cb.addItem(new Man("004","赵六","4班"));
        cb.setRenderer(new ListCellRenderePanel());
        cb.setPreferredSize(new Dimension(100,40));
         
        cb.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) {
               JComboBox c= (JComboBox) e.getSource();
               
                Man s=    (Man) c.getSelectedItem();
                JOptionPane.showMessageDialog(null,s.no+";"+s.name+";"+s.class1);
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
