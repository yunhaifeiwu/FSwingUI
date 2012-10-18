/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base.stylepanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.fswingui.plaf.Style;
import org.fswingui.plaf.Subject;
import org.fswingui.plaf.SubjectCriterion;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.ui.FButtonUI;
import org.fswingui.plaf.ui.FLabelUI;
import org.fswingui.plaf.ui.FListUI;
import org.fswingui.plaf.ui.FPanelUI;
import org.fswingui.plaf.ui.FTableHeaderUI;
import org.fswingui.plaf.ui.FTreeUI;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.part.base.pub.TreeData;
import org.fswingui.tools.frame.part.base.pub.VectorBindTwo;

/**
 *UI基础组件。 辅助 方格显示与编辑操作。
 * 其中各个菜单项的方法，独立出静态方法，可供其他组件直接调用
 */
public class StylePopMenu extends JPopupMenu implements PropertyChangeListener{
    private static final String USER_SUB=SubjectCriterion.USER_NAME_SUBJECT;
    private static final String USER_SE=SubjectCriterion.USER_NAME_SUBJECT_ENTITY;
    /**
     * 主题的实体包关键字
     */
    public static final String SUBJECT_NODE="subjectEntityNode";
    /**
     * 主题的组件包关键字
     */
    public static final String COMPONENT_NODE="componentNode";
    public static final String ACTIVE=SubjectCriterion.ACTIVE;
    public static final String DISABLE=SubjectCriterion.DISABLE;
    public static final String MAIN=SubjectCriterion.MAIN;
             
    private transient JTree tree;
    private transient DefaultTreeModel model;
    private transient DefaultMutableTreeNode select=null;
    
    /**
     * 保存调用者.在调用弹出菜单时传入
     */
    public transient JPanel parentPanel; 
    //保存弹出菜单位位置，供某些菜单项使用
    public transient int popX=0; 
    public transient int popY=0; 
    
    private JMenuItem addStyle=new JMenuItem("新增风格");    
    private JMenuItem insertStyle=new JMenuItem("插入风格"); 
    private JMenuItem deleteStyle=new JMenuItem("删除风格");   
    
    private JMenuItem addSubject=new JMenuItem("新增主题");  
    private JMenuItem insertSubject=new JMenuItem("插入主题");   
    private JMenuItem deleteSubject=new JMenuItem("删除主题");   
    private JMenuItem designateMainColor=new JMenuItem("指定主色");   
    private JMenuItem displayMainColor=new JMenuItem("显示主色");   
    
    private JMenuItem addActiveSubjectEntity=new JMenuItem("添加活动实体");   
    private JMenuItem addDisableSubjectEntity=new JMenuItem("添加禁止实体");   
    private JMenuItem addSubjectEntity=new JMenuItem("添加实体");   
    private JMenuItem insertSubjectEntity=new JMenuItem("插入实体");   
    private JMenuItem deleteSubjectEntity=new JMenuItem("删除实体");   
    
    private JMenuItem  designateSubjectEntity=new JMenuItem("指定实体");   
    private JMenuItem  displaySubjectEntity=new JMenuItem("显示实体"); 
    
    private JMenuItem selectComponents=new JMenuItem("组件选择");   
    private JMenuItem deleteComponent=new JMenuItem("删除组件");   
    
    private JMenuItem rename=new JMenuItem("更名");   
    
    private transient static int styleIndex=0;//风格计算数
    private transient static int subjectIndex=0;//主题计算数
    private transient static int subjectEntityIndex=0;//主题计算数
    private JColorChooser jcolor;
    
    public Vector<String> subeVector=new Vector();//供r指定实体对话框用
    public transient VectorBindTwo<String> subeVBind2=new VectorBindTwo();
    public ComponentsDialog subeDlg;
    public Map <String,SubjectEntity >  subjectEntitys;
    
    public Vector<String> cmpVector;//组件,供组件选择对话框用
    public Vector<String> cmpVectorInit=new Vector();//组件,供组件选择对话框用    
    public transient VectorBindTwo<String> vectorBindTwo=new VectorBindTwo();
    public ComponentsDialog cmpDlg;
    
    //<editor-fold desc="getter and setter">
   
    //</editor-fold>
    
 
 
   
    public StylePopMenu(JPanel parent,JTree tree1){
        this.parentPanel=parent;
        this.tree=tree1;
        this.model=(DefaultTreeModel) tree.getModel();
    }
    
    public void init(){
        JFrame jf=(JFrame) SwingUtilities.getRoot(parentPanel);
        subeDlg=new ComponentsDialog(jf,"主题实体指定，双击选中",subeVBind2);
         
        if(subeVector.size()<=0 && subjectEntitys==null ){
           subeVector.add("test1");
           subeVector.add("test2");
        } else if(subjectEntitys!=null) {
           subeVector.clear();
           for(Map.Entry<String,SubjectEntity> en:subjectEntitys.entrySet()){
               subeVector.add(en.getKey());
           }
        }
            
     
        subeDlg.setVector(subeVector);
        subeDlg.setActonType(2);
        subeDlg.init();
        subeVBind2.addVectorBChangesListener(this);
        
        cmpDlg=new ComponentsDialog(jf,"可选的组件，双击选中",vectorBindTwo);   
        if(cmpVectorInit.size()<=0){
//            cmpVectorInit.add(JLabel.class.getSimpleName());
//            cmpVectorInit.add(JTextField.class.getSimpleName());
//            cmpVectorInit.add(JButton.class.getSimpleName());
//            cmpVectorInit.add(JList.class.getSimpleName());
//            cmpVectorInit.add(JComboBox.class.getSimpleName());
//            cmpVectorInit.add(JTable.class.getSimpleName());
            cmpVectorInit.add(JTree.class.getSimpleName());
            cmpVectorInit.add(JPanel.class.getSimpleName());
            
            cmpVectorInit.add("ButtonUI");
            cmpVectorInit.add("PanelUI");
            cmpVectorInit.add("ListUI");
            cmpVectorInit.add("TreeUI");
            cmpVectorInit.add("LabelUI");
            cmpVectorInit.add("TableHeaderUI");
            cmpVectorInit.add("MenuBarUI");
            
            cmpVector=(Vector<String>) cmpVectorInit.clone();
            
        }
        cmpDlg.setVector((Vector)cmpVector.clone());
        cmpDlg.init();
        vectorBindTwo.addVectorBChangesListener(this);
        
        rename.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                TreePath  path=new TreePath( model.getPathToRoot(select));
                if(select==null) return;
                tree.setEditable(true);
                tree.startEditingAtPath(path);
            }        
        });
        
        addStyle.addActionListener(new ActionListener(){ 

            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                addStyleCmd(select); 
                tree.updateUI();
                StylePopMenu.this.cmpVector= (Vector<String>) 
                        StylePopMenu.this.cmpVectorInit.clone();
            }        
        });
        
        insertStyle.addActionListener(new ActionListener(){ 

            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                TreeNode parent1 = parent1=insertStyleCmd(select); 
                tree.updateUI();
                TreePath path=new TreePath( model.getPathToRoot(parent1)); 
                tree.setEditable(true);
                tree.startEditingAtPath(path); 
                StylePopMenu.this.cmpVector= (Vector<String>) 
                        StylePopMenu.this.cmpVectorInit.clone();
            }      
            
        });
        
        deleteStyle.addActionListener(new ActionListener(){ 

            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                DefaultMutableTreeNode node=deleteStyleCmd(select); 
                TreePath  path=new TreePath( model.getPathToRoot(node));
                    tree.setSelectionPath(path);    
                tree.setSelectionPath(path);
                tree.updateUI();
            }
        
        });
        
        addSubject.addActionListener(new ActionListener(){ 

            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                addSubjectCmd(select); 
                tree.updateUI();
            }        
        });
        
        designateMainColor.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                if(jcolor==null) jcolor=new JColorChooser();
                TreeData data=(TreeData) select.getUserObject();
                Color newc= Color.BLUE;
                if (Style.class.isAssignableFrom(data.userData.getClass())) 
                { 
                     Style s=(Style) data.userData;
                     if(s.getMainColor()!=null) {
                         newc=s.getMainColor(); 
                     }
                }
                
                Color c=jcolor.showDialog((JComponent)e.getSource(), "", newc);
                if(c!=null){
                    data=(TreeData) select.getUserObject();
                    Style  style=(Style) data.userData;
                    style.setMainColor(c);
                }
            }        
        });
        
        displayMainColor.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                TreeData data=(TreeData) select.getUserObject();
                if (Style.class.isAssignableFrom(data.userData.getClass())) 
                { 
                     Style s=(Style) data.userData;
                         
                     Color newc=s.getMainColor(); 
                     if(newc!=null) {
                         String tempstr="指定的颜色是：";
                         tempstr=tempstr+Integer.toHexString(newc.getRGB());
                         tempstr=tempstr+";R:"+String.valueOf(newc.getRed());
                         tempstr=tempstr+";G:"+String.valueOf(newc.getGreen());
                         tempstr=tempstr+";B:"+String.valueOf(newc.getBlue());
                         tempstr=tempstr+";A:"+String.valueOf(newc.getAlpha());
                        
                         
                         JOptionPane.showMessageDialog(displayMainColor,tempstr);
                         
                     } else {
                         JOptionPane.showMessageDialog(displayMainColor, 
                             "指定的颜色为空 ");
                     }
                     
                }
            }        
        });
        
        insertSubject.addActionListener(new ActionListener(){ 

            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                TreeNode parent1 = parent1=insertSubjectCmd(select); 
                tree.updateUI();
                TreePath path=new TreePath( model.getPathToRoot(parent1)); 
                tree.setEditable(true);
                tree.startEditingAtPath(path); 
                
            }        
        });
        
        deleteSubject.addActionListener(new ActionListener(){ 

            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                DefaultMutableTreeNode node=deleteStyleCmd(select); 
                TreePath  path=new TreePath( model.getPathToRoot(node));
                    tree.setSelectionPath(path);    
                tree.setSelectionPath(path);
                tree.updateUI();
            }
        
        });
        

                
                
        addActiveSubjectEntity.addActionListener(new ActionListener(){ 

            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                addSubjectEntityCmd(select,ACTIVE); 
                tree.updateUI();
            }        
        });
        
        addDisableSubjectEntity.addActionListener(new ActionListener(){ 

            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                addSubjectEntityCmd(select,DISABLE); 
                tree.updateUI();
            }        
        });
        
        addSubjectEntity.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                addSubjectEntityCmd(select,""); 
                tree.updateUI();
            }        
        });
        
        insertSubjectEntity.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                insertSubjectEntityCmd(select); 
                tree.updateUI();
            }        
        });
        
        deleteSubjectEntity.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                deleteSubjectEntityCmd(select); 
                tree.updateUI();
            }        
        });
        
        selectComponents.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                TreeData data=(TreeData) select.getUserObject();
                Vector<String> v=(Vector) data.userData;
              
                Vector<String> v1=cmpDlg.getVector();
                Vector<String> v2=(Vector<String>) cmpVector.clone();
                v1.clear();
                if (v!=null && v.size()>0) {
                    for(String s:v2){
                        if (!v.contains(s)) 
                            v1.add(s);
                    }
                   
                   
                } else {
                    for(String s:v2){
                        v1.add(s);
                    }
                     
                }
                cmpDlg.setVisible(true);
            }        
        });
        
        deleteComponent.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                TreeData data=(TreeData) select.getUserObject();
                TreeData pdata = null;
                DefaultMutableTreeNode pnode=(DefaultMutableTreeNode) 
                        select.getParent();
                if (pnode!=null) pdata=(TreeData) pnode.getUserObject();
        
                Vector<String> pv=(Vector) pdata.userData;
                if(pv==null) return;
                
                Vector<String> v1=cmpDlg.getVector();
                cmpVector.addElement(data.title);
                v1.addElement(data.title);
                pv.removeElement(data.title);
                pnode.remove(select);
                
                tree.updateUI();

            }        
        });
        
        designateSubjectEntity.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                TreeData data=(TreeData) select.getUserObject();
                subjectEntitys=CurrentData.getSubjectEntitys();
                if(subjectEntitys!=null) {
                    subeVector.clear();
                    for(Map.Entry<String,SubjectEntity> en:subjectEntitys.entrySet()){
                        subeVector.add(en.getKey());
                    }
                }
                
                subeDlg.setVisible(true);
            }        
        });
        
        displaySubjectEntity.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelect();
                if(select==null) return;
                TreeData data=(TreeData) select.getUserObject();
                if (SubjectEntity.class.isAssignableFrom(data.userData.getClass())) 
                { 
                     SubjectEntity se=(SubjectEntity) data.userData;
                     JOptionPane.showMessageDialog(displaySubjectEntity, 
                             "指定的实体是："+se.getSubjectID());
                }
            }        
        });
    }
    
    @Override
    public void show(Component invoker, int x, int y) {
        getSelect();
        if(tree==null || select==null) return;
        TreeData data=(TreeData) select.getUserObject();        
        if(data==null) return;
        
        TreeData pdata=null;
        DefaultMutableTreeNode pnode=(DefaultMutableTreeNode) select.getParent();
        if (pnode!=null) pdata=(TreeData) pnode.getUserObject();
        
        this.removeAll();
        if( select.getRoot().equals(select) )
        {//风格集 显示功能
            this.add(rename);
            this.add(addStyle);
            this.add(new JSeparator());
            this.add(rename);
        } else if(Style.class.isAssignableFrom(data.userData.getClass()))
        {//风格显示功能   
            this.add(addSubject);
            
            this.add(new JSeparator());
            this.add(insertStyle);
            this.add(deleteStyle);
            this.add(new JSeparator());
            this.add(designateMainColor);
            this.add(displayMainColor);
            
            
            this.add(new JSeparator());
            this.add(rename);
        }else if(Subject.class.isAssignableFrom(data.userData.getClass()))
        {//主题显示功能         
            this.add(insertSubject);
            this.add(deleteSubject);
            this.add(new JSeparator());
            this.add(rename);
        }else if(String.class.isAssignableFrom(data.userData.getClass()) &&
                 data.id.equals(SUBJECT_NODE) )
        {//实体包显示功能       
            this.add(addActiveSubjectEntity);
            this.add(addDisableSubjectEntity);
            this.add(addSubjectEntity);
        } else if(SubjectEntity.class.isAssignableFrom(data.userData.getClass()) &&
                 (!data.id.equals(ACTIVE)) && (!data.id.equals(DISABLE)) &&
                (!data.id.equals(MAIN))
        ){//实体显示功能     
            
            this.add(insertSubjectEntity);
            this.add(deleteSubjectEntity);            
            this.add(new JSeparator()); 
            this.add(rename);
        } else if(Vector.class.isAssignableFrom(data.userData.getClass()) &&
                 data.id.equals(COMPONENT_NODE) )
        {//组件包显示功能       
            this.add(selectComponents); 
        } else if(Vector.class.isAssignableFrom(pdata.userData.getClass()) &&
                 pdata.id.equals(COMPONENT_NODE) )
        {//组件显示功能       
            this.add(deleteComponent); 
            this.add(new JSeparator()); 
        }  else if(SubjectEntity.class.isAssignableFrom(data.userData.getClass()) 
                )
            
        {//主题实体指定功能       
            
            this.add(designateSubjectEntity); 
            this.add(displaySubjectEntity); 
            this.add(new JSeparator()); 
        }  
        
        super.show(invoker, x, y);
    }   
    
    
    public   void getSelect(){
         if (tree==null) return;
         TreePath p=tree.getSelectionPath();
         if (p==null) return;
         select =(DefaultMutableTreeNode) p.getLastPathComponent();         
    }
    
       @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        if(!VectorBindTwo.class.isAssignableFrom(evt.getSource().getClass())
            || evt.getNewValue()==null  
            || !String.class.isAssignableFrom(evt.getNewValue().getClass())
            || ((String)evt.getNewValue()).equals("")
              ) 
        {
            return;
        }   
        getSelect();
        if (select==null ) return;
        TreeData data=(TreeData) select.getUserObject();
        if(data.id.equals(COMPONENT_NODE)) {
            Vector v=(Vector) data.userData;
            if(v==null ) {
                v=new Vector<String>();
            }
            String newS=(String) evt.getNewValue();
            v.addElement(newS);
            cmpVector.removeElement(newS);
            DefaultMutableTreeNode node=new DefaultMutableTreeNode();
            data =new TreeData();
            data.id=String.valueOf(v.indexOf(newS));
            data.title=newS;
            data.userData=newS;
            node.setUserObject(data);
            select.add(node);
            tree.updateUI();
            TreePath path=new TreePath( model.getPathToRoot(select));
            tree.expandPath(path);
        } else if (SubjectEntity.class.isAssignableFrom(data.userData.getClass())) 
        { 
           SubjectEntity se=(SubjectEntity) data.userData;
           String newS=(String) evt.getNewValue();
           se.setSubjectID(newS);
           
        }
            
     
   
      
         
    }
    
    /**
     * 在指点主题节点处增加默认的主题实体，此实体是临时的，最终会在框架中设定的主题<br>
     * 实体所替代。
     */
    public static  void addDefaultSubjectEntity(DefaultMutableTreeNode node){ 
         DefaultMutableTreeNode initNode=node;
         DefaultMutableTreeNode parent=node;
         node =new DefaultMutableTreeNode();
         TreeData data=new TreeData(SUBJECT_NODE,"实体包",SUBJECT_NODE);
         node.setUserObject(data);         
         parent.add(node);
         
         data=(TreeData) parent.getUserObject();
         if(data==null ) {
            try {
                throw new Exception("所指定节点类型，不是Subject");
            } catch (Exception ex) {
                Logger.getLogger(StylePopMenu.class.getName()).log(Level.SEVERE, null, ex);
            }  
             
         } else if(data.userData==null ){
             subjectIndex++;
             String s=String.valueOf(subjectIndex);   
             Subject sub=new Subject();
             sub.setId(USER_SUB+s); 
             sub.setTitle("自定义主题"+s);
             data.userData=sub;
             
             parent=node;
             node=new DefaultMutableTreeNode();
             SubjectEntity se=new SubjectEntity();
             data=new TreeData(Subject.MAIN,"默认",se);
             node.setUserObject(data);
             sub.put(Subject.MAIN, se);
             
         } else if (Subject.class.isAssignableFrom(data.userData.getClass())){
             Subject sub=(Subject) data.userData;
             parent=node;
             if(sub.size()<=0) {
                 node=new DefaultMutableTreeNode();
                 SubjectEntity se=new SubjectEntity();
                 data=new TreeData(Subject.MAIN,"默认",se);
                 sub.put(Subject.MAIN, se);
                 node.setUserObject(data);
                 
                 parent.add(node);
              } else {
                 for(Map.Entry<String,SubjectEntity> en:sub.entrySet()){
                     node=new DefaultMutableTreeNode();
                     SubjectEntity se=en.getValue();
                     if (se==null) se=new SubjectEntity();
                     data=new TreeData(en.getKey(),se.getSubjectID(),se);
                     node.setUserObject(data);
                     parent.add(node);
                 }
            } 
         } else {
            try {
                throw new Exception("所指定节点类型，不是Subject");
            } catch (Exception ex) {
                Logger.getLogger(StylePopMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
        
         
         parent=initNode;
         node =new DefaultMutableTreeNode();
         Vector<String> v=new  Vector();
         data=new TreeData(COMPONENT_NODE,"所描绘组件",v);
         node.setUserObject(data);         
         parent.add(node);
         
    }
    
    
    
    //<editor-fold desc="命令区">
    
    //<editor-fold desc="风格命令区">
    
    /**
     * 添加一个风格，并返回刚建的风格
     */
    public  static  DefaultMutableTreeNode addStyleCmd(DefaultMutableTreeNode select){ 
         Style style=new Style();
         styleIndex++;
         String s=String.valueOf(styleIndex);
         style.setId("style"+s);
         style.setTitle("风格"+s);  
         CurrentData.getStyles().put(style.getId(), style);
          
         DefaultMutableTreeNode parent1=new  DefaultMutableTreeNode(); // 定义树结点
        
         
         TreeData data=new TreeData(style.getId(),style.getTitle(),style);
         parent1.setUserObject(data);         
         for(Map.Entry<String,Subject> en:style.entrySet()){
             DefaultMutableTreeNode node=new  DefaultMutableTreeNode(en.getKey()); 
             data=new TreeData(en.getKey(),en.getValue().getTitle(),en.getValue().clone());
             
             node.setUserObject(data);
             parent1.add(node);
             
             addDefaultSubjectEntity(node);
             
         }      
         select.add(parent1);
         
         return parent1;
    }
    
    /**
     * 删除选中风格，并返回下一个结点或父结点上.根节点不能删除。
     */
    public  static  DefaultMutableTreeNode deleteStyleCmd(DefaultMutableTreeNode select){ 
         if (select==null) return null;
         DefaultMutableTreeNode root=(DefaultMutableTreeNode) select.getRoot();
         DefaultMutableTreeNode parent=(DefaultMutableTreeNode) select.getParent();
         if(parent==null) return null;
         if(root.equals(select)) return null;
         TreeData data=(TreeData) select.getUserObject();
         CurrentData.getStyles().remove(data.userData);
         DefaultMutableTreeNode nodeAfter=(DefaultMutableTreeNode) parent.getChildAfter(select);
         DefaultMutableTreeNode nodeBefor=(DefaultMutableTreeNode) parent.getChildBefore(select);
         if(parent.getChildCount()==1){
             parent.remove(select);
             return parent;
         } else if(nodeAfter!=null ) {
             parent.remove(select);             
             return nodeAfter;
         } else {
             parent.remove(select);             
             return nodeBefor;
         } 
          
    }
    
    /**
     * 插入一个风格，并返回插入结点。
     */
    public  static  DefaultMutableTreeNode insertStyleCmd( DefaultMutableTreeNode select)
    {
        if (select==null) return null;
         
        DefaultMutableTreeNode parent=(DefaultMutableTreeNode) select.getParent();
        if(parent==null) return null;
        
        Style style=new Style();
        styleIndex++;
        String s=String.valueOf(styleIndex);
        style.setId("style"+s);
        style.setTitle("风格"+s);  
        CurrentData.getStyles().put(style.getId(), style);
        DefaultMutableTreeNode parent1=new  DefaultMutableTreeNode(); 
        TreeData data=new TreeData(style.getId(),style.getTitle(),style);
        parent1.setUserObject(data);         
        for(Map.Entry<String,Subject> en:style.entrySet()){
            DefaultMutableTreeNode node=new  DefaultMutableTreeNode(en.getKey()); 
            data=new TreeData(en.getKey(),en.getValue().getTitle(),en.getValue().clone());
            node.setUserObject(data);
            parent1.add(node);
            addDefaultSubjectEntity(node);
        }    

        parent.insert(parent1, parent.getIndex(select)+1);

          
        
        return parent1;
         
    }
    
    //</editor-fold>
    
    
    //<editor-fold desc="主题====命令区">
    /**
     * 添加一个主题，并返回刚建的主题结点
     */
    public  static  DefaultMutableTreeNode addSubjectCmd(DefaultMutableTreeNode select){ 
         
         Subject sub=new Subject();
         subjectIndex++;
         String s=String.valueOf(subjectIndex);   
         sub.setId(USER_SUB+s); 
         sub.setTitle("自定义主题"+s);
         SubjectCriterion.putSub(USER_SUB+s, sub);
         DefaultMutableTreeNode parent1=new  DefaultMutableTreeNode(); // 定义树结点
         TreeData data=new TreeData(sub.getId(),sub.getTitle(),sub);
         parent1.setUserObject(data);         
         select.add(parent1);
         addDefaultSubjectEntity(parent1);
         
         return parent1;
    }
    
    /**
     * 插入一个主题，并返回刚建的主题结点
     */
    public  static  DefaultMutableTreeNode insertSubjectCmd(DefaultMutableTreeNode select){ 
        if (select==null) return null;
         
        DefaultMutableTreeNode parent=(DefaultMutableTreeNode) select.getParent();
        if(parent==null) return null;

        Subject sub=new Subject();
        subjectIndex++;
        String s=String.valueOf(subjectIndex);   
        sub.setId(USER_SUB+s); 
        sub.setTitle("自定义主题"+s);
        SubjectCriterion.putSub(USER_SUB+s, sub);

        DefaultMutableTreeNode parent1=new  DefaultMutableTreeNode(); // 定义树结点
        TreeData data=new TreeData(sub.getId(),sub.getTitle(),sub);
        parent1.setUserObject(data);     
        parent.insert(parent1, parent.getIndex(select)+1);
        addDefaultSubjectEntity(parent1);
        return parent1;
        
    }
    
    /**
     * 删除选中主题 ，并返回下一个结点或父结点上.根节点不能删除。
     */
    public  static  DefaultMutableTreeNode deleteSubjectCmd(DefaultMutableTreeNode select){ 
         if (select==null) return null;
         DefaultMutableTreeNode root=(DefaultMutableTreeNode) select.getRoot();
         DefaultMutableTreeNode parent=(DefaultMutableTreeNode) select.getParent();
         if(parent==null) return null;
         if(root.equals(select)) return null;
         DefaultMutableTreeNode nodeAfter=(DefaultMutableTreeNode) parent.getChildAfter(select);
         DefaultMutableTreeNode nodeBefor=(DefaultMutableTreeNode) parent.getChildBefore(select);
         if(parent.getChildCount()==1){
             parent.remove(select);
             return parent;
         } else if(nodeAfter!=null ) {
             parent.remove(select);             
             return nodeAfter;
         } else {
             parent.remove(select);             
             return nodeBefor;
         } 
    }
    //</editor-fold>
    
    //<editor-fold desc="实体========命令区">
   /**
    * 添加一个主题实体，并返回刚建的结点.
     * 要求选中的结点 的父节点 为他的主题。
     * 
    * @param select 选中结点
    * @param type  active --活动实全；disable---禁止实体。字符串取自主题规约<br>
    *               manin ---默认
    */
    public  static  DefaultMutableTreeNode addSubjectEntityCmd(
            DefaultMutableTreeNode select,String type)
    { 
         DefaultMutableTreeNode parent=(DefaultMutableTreeNode) select.getParent();
         TreeData data=(TreeData) parent.getUserObject();
         Subject sub=(Subject) data.userData;
         
         SubjectEntity se=new SubjectEntity();
         subjectEntityIndex++;
         String s=String.valueOf(subjectEntityIndex);   
         sub.put(USER_SE+s, se);
         if(ACTIVE.equals(type)){
             data=new TreeData(type,"活动",se);
         } else if(DISABLE.equals(type)) {
             data=new TreeData(type,"禁止",se);
         }else if(MAIN.equals(type)) {
             data=new TreeData(type,"默认",se);
         } else {
             data=new TreeData(USER_SE+s,"自定义"+s,se);
         }
         
         DefaultMutableTreeNode node=new DefaultMutableTreeNode();
         node.setUserObject(data);
         select.add(node);     
         return node;
    }
    
    /**
     * 插入一个实体，并返回刚建的主题结点.要求选中的结点 的爷节点 为他的主题。
     */
    public  static  DefaultMutableTreeNode insertSubjectEntityCmd(
            DefaultMutableTreeNode select)
    { 
        if (select==null) return null;
         
        
        
        DefaultMutableTreeNode parent=(DefaultMutableTreeNode) select.getParent();
        DefaultMutableTreeNode  granddad =(DefaultMutableTreeNode) parent.getParent();
        TreeData data=(TreeData) granddad.getUserObject();
        Subject sub=(Subject) data.userData;

        SubjectEntity se=new SubjectEntity();
        subjectEntityIndex++;
        String s=String.valueOf(subjectEntityIndex);   
        sub.put(USER_SE+s, se);
        
        DefaultMutableTreeNode node=new  DefaultMutableTreeNode(); // 定义树结点
        data=new TreeData(USER_SE+s,"自定义"+s,se);
        node.setUserObject(data);  
        
        parent.insert(node, parent.getIndex(select)+1);
        
        return node;
        
    }
    
    /**
     * 删除选中实体 ，并返回下一个结点或父结点上.根节点不能删除。
     */
    public  static  DefaultMutableTreeNode deleteSubjectEntityCmd(DefaultMutableTreeNode select){ 
         if (select==null) return null;
         DefaultMutableTreeNode root=(DefaultMutableTreeNode) select.getRoot();
         DefaultMutableTreeNode parent=(DefaultMutableTreeNode) select.getParent();
         if(parent==null) return null;
         if(root.equals(select)) return null;
         DefaultMutableTreeNode nodeAfter=(DefaultMutableTreeNode) parent.getChildAfter(select);
         DefaultMutableTreeNode nodeBefor=(DefaultMutableTreeNode) parent.getChildBefore(select);
         if(parent.getChildCount()==1){
             parent.remove(select);
             return parent;
         } else if(nodeAfter!=null ) {
             parent.remove(select);             
             return nodeAfter;
         } else {
             parent.remove(select);             
             return nodeBefor;
         } 
    }
    
    //</editor-fold>
    
    //<editor-fold desc="命令区">
    
    
    //</editor-fold>
    
    
    //</editor-fold>

 
    
    
}
