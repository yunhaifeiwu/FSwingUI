/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model.config;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.config.PaintConfig;
import org.fswingui.plaf.config.SubjectEntityConfig;
import org.fswingui.utility.MapSort;

/**
 *
 * @author cloud
 */
public class CurrentDataCofig {
    private Map<String,PaintConfig> panit=new LinkedHashMap();
    private Map <String,BaseDataConfig> allData=new LinkedHashMap();
    private Map <String,SubjectEntityConfig >  subjectEntitys=new  LinkedHashMap();
    //key----为子组件件，value为Key组件的根
    private  Map <String,String >  rootBaseDatas=new  LinkedHashMap();
    private Map<String,StyleTreeConfig> styleTreeConfigs=new  LinkedHashMap();
    
    //供Z排序,存储根下面节点的顺序。v为BaseDataConfig的ID值
    private  Map<Integer,String> zorders=new LinkedHashMap();
   
    private   String currentMainColor;//当前主色
    private   String currentStyle; //当前风格
    
    public Map<String, PaintConfig> getPanit() {
        return panit;
    }

    public void setPanit(Map<String, PaintConfig> panit) {
        this.panit = panit;
    }

    public Map<String, BaseDataConfig> getAllData() {
        return allData;
    }

    public void setAllData(Map<String, BaseDataConfig> allData) {
        this.allData = allData;
    }

    public  Map<String, SubjectEntityConfig> getSubjectEntitys() {
        return subjectEntitys;
    }

    public  void setSubjectEntitys(Map<String, SubjectEntityConfig> subjectEntitys) {
        this.subjectEntitys = subjectEntitys;
    }

    public Map<String, String> getRootBaseDatas() {
        return rootBaseDatas;
    }

    public void setRootBaseDatas(Map<String, String> rootBaseDatas) {
        this.rootBaseDatas = rootBaseDatas;
    }

    public Map<Integer, String> getZorders() {
        return zorders;
    }

    public void setZorders(Map<Integer, String> zorders) {
        this.zorders = zorders;
    }

    public Map<String, StyleTreeConfig> getStyleTreeConfigs() {
        return styleTreeConfigs;
    }

    public void setStyleTreeConfigs(Map<String, StyleTreeConfig> styleTreeConfigs) {
        this.styleTreeConfigs = styleTreeConfigs;
    }

    public String getCurrentMainColor() {
        return currentMainColor;
    }

    public void setCurrentMainColor(String currentMainColor) {
        this.currentMainColor = currentMainColor;
    }

    public String getCurrentStyle() {
        return currentStyle;
    }

    public void setCurrentStyle(String currentStyle) {
        this.currentStyle = currentStyle;
    }

    
    
    

}
