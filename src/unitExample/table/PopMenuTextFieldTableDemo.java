/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public  class PopMenuTextFieldTableDemo extends JTextField {  
  
    private static final long serialVersionUID = 1L;  
    private   JPopupMenu jPopupMenu = new JPopupMenu();  
    private JMenuItem copy = new JMenuItem("复制");  
    private JMenuItem paste = new JMenuItem("粘贴");  
    private JMenuItem cut = new JMenuItem("剪切");  
      
    PopMenuTextFieldTableDemo myself = this;  
      
    public PopMenuTextFieldTableDemo(){  
        this.addMouseListener(new MouseAdapter(){  
            @Override
            public void mousePressed(MouseEvent arg0) {  
               
                if(arg0.getButton()==3){  
                    if(myself.isEnabled()){//如果当前组件处于不可用状态，则不弹出右键菜单  
                      
                        jPopupMenu.removeAll();  
                        if(!myself.isEditable()){
                            jPopupMenu.add(copy);  
                        }else{  
                            jPopupMenu.add(copy);  
                            jPopupMenu.add(paste);  
                            jPopupMenu.add(cut);  
                        }  
                        jPopupMenu.show(arg0.getComponent(), arg0.getX(), arg0.getY());       
                    }     
                }  
            }  
        });  
          
        jPopupMenu.add(copy);  
        jPopupMenu.add(paste);  
        jPopupMenu.add(cut);  
          
 
        copy.addActionListener(new ActionListener() {  
            @Override
            public void actionPerformed(ActionEvent arg0) {  
                myself.copy();  
            }  
        });  
 
        paste.addActionListener(new ActionListener(){  
            @Override
            public void actionPerformed(ActionEvent arg0) {  
                myself.paste();       
            }         
        });  
   
        cut.addActionListener(new ActionListener(){  
            @Override
            public void actionPerformed(ActionEvent arg0) {  
                myself.cut();         
            }         
        });      
    }  
    
    public static  void createPanel() {
        JFrame f = new JFrame("Wallpaper");
        f.getContentPane().setLayout(new FlowLayout());
        Object[] heads = {"d","s","dd"};  
        
         
        DefaultTableModel model = new  DefaultTableModel  (heads,5);   
        DefaultCellEditor objectDefaultCellEditor = new DefaultCellEditor(new PopMenuTextFieldTableDemo());  
  

        JTable t=new JTable();     
        t.setDefaultEditor(Object.class,objectDefaultCellEditor);//为Object类型指定默认编
        t.setModel(model);

       
        //用JScrollPane 能正常顺利显示处JTable
        final JScrollPane scrollPane = new JScrollPane(); 
        scrollPane.setViewportView(t);
        f.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        f.setSize(400, 300);
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