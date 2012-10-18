/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base.pub;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *有两个map，这两个Map都有关联属性，以作两个需要相互发送信息的组件，通过
 * 本抽象而相互耦合。
 */
public class MapBindTwo<K,V> {
    private Map<K,V> mapA;
    private Map<K,V> mapB;
    
    //<editor-fold   desc="事件"> 
    private PropertyChangeSupport mapAChanges = new PropertyChangeSupport(this);
    private PropertyChangeSupport mapBChanges = new PropertyChangeSupport(this);
   
    /**
    * 注册mapAChanges属性事件
    */
    public void addMapAChangesListener (PropertyChangeListener listener) {
        mapAChanges.addPropertyChangeListener(listener);
    }
    /**
    * 注销mapAChanges属性事件
    */
    public void removeMapAChangesListene (PropertyChangeListener listener) {
        mapAChanges.removePropertyChangeListener (listener);        
    }
    
    /**
    * 注册mapBChanges属性事件
    */
    public void addMapBChangesListener (PropertyChangeListener listener) {
        mapBChanges.addPropertyChangeListener(listener);
    }
    /**
    * 注销mapBChanges属性事件
    */
    public void removeMapBChangesListene (PropertyChangeListener listener) {
        mapBChanges.removePropertyChangeListener (listener);        
    }
    
    //</editor-fold>
    
    //<editor-fold desc="封装map方法---MapA">
    public int sizeA() {
        if(mapA==null) return 0;
        return mapA.size();
    }

    public boolean isEmptyA() {
        if(mapA==null) return true;
        return mapA.isEmpty();
    }

    public boolean containsKeyA(K key) {
        if(mapA==null) return false;
        return mapA.containsKey(key);
    }

    public boolean containsValueA(V value) {
        if(mapA==null) return false;
        return mapA.containsValue(value);
    }

    public V getA(K key) {
        if(mapA==null) return null;
        return mapA.get(key);
    }

    public V putA(K key, V value) {
        if(mapA==null) mapA=new LinkedHashMap();
        V old=mapA.get(key);        
        V v=mapA.put(key, value);
        PropertyChangeEvent e=new PropertyChangeEvent(this, "put", old, value);
        mapAChanges.firePropertyChange(e);     
        return v;
    }

    public V removeA(K key) {
        if(mapA==null) mapA=new LinkedHashMap();
        V old=mapA.get(key);        
        PropertyChangeEvent e=new PropertyChangeEvent(this, "remove", old, null);
        mapAChanges.firePropertyChange(e);            
        V v=mapA.remove(key);
        return v;
    }

    public void putAllA(Map<? extends K, ? extends V> m) {
        if(mapA==null) mapA=new LinkedHashMap();              
        PropertyChangeEvent e=new PropertyChangeEvent(this, "putAll", mapA, m);
        mapAChanges.firePropertyChange(e);   
        mapA.putAll(m);
         
    }

    public void clearA() {
        if(mapA==null) mapA=new LinkedHashMap();
        PropertyChangeEvent e=new PropertyChangeEvent(this, "clear", mapA, null);
        mapAChanges.firePropertyChange(e);  
        mapA.clear();
    }

    public Set<K> keySetA() {
        if(mapA==null) return null;
        return mapA.keySet();
    }

    public Collection<V> valuesA() {
        if(mapA==null) return null;
        return mapA.values();
    }

    public Set<Map.Entry<K, V>> entrySetA() {
        if(mapA==null) return null;
        return mapA.entrySet();
    }
    //</editor-fold>
    
    //<editor-fold desc="封装map方法---MapB">
    public int sizeB() {
        if(mapB==null) return 0;
        return mapB.size();
    }

    public boolean isEmptyB() {
        if(mapB==null) return true;
        return mapB.isEmpty();
    }

    public boolean containsKeyB(K key) {
        if(mapB==null) return false;
        return mapB.containsKey(key);
    }

    public boolean containsValueB(V value) {
        if(mapB==null) return false;
        return mapB.containsValue(value);
    }

    public V getB(K key) {
        if(mapB==null) return null;
        return mapB.get(key);
    }

    public V putB(K key, V value) {
        if(mapB==null) mapB=new LinkedHashMap();
        V old=mapB.get(key);        
        V v=mapB.put(key, value);
        PropertyChangeEvent e=new PropertyChangeEvent(this, "put", old, value);
        mapBChanges.firePropertyChange(e);     
        return v;
    }

    public V removeB(K key) {
        if(mapB==null) mapB=new LinkedHashMap();
        V old=mapB.get(key);        
        PropertyChangeEvent e=new PropertyChangeEvent(this, "remove", old, null);
        mapBChanges.firePropertyChange(e);            
        V v=mapB.remove(key);
        return v;
    }

    public void putAllB(Map<? extends K, ? extends V> m) {
        if(mapB==null) mapB=new LinkedHashMap();              
        PropertyChangeEvent e=new PropertyChangeEvent(this, "putAll", mapA, m);
        mapBChanges.firePropertyChange(e);   
        mapB.putAll(m);
         
    }

    public void clear() {
        if(mapB==null) mapB=new LinkedHashMap();
        PropertyChangeEvent e=new PropertyChangeEvent(this, "clear", mapA, null);
        mapBChanges.firePropertyChange(e);  
        mapB.clear();
    }

    public Set<K> keySetB() {
        if(mapB==null) return null;
        return mapB.keySet();
    }

    public Collection<V> valuesB() {
        if(mapB==null) return null;
        return mapB.values();
    }

    public Set<Map.Entry<K, V>> entrySetB() {
        if(mapB==null) return null;
        return mapB.entrySet();
    }
    //</editor-fold>
    
}
