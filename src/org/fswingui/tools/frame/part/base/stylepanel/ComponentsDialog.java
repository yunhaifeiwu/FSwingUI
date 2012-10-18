/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base.stylepanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;
import org.fswingui.plaf.ui.FListUI;
import org.fswingui.tools.frame.part.base.pub.MapBindTwo;
import org.fswingui.tools.frame.part.base.pub.VectorBindTwo;
import unitExample.dialog.DialogDemo1;

/**
 *列出常见组件的字符串名称，本对话框数据被捆定到VectorBindTwo的vectorB上。
 * 供StylePanel 的主题组件集确定使用
 */
public class ComponentsDialog extends JDialog implements PropertyChangeListener {
   
    JPanel root;
    JScrollPane panel;
    JList<String> list;
    private Vector<String> vector;
    private transient VectorBindTwo<String> vectorBind;
    private MouseListener listMouseListener;
    private transient int actionType=0;
    
    
    //<editor-fold desc="构造函数">
    
    /**
     * @param actionType  list 事件响应方式
     *    0----双击，当前选中项发送到vectorBind的B中，并删除自已 <br>
     *    1----双击，当前选中项发送到vectorBind的B中，不删除自已. <br>
     *    2----双击，当前选中项发送到vectorBind的B中，不删除自已.但要关闭对话框 <br>
     */
    public ComponentsDialog(int actionType) {
        
        super();
        this.actionType=actionType;
//        init();
    }
    public ComponentsDialog() {
        super();
//        init();
    }
    public ComponentsDialog(VectorBindTwo<String> vectorBind) {
        super();
        this.vectorBind=vectorBind;
        this.vectorBind.addVectorAChangesListener(this);
//        init();
    }

    public ComponentsDialog(Frame owner,VectorBindTwo<String> vectorBind) {
        super(owner);
        this.vectorBind=vectorBind;
        this.vectorBind.addVectorAChangesListener(this);
//        init();
    }

    public ComponentsDialog(Frame owner, boolean modal,VectorBindTwo<String> vectorBind) {
        super(owner, modal);
        this.vectorBind=vectorBind;
        this.vectorBind.addVectorAChangesListener(this);
//        init();
    }
    public ComponentsDialog(Frame owner, boolean modal) {
        super(owner, modal);
//        init();
    }

    public ComponentsDialog(Frame owner, String title,VectorBindTwo<String> vectorBind) {
        super(owner, title);
        this.vectorBind=vectorBind;
        this.vectorBind.addVectorAChangesListener(this);
//        init();
    }
    
    public ComponentsDialog(Frame owner, String title) {
        super(owner, title);
//        init();
    }
    //</editor-fold>

    public VectorBindTwo getVectorBind() {
        return vectorBind;
    }

    public void setVectorBind(VectorBindTwo vectorBind) {
        this.vectorBind = vectorBind;
    }

    public JList<String> getList() {
        return list;
    }
    
    
    /**
     * 存放组件名
     */
    public Vector<String> getVector() {
        return vector;
    }
    /**
     * 存放组件名
     */
    public void setVector(Vector<String> vector) {
      
        this.vector = vector;
    }
    
    public void refresh(){
        panel.revalidate();
        panel.repaint();
        
    }
    
    /**
     * @param actionType  list 事件响应方式
     *    0----双击，当前选中项发送到vectorBind的B中，并删除自已 <br>
     *    1----双击，当前选中项发送到vectorBind的B中，不删除自已. <br>
     *    2----双击，当前选中项发送到vectorBind的B中，不删除自已.但要关闭对话框 <br>
     */
    public void setActonType(int type){
        this.actionType=type;
    }
    
    
    
    public JDialog init(){
        
        if(vector==null){
            vector=new Vector();
            vector.add(JList.class.getSimpleName());
            vector.add(JPanel .class.getSimpleName());
        }
       
        list=new JList(vector); 
        list.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                switch(actionType){
                    case 0:
                        if(e.getClickCount()==2){
                            vectorBind.addElementB(list.getSelectedValue());
                            vector.removeElement(list.getSelectedValue());
                            list.revalidate();
                            list.repaint();
                        }
                        break;
                    case 1:
                        if(e.getClickCount()==2){
                            vectorBind.addElementB(list.getSelectedValue());
                            list.revalidate();
                            list.repaint();
                        }
                        break;
                    case 2:
                        if(e.getClickCount()==2){
                            vectorBind.addElementB(list.getSelectedValue());
                            list.revalidate();
                            list.repaint();
                            ComponentsDialog.this.setVisible(false);
                        }
                        break;
                }
                        
            
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) { }
        
        });
        panel=new JScrollPane(list);
        panel.revalidate();
        panel.repaint();
        
        
        root=(JPanel) this.getContentPane();
        root.setLayout(new BorderLayout());
        root.add(panel,BorderLayout.CENTER);    
        this.setSize(new Dimension(300,200));
        
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        
        setLocation((screenSize.width-300)/2,(screenSize.height-200)/2);
        this.setModal(true);
        return this;
    }
    
    public void addListMouseListener(MouseListener m){
        this.listMouseListener=m;
        list.addMouseListener(m);
    }
    
    public void removeListMouseListener(MouseListener m){
        list.removeMouseListener(m);
       
    }
    public void removeListMouseListener(){
        list.removeMouseListener(listMouseListener);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
         
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
