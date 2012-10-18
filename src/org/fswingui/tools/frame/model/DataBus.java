/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 *工具各部份主要的数据交换通道
 */
public class DataBus {
    
   
    /**
     * 存储当前属性的数据
     */    
    private   CurrentData currentData=new CurrentData(this);
    
    /**
     * 存放所有操作过的对象的属性数据，且这些对象生存在主面板中。
     */
    private Map <String,BaseData> allData=new LinkedHashMap();
    
    
    /**
     * 保存主应用程序的 主要构成部件，方便其他部件对另一部件设置事件什么的。
     */
    private Map<String,Object> guiParts=new HashMap();

    public CurrentData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(CurrentData currentData) {
        this.currentData = currentData;
    }

   
        
    public Map getGuiParts() {
        return guiParts;
    }

    public void setGuiParts(Map guiParts) {
        this.guiParts = guiParts;
    }

    public Map<String, BaseData> getAllData() {
        return allData;
    }

    public void setAllData(Map<String, BaseData> allData) {
        this.allData = allData;
    }

   public  BaseData getCurrentBaseData(){
       return currentData.getCurrBaseData();
   }

   /**
     * 从总存储中获得指定DIV的ID的属性
     */
    public BaseData getBaseData(String id){
        return allData.get(id);
    }
   /**
     * 把指定DIV的属性存储到总存储体
     */ 
    public void putBaseData(BaseData baseData){
       allData.put(baseData.getId(), baseData);
    }
    /**
     * 从总存储中删除指定DIV
     */
    public void removeBaseData(String id){
        allData.remove(id);
    }
    /**
     * 清空总存储体
     */
    public void clearBaseData(){
        allData.clear();
    }
    
    /**
     * 保存当前DIV的属性到总存储体中
     */
    public void saveCurrBaseData(){
      allData.put(currentData.getCurrBaseDataID(), currentData.getCurrBaseData().clone());
    }
    
    public DataBus(){}
    
}
