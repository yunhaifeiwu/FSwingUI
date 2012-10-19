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
import javax.swing.JComponent;
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
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.fswingui.plaf.Style;
import org.fswingui.plaf.Subject;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.tools.frame.part.base.PropertyPanel;

/**
 *
 * @author cloud
 */
public class JTreePanelPopMenuDemo extends JPanel{
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
    private DefaultMutableTreeNode root;
    
    private JPopupMenu pop=new JPopupMenu("dd");
    
    public void init(){
        
         JScrollPane jsp=new JScrollPane();
         root=new DefaultMutableTreeNode();
         root.setUserObject(new Data("0","风格集",null));
         
         Style style=new Style();
         style.setId("style1");
         style.setTitle("风格1");       
         DefaultMutableTreeNode parent=new  DefaultMutableTreeNode(); // 定义树结点
         Data data=new Data (style.getId(),style.getTitle(),style);
         parent.setUserObject(data);

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
         
         for(Map.Entry<String,Subject> en:style.entrySet()){
             DefaultMutableTreeNode node=new  DefaultMutableTreeNode(en.getKey()); 
             data=new Data(en.getKey(),en.getValue().getTitle(),en.getValue());
             
             node.setUserObject(data);
             parent.add(node);
         }     
         root.add(parent);
         
         
         tree=new JTree(root);
         
         tree.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
//                     pop.show(e.getComponent(), e.getX(), 
//                             e.getY());//弹出右键菜单
                     PopupFactory f=PopupFactory.getSharedInstance();
                     JFrame jf=(JFrame) SwingUtilities.getRoot((JComponent)e.getSource());
                     int x=e.getXOnScreen();
                     int y=e.getYOnScreen();
                     Popup p=f.getPopup(jf, new JTextField("I is popu"),x, y);                 
                     p.show();


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
       
        JTreePanelPopMenuDemo scrollPane = new JTreePanelPopMenuDemo(); 
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
