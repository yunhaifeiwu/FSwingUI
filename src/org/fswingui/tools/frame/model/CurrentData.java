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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import javax.swing.Icon;
import javax.swing.JComponent;
import org.fswingui.plaf.Style;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.RootPaint;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import org.fswingui.tools.frame.model.config.FGuiConfig;
import org.fswingui.tools.gui.component.extra.Div;

/**
 *
 * @author cloud
 */
public class CurrentData implements Serializable {
    public final static String DIVBIND_EVENT_NAME="divBind";
    public final static String CURRENT_MANIN_COLOR="currentMainColor";
    public final static String CURRENT_MANIN_STYLE="currentStyle";
    
    //<editor-fold desc="变量">
    
    private DataBus dataBus;
    private FGuiConfig config;
    
    private  BaseData currBaseData =new BaseData(this);
    private static RootPaint paints=new RootPaint();
    private static Map <String,BaseData >  selectBaseDatas=new  LinkedHashMap();
    // key为组件ID；Value为KEY指示组件的根节点
    private static Map <String,BaseData >  rootBaseDatas=new  LinkedHashMap();
    private static Map <String,Div >  divs=new  LinkedHashMap();
    private static Map <String,Style >  styles=new  LinkedHashMap();
    private static Map <String,SubjectEntity >  subjectEntitys=new  LinkedHashMap();
    
    private   Color currentMainColor;//当前主色
    private   String currentStyle; //当前风格
    
 
    
    
    
    //</editor-fold>
    
    //<editor-fold desc="变量">
    
    

    public static Map<String, Style> getStyles() {
        return styles;
    }

    public static void setStyles(Map<String, Style> styles) {
        CurrentData.styles = styles;
    }

    public static Map<String, SubjectEntity> getSubjectEntitys() {
        return subjectEntitys;
    }

    public static void setSubjectEntitys(Map<String, SubjectEntity> subjectEntitys) {
        CurrentData.subjectEntitys = subjectEntitys;
    }

    public FGuiConfig getConfig() {
        return config;
    }

    public void setConfig(FGuiConfig config) {
        this.config = config;
    }

    public  Color getCurrentMainColor() {
        return currentMainColor;
    }

    public  void setCurrentMainColor(Color currentMainColor) {        
        
        
        PropertyChangeEvent e= new PropertyChangeEvent(this,
                CURRENT_MANIN_COLOR, this.currentMainColor, currentMainColor);
        
        mainColorSylteChanges.firePropertyChange(e);
        this.currentMainColor = currentMainColor;
    }

    public  String getCurrentStyle() {
        return currentStyle;
    }

    public  void setCurrentStyle(String currentStyle) {
        PropertyChangeEvent e= new PropertyChangeEvent(this,
                CURRENT_MANIN_STYLE, this.currentStyle, currentStyle);        
        mainColorSylteChanges.firePropertyChange(e);
        this.currentStyle = currentStyle;
        
    }

   
    
    
    
    
    //</editor-fold>
    

    
    public CurrentData(DataBus dataBus){
        this.dataBus=dataBus;     
        AbstractPaint paint=new CrystalPaint();
        paint.setPaintID("dd");
        paints.putPaint(paint);
         
    }
   //<editor-fold desc="辅助数据 获取">
    
    //<editor-fold desc="主数据">
    
    public DataBus getDataBus() {
        return dataBus;
    }
    
    public BaseData getCurrBaseData(){
        return currBaseData;
    }
    public String getCurrBaseDataID(){
        if (currBaseData==null) return "";
        return currBaseData.getId();
    }
    
    /**
     * 获得所有Div的属性
     */
    public Map <String,BaseData> getAllData(){
        return dataBus.getAllData();
    }
    
    /**
     * 从总存储中获得指定DIV的ID的属性
     */
    public BaseData getBaseData(String id){
        return dataBus.getAllData().get(id);
    }
   /**
     * 把指定DIV的属性存储到总存储体
     */ 
    public void putBaseData(BaseData baseData){
        dataBus.getAllData().put(baseData.getId(), baseData);
    }
    /**
     * 从总存储中删除指定DIV
     */
    public void removeBaseData(String id){
        dataBus.getAllData().remove(id);
    }
    /**
     * 清空总存储体
     */
    public void clearBaseData(){
        dataBus.getAllData().clear();
    }
    
    /**
     * 判断总的存储体是否 有指定id的DIV的属性
     */
    public boolean  containsBaseDataKey(String id){
        boolean bl=false;
        bl=dataBus.getAllData().containsKey(id);
        return bl;
    }
   
    /**
     * 保存当前DIV的属性到总存储体中
     */
    public void saveCurrBaseData(){
      dataBus.getAllData().put(currBaseData.getId(), currBaseData.clone());
    }
    public void restorCurrBaseDataFrom(String id){
       this.currBaseData= dataBus.getAllData().get(id).clone();       
    }
 
    //</editor-fold>
 
    //<editor-fold desc="selectBaseData">
    public static BaseData getSelectBaseData(String id){
        return selectBaseDatas.get(id);
    }
    public static void putSelectBaseData(BaseData baseData){
        selectBaseDatas.put(baseData.getId(), baseData);
    }
    public static void removeSelectBaseData(String id){
        selectBaseDatas.remove(id);
    }
    public static void clearSelectBaseData(String id){
        selectBaseDatas.clear();
    }
    public static Map <String,BaseData > getSelectBaseDatas(){
        return selectBaseDatas;
    }
    //</editor-fold>
    
    //<editor-fold desc="rootBaseData"> RootBaseData
    public static BaseData getRootBaseData(String id){
       return  rootBaseDatas.get(id);
    }
    public static void putRootBaseData(String son,BaseData root,CurrentData cd){
        BaseData bd= cd.getBaseData(son);
        
        Stack<BaseData> stack=new Stack();
        stack.push(bd);
        Map<String,BaseData> children ;
        while (! stack.isEmpty()){
            bd=stack.pop();
            rootBaseDatas.put(bd.getId(), root);    
            children =bd.getChildren();
            if (children==null) continue;
            for( Map.Entry<String,BaseData> en:children.entrySet()){
                stack.push(en.getValue());
            }
        }
            
    }
    
    public static void removeRootBaseData(String id){
        rootBaseDatas.remove(id);
    }
    
    public static void clearRootBaseData(String id){
        rootBaseDatas.clear();
    }
    public static Map <String,BaseData > getRootBaseDatas(){
        return rootBaseDatas;
    }
    //</editor-fold>
    
    //<editor-fold desc="divs"> Div div
    public static Div getDiv(String id){
        return  divs.get(id);
    }
    public static void putDiv(Div div){
        divs.put(div.getName(), div);
    }
    public static void removeDiv(String id){
        divs.remove(id);
    }
    public static void clearDiv(String id){
        divs.clear();
    }
    public static Map <String,Div > getDivs(){
        return divs;
    }
    
    public static  JComponent  getDivView(String id){
        Div div=divs.get(id);
        if (div==null) return null;
        return div.getView();
    }
    
    //</editor-fold>
    
    // <editor-fold   desc="DivBind事件"> 
    public  PropertyChangeSupport divBindChanges = new PropertyChangeSupport(this);
    /**
    * 注册divBind属性事件
    */
    public void addDivBindChangesListener (PropertyChangeListener listener) {
         
        divBindChanges.addPropertyChangeListener(listener);
    }
    /**
    * 注销divBind属性事件
    */
    public void removeDivBindChangesListene (PropertyChangeListener listener) {
        divBindChanges.removePropertyChangeListener (listener);        
    }
    //</editor-fold>
    
    // <editor-fold  desc="主色与主风格事件"> 
    public   PropertyChangeSupport mainColorSylteChanges = new PropertyChangeSupport(this);
    /**
    * 注册主色与主风格事件属性事件
    */
    public  void addMainColorSylteChangesListener (PropertyChangeListener listener) {
        mainColorSylteChanges.addPropertyChangeListener(listener);
        
    }
    /**
    * 注销主色与主风格事件属性事件
    */
    public void removeMainColorSylteChangesListene (PropertyChangeListener listener) {
        mainColorSylteChanges.removePropertyChangeListener (listener);        
    }
    //</editor-fold>
    
    //<editor-fold desc="RootPaint "> RootPaint rootPaint AbstractPaint
    public static RootPaint getPaints(){
        return  paints;
    }
    
    //</editor-fold>
  
   //</editor-fold>

   
}
