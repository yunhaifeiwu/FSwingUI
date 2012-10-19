/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.jtree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import org.fswingui.plaf.Style;

/**
 *
 * @author cloud
 */
public class JTreePanelDemo3 extends JPanel{
    
    public class MyRenderer implements TreeCellRenderer {
        private transient DefaultTreeCellRenderer renderer ;
        private transient JTree tree;
        private transient Object v;
        private JPanel editPanel;
        private JTextField editText;
        private JButton editButton;
        public MyRenderer(){
        editPanel = new javax.swing.JPanel();
            
            editButton = new javax.swing.JButton();
            editText = new javax.swing.JTextField(); 
            editText.setPreferredSize(new Dimension(120,
                    editText.getPreferredSize().height)
            );
            editButton.setText("*");
            editButton.setPreferredSize(new Dimension(15, 
                    editButton.getPreferredSize().height)
            );
           
            editButton.addActionListener(new ActionListener() {
               
                public void actionPerformed(ActionEvent e) {
                     String dd=JOptionPane.showInputDialog(null,
                              MyRenderer.this.editPanel
                     );   
                     editText.setText(dd);
                }
            });
            editPanel.setLayout(new BorderLayout());             
            editPanel.add(editText,BorderLayout.CENTER);
            editPanel.add(editButton,BorderLayout.EAST);
        }
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, 
            int row, boolean hasFocus) 
        {
            DefaultMutableTreeNode node=(DefaultMutableTreeNode) value;
            Data data=(Data) node.getUserObject();
             if (value == null) 
                editText.setText(""); 
            else 
                editText.setText(data.title); 
            return editPanel;
        }
        
    }
    public class Data{       
        public String id;
        public String title;
        public Object userData;
        
        public Data(){}
        public Data(String id, String title, Object userData) {
            this.id = id;
            this.title = title;
            this.userData = userData;
        }
         
        @Override
        public String toString() {
            return title;
        }
        
         
              
    }
    private JTree tree;
    private DefaultMutableTreeNode root=null;
    private DefaultTreeModel mode;
    private DefaultMutableTreeNode node1;
    private DefaultMutableTreeNode parent;
    
    
    
    public void init(){
        
         JScrollPane jsp=new JScrollPane();
         Style style=new Style();
         style.setId("style1");
         style.setTitle("风格1");
         root=new DefaultMutableTreeNode();
         root.setUserObject(new Data("0","风格集",null));
 
         DefaultMutableTreeNode node;
         node=new  DefaultMutableTreeNode(); // 定义树结点
         Data data=new Data (style.getId(),style.getTitle(),style);
         node.setUserObject(data);
         root.add(node);
         parent=node;
         
         node =new  DefaultMutableTreeNode();
         node.setUserObject(new Data("ddd0","xxxx",null));
         parent.add(node);
         parent=node;
         for(int i=0;i<=6;i++)  {
             String s=String.valueOf(i);
            
             if(i%2==0){
                 data=new Data (s,"主题"+s,new JLabel(s));
                
             } else{
                  data=new Data (s,"主题"+s,new JTextField(s));
             }
             node=  new  DefaultMutableTreeNode(data);
             if(i==3) node1=parent;
             parent.add(node);
         }  
         
         
         tree=new JTree(root);
         
         tree.setCellRenderer(new MyRenderer());         
         
         tree.addTreeSelectionListener(new TreeSelectionListener(){

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node=(DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();
                Data data=(Data) node.getUserObject();
                System.out.println("id:"+data.id+";titel:"+data.title+";data:"+data.userData);
            }
         
         });
         
         jsp.setViewportView(tree); 
         
          BorderLayout layout=new BorderLayout();
         this.setLayout(layout); 
         this.add(jsp,BorderLayout.CENTER);      
    }
    
    public Data getDataInstance(String id, String title, Object userData){
        return new Data(id,title,userData);
    }
   
    //<editor-fold desc="test"> 
    public static void createPanel(){
        
        JFrame f = new JFrame("Wallpaper");
       
        JTreePanelDemo3 scrollPane = new JTreePanelDemo3(); 
        scrollPane.init();
        f.add(scrollPane);
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
    //</editor-fold>
}
