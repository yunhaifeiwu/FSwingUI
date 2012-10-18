/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fswingui.plaf.tools.paint.AbstractPaint;

/**
 *
 * @author cloud
 */
public class MapPaints  implements Cloneable {
    public class MapPaintChangeEvent extends PropertyChangeEvent{
        /*
         * 事件最初源头类型
         */
        public String sourceType="";
        /**
         * 
         */
        public String objectId="";
        
        public MapPaintChangeEvent(Object source, String propertyName, 
                Object oldValue, Object newValue)
        {
            super(source, propertyName, oldValue, newValue);
        }
         public MapPaintChangeEvent(String sourceType,String objectId,
                 Object source,  String propertyName, 
                 Object oldValue, Object newValue)
         {
            super(source, propertyName, oldValue, newValue);
            this.sourceType=sourceType;
            this.objectId=objectId;
        }
    }
    
    
    /**
     * 存储具体属性值
     */
    private Map<String,AbstractPaint> map=new LinkedHashMap();
    // <editor-fold defaultstate="collapsed" desc="事件"> 
    private PropertyChangeSupport mapChanges = new PropertyChangeSupport(this);
    /**
    * 注册mapChanges属性事件
    */
    public void addMapChangesListener (PropertyChangeListener listener) {
        mapChanges.addPropertyChangeListener(listener);
    }
    /**
    * 注销mapChanges属性事件
    */
    public void removeMapChangesListene (PropertyChangeListener listener) {
        mapChanges.removePropertyChangeListener (listener);        
    }
    //</editor-fold>
    
    @Override
    public MapPaints clone(){
        MapPaints o=null;
        try {
            o=(MapPaints) super.clone();   
            if (map==null) return null;
            LinkedHashMap<String,AbstractPaint>  m= new LinkedHashMap();
            for(  Entry<String,AbstractPaint>  en:map.entrySet() ){
                String k=en.getKey();
                m.put(en.getKey() , en.getValue().clone());
            }
            o.map=  (LinkedHashMap<String, AbstractPaint>) m;
            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(MapPaints.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return o;
    }

       
    /**
     * 会产生事件
     */
    public void setPaint(String sourceType,String objectId,
            AbstractPaint paint)
    {
        if (paint==null)  return;      
        
        String id=paint.getPaintID();
        AbstractPaint old=map.get(id);
        if (old!=null) old=old.clone();
        
        MapPaintChangeEvent e=new MapPaintChangeEvent(sourceType,objectId,this,
                id, old, paint.clone()
        );
        mapChanges.firePropertyChange(e);   
        map.put(id, paint);
        
    }
    
}
