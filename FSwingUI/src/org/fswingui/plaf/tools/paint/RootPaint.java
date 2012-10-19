/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.tools.paint;

import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JComponent;

/**
 *
 * @author cloud
 */
public class RootPaint extends AbstractPaint {
    
    public class PaintChangeEvent extends PropertyChangeEvent{
        /*
         * 事件最初源头类型
         */
        public String sourceType="";
        /**
         * AbstractPaint 所代表事物的ID
         */
        public String objectId="";
        
        public PaintChangeEvent(Object source, String propertyName, 
                Object oldValue, Object newValue)
        {
            super(source, propertyName, oldValue, newValue);
        }
         public PaintChangeEvent(String sourceType,String objectId,
                 Object source,  String propertyName, 
                 Object oldValue, Object newValue)
         {
            super(source, propertyName, oldValue, newValue);
            this.sourceType=sourceType;
            this.objectId=objectId;
        }
    }
    
    private Map <String, AbstractPaint > items=new LinkedHashMap();
    
    public RootPaint(){
        
        
    }
    
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
    
   
   public  boolean isNodeFlag(){
        return true;
    }
   
    @Override
    protected void paintImp(Graphics g, JComponent c) {}

    @Override
    protected void setParameter() {}
    
    //<editor-fold defaultstate="collapsed" desc=" paints  "> 
    
    public void putPaint(AbstractPaint paint){
        if (paint==null) return;
        
        items.put(paint.getPaintID(),paint);
    }
    
    /**
     * 增加且产生一个事件
     * @param sourceType  产生事件者的类型
     * @param objectId  paint所代表对象的ID
     * @param paint 
     */
    public void putPaint(String sourceType,String objectId,
            AbstractPaint paint){
        if (paint==null)  return;      
        
        String id=paint.getPaintID();

        
        
        AbstractPaint old=items.get(objectId);        
   
        if (old!=null) {
            old=old.clone();
        }
        
        AbstractPaint newpaint= paint.clone();
        
       if(id.equals(objectId)){
            items.remove(objectId);
        } 
       
        items.put(id, newpaint);
        PaintChangeEvent e=new PaintChangeEvent(sourceType,objectId,this,
                id, old, newpaint
        );
        mapChanges.firePropertyChange(e);   
        
        
    }
    
    /**
     * 删除且产生一个事件
     * @param sourceType  产生事件者的类型
     * @param objectId  paint所代表对象的ID
     * @param paint 
     */
    public void removePaint(String sourceType,String objectId,
            AbstractPaint paint){
        if (objectId==null)  return;      
                 
        AbstractPaint old=items.get(objectId);
        if (old!=null) old=old.clone();
        items.remove(objectId);
        
        String id=old.getPaintID();
          
        PaintChangeEvent e=new PaintChangeEvent(sourceType,objectId,this,
                "", old, null
        );
        mapChanges.firePropertyChange(e);   
    }
    
    public AbstractPaint getPaint(String id){
        return items.get(id);
    }
    
    
    public Map<String, AbstractPaint> getItems() {
        return items;
    }
    
    
    //</editor-fold>
    
}
