/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;

/**
 * 基于二分分割所做的 无限次分割面板。<br/>
 * 对已经存在的面板，新添一个分割，并填入指定panel.<br>  
 * 组合模式---当在设计时，如果一个对象A中有对象B，B中又有C，等等，而这些对象<br>
 * 操作都相同，可以用组合模式。简化实现是：设计一个类，允许把该类型的其他有限个实<br>
 * 例注入进去。<br>
 * 
 * 
 * @author cloud
 */
public class FSplitPanel extends JPanel {
    //所有子组件，都存放于Map中。在新增时自动维护。 
   private int interval =3;
    
    
    public static final int HORIZONTAL_SPLIT=JSplitPane.HORIZONTAL_SPLIT;
    public static final int VERTICAL_SPLIT=JSplitPane.VERTICAL_SPLIT;
    private class UnitData {
        public  boolean isFree=true;
        private JPanel panel;
        private String name;          
        private JSplitPane parent; 
        private boolean isFirst=false;
        public UnitData(){};
        public UnitData( boolean isFree){ this.isFree=isFree;};
        
    }
    private FSplitPanel root;
 
    private Map<String,UnitData> componentMap=new HashMap<String,UnitData>();
    
 
    public FSplitPanel(){     
        this.root=this;
        UnitData data=new UnitData();
       
        data.name="root";
        data.panel=null;    
        componentMap.put("root", data);         
      
    }
    
    /**
     * 在指定且已经存在的分割中，新增一个分割。并在新得到分割区域中，加入指定panel。
     * @param parent ----指定且已经存在的分割。
     * @param name ------对新得到的分割进行定义。
     * @param panel ------要用该panel去填充新分割
     * @param splitSyle ----分割方向，方平分割还是竖直分割。
     * @return true ---true,分割成功;false,分割失败。
     */
    public boolean  add(String parent,String name,JPanel panel,int splitSyle){
        UnitData data=componentMap.get(parent);        
        if (data==null) return false;
        if (parent.equals("root") && (data.parent!=null ) ) 
            return false;
        
        if (parent.equals("root")){
            SpringLayout layout = new SpringLayout();
            this.setLayout(layout);
            data.isFree=false;
            data.name=name;
            data.panel=panel ;
            data.isFirst=true;                    
            this.add(panel);
            layout.putConstraint(SpringLayout.WEST, panel, interval, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.EAST, this, interval, SpringLayout.EAST,panel );
            layout.putConstraint(SpringLayout.NORTH, panel, interval, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.SOUTH, this, interval, SpringLayout.SOUTH, panel);
            

            componentMap.put(name, data);
        } else {
            //当节点不为空，在当前节点使用JSplitPanel分割，并把当前结点，存放到
            //JSplitPanel的左分割中。其右割存放新增的节点            
            
            JSplitPane  splitPanel=new JSplitPane (splitSyle);
            splitPanel.setBorder(null);
             
            if (data.parent==null){
                data.parent=splitPanel; 
                Container  c=data.panel.getParent();
                c.removeAll();
                
                 
                if (data.isFirst){
                    SpringLayout layout = new SpringLayout();
                    c.setLayout(layout);                     
                    c.add(splitPanel);
                    layout.putConstraint(SpringLayout.WEST, splitPanel, interval, SpringLayout.WEST, c);                    
                    layout.putConstraint(SpringLayout.EAST, c, interval, SpringLayout.EAST, splitPanel);
                    layout.putConstraint(SpringLayout.NORTH, splitPanel, interval, SpringLayout.NORTH,c );
                    layout.putConstraint(SpringLayout.SOUTH, c, interval, SpringLayout.SOUTH, splitPanel);

                } else {
                    c.add(splitPanel);
                }               
                splitPanel.setLeftComponent(data.panel);  
                
                UnitData tempData=new UnitData();
                tempData.isFree=false;
                tempData.panel=panel;
                tempData.name=name;     
                tempData.parent=splitPanel;
                splitPanel.setRightComponent(panel);
                componentMap.put(name, tempData);

            } else {
                Component  c=data.parent.getLeftComponent();
                data.parent.setLeftComponent(splitPanel); 
                splitPanel.setLeftComponent(c);
                
                
                
                UnitData tempData=new UnitData();//为了阅读或以后维护，与前一个if重复
                tempData.isFree=false;
                tempData.panel=panel;
                tempData.name=name;     
                tempData.parent=splitPanel;
                splitPanel.setRightComponent(panel);
                componentMap.put(name, tempData);                
            }
            
        }
        return true;
    }
    
    /**
     * 获得指定名称的JPanel.注意：仅返回JPanel。
     * @param name ----分割区域名。在add方法中，通过name参数进行定义。<br>
     * 初始(即构造方法中）仅有一个名为root的区域。
     */
    public JPanel get(String name){
        UnitData data=componentMap.get(name);
        if (data!=null){
            return data.panel;
        } else {
           return null;
        }
      
    }
    
    /**
     * 获得所有分割名称
     */
    public List getSplitName(){
        List<String> l=new ArrayList();        
        for (String t :componentMap.keySet()){
            l.add(t);
        }
        return l;
    }
    //<editor-fold defaultstate="collapsed" desc="测试"> 
    public static  void createPanel() {
        JFrame f = new JFrame("d");   
        f.setSize(400, 300);
        JPanel p = (JPanel) f.getContentPane(); 
        
        
        FSplitPanel pss=new FSplitPanel();
        p.add( pss); 
         
        JPanel t=new JPanel(); 
        pss.add("root", "left", t, FSplitPanel.VERTICAL_SPLIT);
        
        t=new JPanel(new FlowLayout(FlowLayout.LEFT));
        t.add(new JLabel("right"));
        pss.add("left", "right", t, FSplitPanel.VERTICAL_SPLIT);
        
        t=new JPanel(new FlowLayout(FlowLayout.LEFT));
        t.add(new JLabel("left1"));
        pss.add("left", "left1", t, FSplitPanel.VERTICAL_SPLIT);
        
        t=new JPanel(new FlowLayout(FlowLayout.LEFT));       
        t.add(new JLabel("left11"));
        pss.add("left", "left11", t, FSplitPanel.HORIZONTAL_SPLIT);
        
        t=new JPanel(new FlowLayout(FlowLayout.LEFT));
        t.add(new JLabel("right1"));
        pss.add("right", "right1", t, FSplitPanel.VERTICAL_SPLIT);
        
        t=new JPanel(new FlowLayout(FlowLayout.LEFT));         
        t.add(new JLabel("leftddd"));
        pss.add("right1", "leftddd", t, FSplitPanel.VERTICAL_SPLIT);
        
        t=new JPanel(new FlowLayout(FlowLayout.LEFT));
        t.add(new JLabel("right11"));
        pss.add("right1", "right11", t, FSplitPanel.VERTICAL_SPLIT);
       
        t=pss.get("left");
        t.setBackground(Color.blue);
        
        t=pss.get("right1");
        t.setBackground(Color.red);
        
        t=pss.get("right11");
        t.setBackground(Color.yellow);
        
        t=pss.get("left11");
        t.setBackground(Color.pink);
                
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
