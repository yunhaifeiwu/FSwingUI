/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.jtree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.fswingui.plaf.Style;
import org.fswingui.plaf.Subject;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.tools.frame.part.base.PropertyPanel;

/**
 *
 * @author cloud
 */
public class JTreePanelPopMenuDemo1 extends JPanel{
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
    
    private JPopupMenu pop=new JPopupMenu("dd");
    
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

         JMenu openFile = new JMenu("打开");
         JMenu closeFile = new JMenu("关闭");
         pop.add(openFile);
         pop.add(closeFile);
         JMenuItem item=new JMenuItem("dd");
         item.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "ddd");
            }
         
         });
         openFile.add(item);
         
         
         
         
         tree=new JTree(root);
         
         tree.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                     pop.show(e.getComponent(), e.getX(), 
                             e.getY());//弹出右键菜单

                }
            }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) {}
         
         });
          
         tree.addTreeSelectionListener(new TreeSelectionListener(){

            @Override
            public void valueChanged(TreeSelectionEvent e) {
//                DefaultMutableTreeNode node=(DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();
//                Data data=(Data) node.getUserObject();
//                System.out.println("id:"+data.id+";titel:"+data.title+";data:"+data.userData);
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
       
        JTreePanelPopMenuDemo1 scrollPane = new JTreePanelPopMenuDemo1(); 
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
