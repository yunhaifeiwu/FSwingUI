/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import org.fswingui.plaf.Style;
import org.fswingui.plaf.Subject;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.tools.frame.part.base.PropertyPanel;
import org.fswingui.tools.frame.part.base.pub.MapBindTwo;
import org.fswingui.tools.frame.part.base.pub.TreeData;
import org.fswingui.tools.frame.part.base.pub.VectorBindTwo;
import org.fswingui.tools.frame.part.base.stylepanel.StylePopMenu;

public class StylePanel extends JPanel{     
    
    //<editor-fold desc="自定义单元编辑框">
    public class MyTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {
        private transient TreeCellRenderer renderer ;
        protected transient JTree tree;
        private JPanel editPanel;
        private JTextField editText;
       
        
        public MyTreeCellEditor(JTree tree){
            this.tree=tree;
            renderer=(TreeCellRenderer) tree.getCellRenderer();
            editPanel = new javax.swing.JPanel();
            
            editText = new javax.swing.JTextField(); 
            editText.setPreferredSize(new Dimension(120,
                    editText.getPreferredSize().height)
            );
            
            editPanel.setLayout(new BorderLayout());     
//            Component  c= Box.createHorizontalStrut(editText.getPreferredSize().height); 
//            editPanel.add(c,BorderLayout.WEST);
            editPanel.add(editText,BorderLayout.CENTER);
        }
        @Override
        public Component getTreeCellEditorComponent(JTree tree, 
            Object value, boolean isSelected, boolean expanded,
            boolean leaf, int row) 
        {
             DefaultMutableTreeNode node=(DefaultMutableTreeNode) value;
             
             TreeData data=(TreeData) node.getUserObject();
             if (value == null) 
                editText.setText(""); 
            else 
                editText.setText(data.title); 
            return editPanel;
        }
         
        @Override
        public Object getCellEditorValue() {
            DefaultMutableTreeNode node=(DefaultMutableTreeNode) 
                    tree.getSelectionPath().getLastPathComponent();         
            TreeData data= (TreeData) node.getUserObject();
            data.title=editText.getText();       
            tree.setEditable(false);
            return node;
        }
         
    }
    //</editor-fold>
    
    //<editor-fold desc="变量">
    protected JTree tree;
    private transient DefaultMutableTreeNode root;
    private transient MyTreeCellEditor editor;
    protected StylePopMenu  stylePop; 
    private JScrollPane jsp=new JScrollPane();       
    //</editor-fold>

    public JTree getTree() {
        return tree;
    }

    public void setTree(JTree tree) {
        this.tree = tree;
    }
    
    
 
    public StylePanel(){
          
        root=new DefaultMutableTreeNode();
        root.setUserObject(new TreeData("0","风格集",null));        
        tree=new JTree(root);
    }
    
    
    public void reSetCellEditor(){
        editor=new MyTreeCellEditor(tree);
        tree.setCellEditor(editor);

        stylePop=new StylePopMenu(this,tree);
        stylePop.init();
         
    }
    
    public void init(){                     
         editor=new MyTreeCellEditor(tree);
         tree.setCellEditor(editor);
         if(stylePop ==null){
             stylePop=new StylePopMenu(this,tree);
             stylePop.init();
         }
         
         tree.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==3){
                    tree.setEditable(true);     
                    
                }
            
            }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int selRow=tree.getRowForLocation(e.getX(),e.getY()); 
                    TreePath selPath=tree.getPathForLocation(e.getX(),e.getY()); 
                    if(selRow!=-1){ 
                        int x=e.getXOnScreen();
                        int y=e.getYOnScreen();

                        tree.setSelectionPath(selPath); 
                        stylePop.popX=x;
                        stylePop.popY=y;
                        stylePop.show(e.getComponent(), e.getX(), e.getY());
                    }   
                }
            }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) {}
         
         });
         
         tree.addTreeSelectionListener(new TreeSelectionListener(){

            @Override
            public void valueChanged(TreeSelectionEvent e) {
//                TreePath treePath=e.getNewLeadSelectionPath();
//                if (treePath==null) return;
//                DefaultMutableTreeNode node=(DefaultMutableTreeNode) treePath.getLastPathComponent();
//                TreeData data=(TreeData) node.getUserObject();
//                System.out.println("id:"+data.id+";titel:"+data.title+";data:"+data.userData);
            }
         
         });
         
         jsp.setViewportView(tree); 
         
          BorderLayout layout=new BorderLayout();
         this.setLayout(layout); 
         this.add(jsp,BorderLayout.CENTER);      
    }
    
    
   
    //<editor-fold desc="test"> 
    public static void createPanel(){
        
        JFrame f = new JFrame("Wallpaper");
       
        StylePanel scrollPane = new StylePanel(); 
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