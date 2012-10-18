/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * 使用时，先要生成根节点。新增panel时，必须指定在根节点中备了案的节点 为其父结点；<br>
 * 同时根据结点类型或指点方割方式，创建面板。
 * 
 * 新增Panel时
 * @author cloud
 */
public class AgilePanel extends JPanel {
    
    //<editor-fold defaultstate="collapsed" desc="变量定义区">  
    public final static int TYPE_TAB_PANEL_=0;
    
    
    public final static int SPLIT_STYLE_HORIZON=100;//水平分割
    public final static int SPLIT_STYLE_VERTICAL=101;//竖直分割
    
    /**
     * 如果是根节点，则有值。否则，为NULL。保存子PANEL，便于用户快速查询
     */
    private Map<String,AgilePanel> panelMap;    
    
    private String PanelType;    
    private AgilePanel root;
    
    private boolean isRoot;
    
    private JSplitPane  splitPane ;//
        
 
 
    //</editor-fold>
    
    
    public AgilePanel(){
        this.isRoot=false;
        this.root=null;
         
    }
    /**
     * 所生成的Panel将是根节点
     * @param isRoot 
     */
    public AgilePanel(boolean isRoot){
        this.isRoot=isRoot;
        root=isRoot?new AgilePanel():null;
    }
    
    /**
     * 获得指定名称的Panel.在所有子孙中寻找
     */
    public AgilePanel getPanel(String name){        
        //遍历AgilePanel。当本结点的panelMap为null时，在父节点的中寻中。并以次递归。
        
        return null;
    }
    
    /**
     * 
     * @param parent
     * @param panel
     * @param style 
     */  
    public void add(String parent,AgilePanel panel,int style){
        //每增加一个时，如果有根节点，则将panel保存在根节点中的panelMap中。其中KEY为
        //panel的name属性。
        
        AgilePanel p;
        p=panelMap==null?root.panelMap.get(parent) :panelMap.get(parent);        
        panel.root=p.root;
        
        int spt=style==SPLIT_STYLE_HORIZON ?JSplitPane.HORIZONTAL_SPLIT:JSplitPane.VERTICAL_SPLIT;
        
        
        if (splitPane==null) {
            splitPane=new JSplitPane(spt);
            splitPane.setLeftComponent(panel);
        } else if (splitPane.getRightComponent()==null) {
            splitPane.setRightComponent(panel);
        } 
        
        
        
        
    }
    
}
