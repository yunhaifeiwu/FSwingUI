/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf;

import java.awt.Color;
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
public class Style implements Cloneable {
    private String id;
    private String title;
    private Color mainColor;
    private LinkedHashMap<String,Subject> subjects=new LinkedHashMap();
    
    public Style(){
        for (Map.Entry<String,Subject> en:SubjectCriterion.getKeySubject().entrySet()){           
            subjects.put(en.getKey(),en.getValue().clone());
        }
    }
    @Override
    public Style clone(){
        Style o=null;
        try {
            o=(Style) super.clone();        
            if (subjects==null) return null;
            LinkedHashMap<String,Subject> m=new LinkedHashMap();
            for(  Entry<String,Subject>  en:subjects.entrySet() ){
                String k=en.getKey(); 
                m.put(en.getKey() , en.getValue().clone());
            }
           o.subjects=  (LinkedHashMap<String, Subject>) m;            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Style.class.getName()).log(Level.SEVERE, null, ex);
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

    public Color getMainColor() {
        return mainColor;
    }

    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
    }

    
    
    //</editor-fold>
    
    //<editor-fold desc="封装subjectEntitys的操作 ">
    public void clear() {
        if(subjects==null) return;
        subjects.clear();
    }

    public boolean containsKey(String key) {
        if(subjects==null) return false;
        return subjects.containsKey(key);
    }

    public Set<Entry<String, Subject>> entrySet() {
        if(subjects==null) return null;
        return subjects.entrySet();
    }
 

    public Subject get(String key) {
        if(subjects==null) return null;
        SubjectCriterion.containCheckSub(this, key);
        return subjects.get(key);
    }

    public boolean isEmpty() {
        if(subjects==null) return true;
        return subjects.isEmpty();
    }

    public Set<String> keySet() {
        if(subjects==null) return null;
        return subjects.keySet();
    }

    public Subject put(String key, Subject value) {
        if(subjects==null) {
            subjects=new LinkedHashMap();
        }
        SubjectCriterion.containCheckSub(this, key);
        return subjects.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends Subject> m) {
        if(subjects==null) {
            subjects=new LinkedHashMap();
        }
        SubjectCriterion.containCheckSub(this,(Map) m);
        subjects.putAll(m);
    }

    public Subject remove(String key) {
        if(subjects==null) return null;
        return subjects.remove(key);
    }

    public int size() {
        if(subjects==null) return 0;
        return subjects.size();
    }

    public boolean containsValue(Subject value) {
        if(subjects==null) return false;
        return subjects.containsValue(value);
    }

    public Collection<Subject> values() {
        if(subjects==null) return null;
        return subjects.values();
    }
    //</editor-fold>
    
    
}
