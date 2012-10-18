/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.combox;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Vector;
import javax.swing.*;

/**
 * 由于JcomboBox，的Item,是Object, 故，可以自定义一个Bean, 然后重写该Bean的
 * toString方法，让他返回指定的属性即可。
 */
public class JComboBoxDemo extends JFrame{
    public class MyObj implements Serializable {

        private String displayName = null;
        private String value = null;
        public MyObj(){}
        public MyObj(String aDisplayName, String aValue) {
            this.displayName = aDisplayName;
            this.value = aValue;
        }

        public String toString() {
//            return this.displayName.toString();
            return this.displayName.toString()+"    "+this.value.toString();
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public String getValue() {
            return this.value;
        }
        
        public   Vector getVector() {
      
            Vector<MyObj> vector = new Vector();
            MyObj obj = new MyObj("单位1", "danwei1");
            vector.add(obj);
            obj = new MyObj("单位2", "danwei2");
            vector.add(obj);
            obj = new MyObj("单位3", "danwei2");
            vector.add(obj);
            obj = new MyObj("单位4", "danwei4");
            vector.add(obj);
            obj = new MyObj("单位5", "danwei5");
            vector.add(obj);
            obj = new MyObj("单位6", "danwei6");
            vector.add(obj);
            return vector;
        }

    }

    




    public JComboBoxDemo(){
        Container contentPane=this.getContentPane();
        JPanel jPanel1=new JPanel(new GridLayout(1,2));

       
        MyObj myObj=new MyObj();
        JComboBox jComboBox1=new JComboBox(myObj.getVector());
       
        jComboBox1.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) {
               JComboBox c= (JComboBox) e.getSource();
                MyObj o= (MyObj) c.getSelectedItem();
               
                
                JOptionPane.showMessageDialog(null, o.displayName+";"+
                        o.value);
            }
        });
        
        jComboBox1.setBorder(BorderFactory.createTitledBorder("---------"));
        jComboBox1.setEditable(true);
        jPanel1.add(jComboBox1);

         
        
        contentPane.add(jPanel1);
        this.setTitle("JComboBoxDemo");
        this.setSize(350,90);
        this.setVisible(true);
    }

 

    public static void main(String args[]){
        JComboBoxDemo test=new JComboBoxDemo();
    }
}