/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.RootPaint;
import org.fswingui.plaf.tools.paint.RootPaint.PaintChangeEvent;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import org.fswingui.tools.frame.model.MapPropertys.MapPropertyChangeEvent;
import org.fswingui.tools.frame.model.MapPropertys.PropertyUnit;
import org.fswingui.tools.gui.component.extra.Div;
import org.fswingui.tools.gui.component.extra.BaseDiv;
import org.fswingui.utility.MapSort;

/**
 *
 * @author cloud
 */
public class BaseData  implements PropertyChangeListener,Cloneable,Serializable  {
    
    //<editor-fold desc="变量">
    protected transient CurrentData currentData;
    protected String id;
    private MapPropertys mapPropertys =new MapPropertys();
    
    private  boolean leaf=true;
    private LinkedHashMap<String,BaseData> children;
    private BaseData parent;
    private int zorder=0;    
    private boolean contained=false;
    private MapSort<Integer,BaseData> zorderChildren;//供Z排序，即确定前后排序
   //</editor-fold>
    
    
    public BaseData(CurrentData currentData ){
     
        this.currentData=currentData;
        Object[][] p={
            {PropertyCriterion.NAME,"名称",String.class,"",""},
            {PropertyCriterion.SUBJECT_ID,"主题",SubjectEntity.class,"",null},
            {PropertyCriterion.WIDTH,"宽",Integer.class,"0",0},
            {PropertyCriterion.HEIGHT,"高",Integer.class,"0",0},
            {PropertyCriterion.X,"横坐标",Integer.class,"0",0},
            {PropertyCriterion.Y,"纵坐标",Integer.class,"0",0},
            {PropertyCriterion.OPAQUE,"透明",Boolean.class,"false",false},  
            {PropertyCriterion.ARRANGEMENT,"图文位置",Integer.class,"0",0},            
            {PropertyCriterion.DISABLE_TEXT,"禁止文本",Boolean.class,"true",true},
            {PropertyCriterion.TEXT,"文本",String.class,"",""},
            {PropertyCriterion.FONT,"字体",Font.class,"",null},
            {PropertyCriterion.FONT_COLOR,"字体颜色",Color.class,"",null},
            {PropertyCriterion.TEXT_TRANSPARENCE,"文本透明度",Float.class,"1",1f},
            {PropertyCriterion.DISABLE_ICON,"禁止图标",Boolean.class,"true",true},
            {PropertyCriterion.ICON,"图标",Icon.class,"",null},
            {PropertyCriterion.ICON_TRANSPARENCE,"图标透明度",Float.class,"1",1f},
            {PropertyCriterion.BACKIMG,"背景图",Icon.class,"",null},                
            {PropertyCriterion.BACKGROUD_TRANSPARENCE,"背景透明度",Float.class,"1",1f},
            {PropertyCriterion.PAINT,"背景绘制",AbstractPaint.class,"",null}

        };
        mapPropertys.initPropertys(p);
        mapPropertys.addMapChangesListener(this);
         
        this.addCurrDataChangesListener(this);
   }
    
    
    //<editor-fold desc="getter and setter">
    public String getId() {
        return id;
    }
    
    public void setId(String id){
        Map<String,BaseData> ad= currentData.getAllData();
        BaseData bd=ad.get(this.id);
        this.id=id;
        if (bd==null) {
            ad.put(id, this.clone());
        }else{
            bd.id=id;
            ad.remove(id);
            ad.put(id, bd);
        }
        
    }

    public CurrentData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(CurrentData currentData) {
        this.currentData = currentData;
    }
 
    
    public MapPropertys getMapPropertys() {
        return mapPropertys;
    }

    public void setChildren(LinkedHashMap<String, BaseData> children) {
        PropertyChangeEvent e=new PropertyChangeEvent(this,"children",
                this.children,children);         
        currDataChange.firePropertyChange(e);
        this.children = children;
       
    }

    public LinkedHashMap<String, BaseData> getChildren() {
        return children;
    }
    
    public  BaseData getChild(String id) {
        return children.get(id);
    }
    
    public  Div getChildDiv(String id) {
        if (children==null) return null;
        BaseData child=children.get(id);
        if (child==null) return null;
        Div div=CurrentData.getDiv(child.getId());
        return div;
    }
    
    public  JComponent getChildDivView(String id) {
        if (children==null) return null;
        BaseData child=children.get(id);
        if (child==null) return null;
        Div div=CurrentData.getDiv(child.getId());
        if (div==null) return null;
        
        return div.getView();
    }

    public BaseData getParent() {
        return parent;
    }
    
    public Div getParentDiv() {
        if (parent==null) return null;
        Div div=CurrentData.getDiv(parent.getId());
        return div;
    }
    
    public JComponent getParentDivView() {
        if (parent==null) return null;
        Div div=CurrentData.getDiv(parent.getId());
        if (div==null) return null;
        return div.getView();
    }

    public void setParent(BaseData baseData) {
        PropertyChangeEvent e=new PropertyChangeEvent(this,"parent",
                this.parent,baseData);         
        currDataChange.firePropertyChange(e);
        this.parent = baseData;
        
    }

    public Div getDiv(){
        Div div=CurrentData.getDiv(this.getId());
        return div;
    }
  
    public JComponent getDivView(){
        Div div=CurrentData.getDiv(this.getId());
        if(div==null) return null;
        return div.getView();
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    
    /**
     * 组合中，被包容。如果为false说明，是根容器
     */
    public boolean isContained() {
        return contained;
    }
    
    /**
     * 组合中，被包容。如果为false说明，是根容器
     */
    public void setContained(boolean contained) {
        this.contained = contained;
    }
    
    public void setMapPropertys(MapPropertys mapPropertys) {
        this.mapPropertys = mapPropertys; 
    }

    public int getZorder() {
        return zorder;
    }
    
    
    
    //</editor-fold>
    
    @Override  
    public BaseData clone()  {  
        BaseData o=null;
        try {
            o=(BaseData) super.clone();
            o.mapPropertys=this.mapPropertys.clone();    
            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(BaseData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    } 
    
    
    // <editor-fold defaultstate="collapsed" desc="事件 注册与取消注册"> 
    private PropertyChangeSupport currDataChange 
            = new PropertyChangeSupport(this);
    /**
    * 注册mapChanges属性事件
    */
    public void addCurrDataChangesListener (PropertyChangeListener listener) {
        currDataChange.addPropertyChangeListener(listener);
    }
    /**
    * 注销mapChanges属性事件
    */
    public void removeCurrDataChangesListene (PropertyChangeListener listener) {
        currDataChange.removePropertyChangeListener (listener);        
    }
    //</editor-fold>
    
    // <editor-fold  desc="事件（核心区）"> 
    /**
     * 响应 属性改变，与绘制函数参数改变
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(  BaseData.class.isAssignableFrom(
            evt.getSource().getClass() ) ){
            currDatapropertyChangePaint(evt);
        } else if (evt.getClass().isAssignableFrom(
                MapPropertys.MapPropertyChangeEvent.class) )
        {
            try {
                propertyChangeMapProperty(evt);
            } catch (Exception ex) {
                Logger.getLogger(BaseData.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(evt.getClass().isAssignableFrom(
                RootPaint.PaintChangeEvent.class))
        {
            //propertyChangePaint(evt);
        }
        
    }
    
    /**
     * 当《抽象属性》发生改变时，自动同步DataBus中的AllData中相应数据
     * 注:这里要求DIV（即《真实物体》）的标识 名称必须为name，存放于PropertyUnit中
     * @param evt 
     */
    public void propertyChangeMapProperty(PropertyChangeEvent evt) throws Exception {      
        MapPropertyChangeEvent e=(MapPropertyChangeEvent) evt;
        
        if (evt.getNewValue()==null) return;
        if ( !  (
                PropertyUnit.class.getName().equals(
                evt.getNewValue().getClass().getName() )   &&
                e.getOldValue()!=null &&
                PropertyUnit.class.getName().equals(
                 e.getOldValue().getClass().getName() )  
            ) )//getOldValue是上次属性值不是旧值，新旧值，不是 PropertyUnit类直接退出。               
        {   
            return;
        }
        
        
        PropertyUnit p=(PropertyUnit) evt.getNewValue();   
       //上一次DIV的属性
        PropertyUnit lastp=(PropertyUnit) e.getOldValue();
        Map<String,BaseData> ad=  currentData.getAllData();
        
        if (!p.name.equals(lastp.name)) return;//新属性与上一次属性不一致退出。
        
        
        PropertyUnit oldp=(PropertyUnit) e.getOldValue();
        
        String oid=e.objectId;//表示当前真实物体的ID值 
//        if(
//                p.value.getClass().getSimpleName().equals("String") &&
//                oid.equals((String)p.value) && 
//                !p.name.equals("name")) 
//        {
//            String d="相关开发人员注意，Mapropertys的ID名称为:name.出错";
//            d=d+"出错处："+this;
//            throw new Exception(d);
//        } 
        
        
        
        BaseData bd=null;
        
        if (this.id==null || this.id.equals("") )
        {
            this.id=oid;
        }
       
        bd=ad.get(oid);
        if (bd==null &&   e.sourceType.equals(MapPropertys.TYPE_OBJECT))
        {           
            this.id=oid;
            bd=(BaseData) this.clone();
            ad.put(bd.id, bd);
             
        }  else if (bd==null &&  
                e.sourceType.equals(MapPropertys.TYPE_EDIT)
            )
        {           return;
        } else if (bd!=null &&  
                e.sourceType.equals(MapPropertys.TYPE_EDIT)   
                && "name".equals(p.name)
            )
        {
            
            this.id=(String) p.value;
            bd.id=this.id;
            ad.put(bd.id, bd);
            ad.remove(oid);
            
             Div od=CurrentData.getDiv(oid);
             
             if(od!=null ){    
                  od.setName((String)p.value);
                  od.getView().setName(od.getName());
                  CurrentData.getDivs().remove(oid);
                  CurrentData.putDiv(od);
             }
             
             BaseData tbd= CurrentData.getRootBaseDatas().get(oid);
             if(tbd!=null){
                 tbd.setId((String)p.value);
                 CurrentData.getRootBaseDatas().remove(oid);
                 CurrentData.getRootBaseDatas().put((String)p.value, tbd);
             }

             
             
             tbd= CurrentData.getSelectBaseDatas().get(oid);
             if(tbd!=null){
                 tbd.setId((String)p.value);
                 CurrentData.getSelectBaseDatas().remove(oid);
                 CurrentData.getSelectBaseDatas().put(tbd.getId(), tbd);
             }
             
        }

        bd.mapPropertys.setProperty(p);
     
        //test
//        test1();
        
    }
    
    /**
     * 当 currData的某些属性变化时，同步全局存储体的值。
     */
    public void currDatapropertyChangePaint(PropertyChangeEvent evt) {     
     
        Map<String,BaseData> ad= currentData.getAllData();
        BaseData bd=ad.get(this.id);
        if (bd==null) return;
        
        if (evt.getPropertyName().equals("id")){
            bd.id=(String) evt.getNewValue();
             
        }   else if(evt.getPropertyName().equals("children")){
            bd.children= (LinkedHashMap<String, BaseData>) evt.getNewValue();
            if (bd.children!=null)  bd.children=bd.children;
        }  else if (evt.getPropertyName().equals("parent")){            
            BaseData newv=(BaseData) evt.getNewValue();
            if (newv==null) return;
            newv=ad.get(newv.getId());
            bd.parent=newv;
        } 
        
        
    }
    
    public void propertyChangePaint(PropertyChangeEvent evt) {      
        PaintChangeEvent e=(PaintChangeEvent) evt;
        if (evt.getNewValue()==null) return;
    }
    
    //</editor-fold>
      
    //<editor-fold desc="Z排序处理">
    
    /**
     *增加Z序号时，父类节点的子孙序自动增减1变化
     */
    public void setZorder(int zorder,boolean auto) {
        this.zorder = zorder;
        if(parent==null || parent.getChildren()==null || !auto ||
                parent.getChildren().isEmpty()) 
        {
            return;
        }
        BaseData bd=null;
        MapSort<Integer,BaseData> zcld=this.parent.zorderChildren;
        if(zcld==null){
            settleAllZorder(this.parent);
            return;
        }
        if(!zcld.containsKey(new Integer(zorder))){
            zcld.put(new Integer(zorder), bd);
        } else {
            settleAllZorder(this.parent);
        }  
    }
    
    /**
     * 新增子节点，并整理Z轴排序
     */
    public void putChild(BaseData baseData,boolean isZorder) {
         if (this.children==null) this.children=new LinkedHashMap();
         this.children.put(baseData.getId(), baseData);
         
         if ( ! isZorder) return;        
         if (this.zorderChildren==null) this.zorderChildren=new MapSort();
         Integer z=new Integer(baseData.getZorder());
         if (!this.zorderChildren.containsKey(z) ){
             this.zorderChildren.put(z, baseData);
         } else { 
             settleAllZorder(baseData);
         }
         
    }
    
    public void paintViewZorder(){
        paintViewZorder(this);
    }
    public void paintViewZorder(BaseData baseData){
        if (baseData.zorderChildren==null || baseData.zorderChildren.isEmpty()){
            settleAllZorder(baseData);
        }    
        if (baseData.zorderChildren==null || baseData.zorderChildren.isEmpty()) return;
        BaseData bd=null;
        JComponent v;
        JComponent rootV=baseData.getDivView();
        MapSort<Integer,BaseData> zcld=baseData.zorderChildren;
        rootV.removeAll();
        for   (Iterator it=zcld.keySet().iterator();it.hasNext();) { 
            Integer key=(Integer) it.next(); 
            bd= zcld.get(key);
            v=bd.getDivView();
            rootV.add(v);
           
            if (bd.zorderChildren!=null &&  !bd.zorderChildren.isEmpty() ){
                paintViewZorder(bd);
            }
            
        } 
    }
    
    /**
     * 整理指点点节点的 子节点的Z轴排序
     */
    public void  settleAllZorder(BaseData baseNode){
       
        if (baseNode==null || baseNode.getChildren()==null
                || baseNode.getChildren().isEmpty()  )
        {
            return;
        }
        LinkedHashMap<String,BaseData> cld=baseNode.getChildren();
        MapSort<Integer,BaseData> zcld=new MapSort();
    
        baseNode.zorderChildren=zcld;
        int j=0;
        for (Map.Entry<String,BaseData> en:cld.entrySet()){
            int i=en.getValue().getZorder();     
            j=i;
            while (cld.containsKey(new Integer(j))){
                j++;
            }
            zcld.put(new Integer(j), en.getValue());
        }
      
       
         
    }
    //</editor-fold>
    

    
       
 
    
   
    //<editor-fold defaultstate="collapsed" desc=" test 3"> 
    public void test1(){
        for (Map.Entry en:currentData.getAllData().entrySet()){
            System.out.println("========="+(String)en.getKey() +"============");
            BaseData cd1=(BaseData) en.getValue();
            MapPropertys mp1=cd1.getMapPropertys();
            Map <String,PropertyUnit> mt= mp1.getPropertysClone();
            for (Map.Entry en1:mt.entrySet()){
                PropertyUnit p1=(PropertyUnit) en1.getValue();
                System.out.println(p1.name+":"+p1.value);
                
            }
            
            System.out.println("========="+(String)en.getKey() +"============");
        }
    }
    
    public static void test2(){
        CurrentData cd=new CurrentData(new DataBus());
        BaseData bd=new BaseData(cd);
        String id1="aa";
        bd.id=id1;
        Object[][] p={
            {"name","名称",String.class,"",""},
            {"subjectID","主题",String.class,"",""}
        };
        bd.mapPropertys.initPropertys(p);
        bd.mapPropertys.setPropertyValue("name", id1,id1);
        BaseData bd1=(BaseData) bd.clone();
        id1="bb";
        bd1.mapPropertys.setPropertyValue("name", id1,id1);
        System.out.println();
    }
    public static void main(String args[]) {
        test2();
    }
    //</editor-fold> 
}
