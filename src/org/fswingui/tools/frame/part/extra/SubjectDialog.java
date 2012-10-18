/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.extra;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.part.base.PropertyPanel;
import org.fswingui.tools.frame.part.base.pub.VectorData;
import org.fswingui.utility.SpringUtilities;
import unitExample.dialog.DialogDemo1;

/**
 *
 * @author cloud
 */
public class SubjectDialog extends  JDialog {
    //<editor-fold  desc="in class">   
   
    
    //</editor-fold>
    private transient JPanel contentPanel ;
    private transient SpringLayout layout = new SpringLayout();
    private JList list;
    public  Vector<VectorData> vectorDatas=new Vector();
    private JButton okbtn;
    private JButton deletebtn;
    public  VectorData selectVectorData=null;

    public SubjectDialog(Frame owner) {
        super(owner);
        this.setTitle("主题列表选项");
        contentPanel=(JPanel) this.getContentPane();
    }
   
    
    public SubjectDialog(){
        super();
        this.setTitle("主题列表选项");
        contentPanel=(JPanel) this.getContentPane();
         
    } 
    
    
    public void init(){
        contentPanel.setLayout(layout);
        int w=contentPanel.getWidth();
        int h=contentPanel.getHeight();
        
         
        JLabel title=new JLabel("主题列表选项:");
        contentPanel.add(title);
        
        list=new JList(vectorDatas); 
        JScrollPane jp=new JScrollPane(list);
        contentPanel.add(jp); 
        
        deletebtn=new JButton("删除");
        okbtn=new JButton("应用");
        JPanel p=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Component c1=Box.createHorizontalGlue();
        p.add(c1);
        p.add(deletebtn);
        p.add(okbtn);
        
        contentPanel.add(p);
        
        c1=Box.createHorizontalBox();
        contentPanel.add(c1);
        
        SpringUtilities.makeCompactGrid(contentPanel,
                                        4, 1, //rows, cols
                                        15, 35,        //initX, initY
                                        15, 15);       //xPad, yPad
        
        addAction();
        this.setSize(400, 300);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        setLocation((screenSize.width-300)/2,(screenSize.height-200)/2);
    }
    
    public void addAction(){
        deletebtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                VectorData v=(VectorData) list.getSelectedValue();
                vectorDatas.removeElement(v);
                CurrentData.getSubjectEntitys().remove(v.getDisplayName());
                list.revalidate();
                list.repaint();
            }
        
        });
        
        okbtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                selectVectorData=(VectorData) list.getSelectedValue(); 
                SubjectDialog.this.setVisible(false);
            }
        
        });
        
        
    }
   
    
    //<editor-fold  desc="test"> 
    public static void createPanel(){

        
       SubjectDialog dlg=new SubjectDialog();
       dlg.init();
       dlg.setSize(400, 300);
       dlg.setVisible (true);
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
