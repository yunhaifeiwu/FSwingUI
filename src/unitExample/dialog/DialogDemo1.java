/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import unitExample.component.PropertyPanel;

/**
 *
 * @author cloud
 */
public class DialogDemo1 extends JDialog {
    JPanel root;
    JScrollPane panel;
    JList<String> list;
    private Vector vector;

    //<editor-fold desc="构造函数">
    public DialogDemo1() {
        super();
        init();
    }

    public DialogDemo1(Frame owner) {
        super(owner);
        init();
    }

    public DialogDemo1(Frame owner, boolean modal) {
        super(owner, modal);
        init();
    }

    public DialogDemo1(Frame owner, String title) {
        super(owner, title);
        init();
    }
    //</editor-fold>
    
    private JDialog init(){
        panel=new JScrollPane();
        vector=new Vector();
        vector.add(JList.class.getSimpleName());
        vector.add(JPanel .class.getSimpleName());
       
        list=new JList(vector);
        panel.add(list);
        
        root=(JPanel) this.getContentPane();
        root.add(list);    
        this.setSize(new Dimension(300,200));
        return this;
    }
    
    //<editor-fold  desc="test"> 
    public static void createPanel(){

        
        JFrame f = new JFrame("Wallpaper");
        
        JButton b=new JButton("dd");
       
        f.add(b);
        b.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DialogDemo1 d=new DialogDemo1(null,"组件选择。双击选中");
                d.setVisible(true);
            }
            
        
        });
         f.setSize(400, 300);
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
