/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model.config;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.model.MapPropertys;
import org.fswingui.utility.MapSort;

/**
 *
 * @author cloud
 */
public class BaseDataConfig {
    //<editor-fold desc="变量">
    protected String id;
    private String viewClassName;
    private String DivClassName;
    private Map<String,PropertyUnitConfig> propertys=new LinkedHashMap();
    
    private  boolean leaf=true;
    //String BaseDataConfig id ----对应BaseDatar的id
    private LinkedList<String> children;
    private String parent;
    private int zorder=0;    
    private boolean contained=false;
    //key 为序号，string为BaseDataConfig的ID
    private  Map<String,String> zorders=new LinkedHashMap();
   //</editor-fold>

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewClassName() {
        return viewClassName;
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
    }

    public String getDivClassName() {
        return DivClassName;
    }

    public void setDivClassName(String DivClassName) {
        this.DivClassName = DivClassName;
    }

    
    
    public Map<String, PropertyUnitConfig> getPropertys() {
        return propertys;
    }

    public void setPropertys(Map<String, PropertyUnitConfig> propertys) {
        this.propertys = propertys;
    }

   

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public LinkedList<String> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<String> children) {
        this.children = children;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getZorder() {
        return zorder;
    }

    public void setZorder(int zorder) {
        this.zorder = zorder;
    }

    public boolean isContained() {
        return contained;
    }

    public void setContained(boolean contained) {
        this.contained = contained;
    }
    
    /*
     * key 为序号，string为BaseDataConfig的ID
     */
    public Map<String, String> getZorders() {
        return zorders;
    }

    /*
     * key 为序号，string为BaseDataConfig的ID
     */
    public void setZorders(Map<String, String> zorders) {
        this.zorders = zorders;
    }

   

   
    
    
    
}
