/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cloud
 */
public class Subject  implements Cloneable{
    /**
     * 主题集中第一个SubjectEntity，关键字为PRIMARY所设
     */
    public static final String MAIN=SubjectCriterion.MAIN;
    
    private String id;
    private String title;
    private int index=-1;
    private LinkedHashMap<String,SubjectEntity> subjectEntitys;

    public Subject(String id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public Subject(){}
    
    @Override
    public Subject clone(){
        Subject o=null;
        try {
            o=(Subject) super.clone();        
            if (subjectEntitys==null) return o;
            LinkedHashMap<String,SubjectEntity> m=new LinkedHashMap();
            for(  Entry<String,SubjectEntity>  en:subjectEntitys.entrySet() ){
                String k=en.getKey(); 
                if(en.getValue()==null) {
                    m.put(k, null);
                } else {
                    m.put(k , en.getValue().clone());
                }
               
            }
           o.subjectEntitys=  (LinkedHashMap<String, SubjectEntity>) m;            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Subject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }
   
    //<editor-fold desc="getter and setter">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public SubjectEntity getMainSubjectEntity(){
        if (subjectEntitys==null )return null;
        return subjectEntitys.get(this.MAIN);
    }
    
    //</editor-fold>
    
    //<editor-fold desc="封装subjectEntitys的操作 ">
    public void clear() {
        if(subjectEntitys==null) return;
        subjectEntitys.clear();
    }

    public boolean containsKey(String key) {
        if(subjectEntitys==null) return false;
        return subjectEntitys.containsKey(key);
    }

    public Set<Entry<String, SubjectEntity>> entrySet() {
        if(subjectEntitys==null) return null;
        return subjectEntitys.entrySet();
    }
 

    public SubjectEntity get(String key) {
        if(subjectEntitys==null) return null;
        return subjectEntitys.get(key);
    }

    public boolean isEmpty() {
        if(subjectEntitys==null) return true;
        return subjectEntitys.isEmpty();
    }

    public Set<String> keySet() {
        if(subjectEntitys==null) return null;
        return subjectEntitys.keySet();
    }

    public SubjectEntity put(String key, SubjectEntity value) {
        if(subjectEntitys==null) {
            subjectEntitys=new LinkedHashMap();
        }
        
        return subjectEntitys.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends SubjectEntity> m) {
        if(subjectEntitys==null) {
            subjectEntitys=new LinkedHashMap();
        }
        subjectEntitys.putAll(m);
    }

    public SubjectEntity remove(String key) {
        if(subjectEntitys==null) return null;
        return subjectEntitys.remove(key);
    }

    public int size() {
        if(subjectEntitys==null) return 0;
        return subjectEntitys.size();
    }

    public boolean containsValue(SubjectEntity value) {
        if(subjectEntitys==null) return false;
        return subjectEntitys.containsValue(value);
    }

    public Collection<SubjectEntity> values() {
        if(subjectEntitys==null) return null;
        return subjectEntitys.values();
    }
    //</editor-fold>
    
}
