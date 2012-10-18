/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 关联属性中的属性为一个map.即当map中的一个值发生了变化，将通知注册他的DIV与显示<br>
 * 他的组件。
 */
public class MapPropertys   implements Cloneable,Serializable {
     
    /**
     * TYPE开头，特指sourceType的类型。
     * TYPE_EDIT，由显示属性的控件发出属性更改
     */
    final public static  String TYPE_EDIT="type_edit";
    /**
     * TYPE开头，特指sourceType的类型。
     * TYPE_OBJECT-- 由真实物体发出 属性更改
     */
    final public static  String TYPE_OBJECT="type_object";
    /**
     * TYPE开头，特指sourceType的类型。
     * TYPE_INIT-- 初始化产生属性变化
     */
    final public static  String TYPE_INIT="type_init";
    
    /**
     * 由于本《抽象属性》模型 即MapPropertys，既要与《真实物体》即DIV相联系，<br>
     * 又要与《属性显示与设置》或即PropertyPanelExtra(这个允许为其他）。<br>
     * 相联系。即他们都要注册“《抽象属性》模型中的属性变化事件”，以便各自相应<br>
     * 变化。为了既要隔离他们，又要让他们区别是谁产生的事件源，故扩展两个名为<br>
     * sourceType objectId的属性
     */
    public class MapPropertyChangeEvent extends PropertyChangeEvent{
        /*
         * 事件最初源头类型，源编号。如果是由DIV产生事件，则是DIV的ID
         */
        public String sourceType="";
        /**
         * 《真实物体》的ID值，唯一的。事件中必须传递该值。
         */
        public String objectId="";
        
        public MapPropertyChangeEvent(Object source, String propertyName, 
              PropertyUnit  lastValue, Object newValue)
        {
           
            super(source, propertyName, lastValue, newValue);
        }
         public MapPropertyChangeEvent(String sourceType,String objectId,
                 Object source,  String propertyName,  PropertyUnit  lastValue,
                  Object newValue)
         {
            super(source, propertyName, lastValue, newValue);
            this.sourceType=sourceType;
            this.objectId=objectId;
        }
    }
    public class PropertyUnit implements Cloneable {
        /**
         * 属性名
         */
        public String name;
        /**
         * 显示名称
         */
        public String title="";
        /**
         * 属性类型
         */
        public Class  type;
        /**
         * 属性值
         */
        public Object value;     
        /**
         * 属性值编码，供显示用
         */
        public String strValue;  
        /**
         * 属性禁止值
         */
        public boolean disable=false;
        /**
         * 索引，用于属性显示的定位。-1强行规定为没有索引
         */
        public int index=-1;; 
        
        public PropertyUnit(){ }      
        
        public PropertyUnit(String name,String title,Class type, String strValue,Object value) {
            this.name = name;
            this.type = type;
            this.value = value;
            this.title=title;
            this.strValue=strValue;
            //debug
            PropertyCriterion.containCheck(this, name);
        }
        
         public PropertyUnit(String name,String title,String strValue, Object value){
            
            this.name = name;
            this.type = value==null?null:value.getClass();
            this.value = value;
            this.title=title;
            this.strValue=strValue;
             //debug
             PropertyCriterion.containCheck(this, name);
   
         }    

      
       
         
        @Override
         public  PropertyUnit clone(){
            PropertyUnit p =null;
            try {
                 p=(PropertyUnit) super.clone();               
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(MapPropertys.class.getName()).log(Level.SEVERE, 
                        null, ex
                );
            }
            
            return p; 
         }     
    }
       
    /**
     * 存储具体属性值,Key 与PropertyUnit的name 必须保持一致
     */
    private Map<String,PropertyUnit> map=new LinkedHashMap();
    
   
    //<editor-fold desc="封装Map维护方法" >
    public Set<Map.Entry<String,PropertyUnit>> entrySet(){
       
        return map.entrySet();
    }
    public void clear(){
        map.clear();
        
    }
 
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }


    public boolean containsValue(PropertyUnit value) {
        return map.containsValue(value);
    }

    public PropertyUnit put(String name,Class type,String title,
            Object value,String strValue,int index)
    {
        PropertyUnit pu=new PropertyUnit();
        pu.name=name;
        pu.index=index;
        pu.type=type;
        pu.title=title;       
        pu.value=value;
        pu.strValue=strValue;
        map.put(name, pu);
        return pu;        
    }
    
    public PropertyUnit put(String name)
    {
        PropertyUnit pu=new PropertyUnit();
        pu.name=name;
        map.put(name, pu);
        return pu;        
    }

    public Object put(String key, PropertyUnit value) {
         //debug
         PropertyCriterion.containCheck(this, key);
         PropertyCriterion.containCheck(this, value.name);
        return map.put(key, value);
    }


    public boolean isEmpty() {
        return map.isEmpty();
    }


    public int size() {
        return map.size();
    }

    
    public void putAll(Map m) {
        map.putAll(m);
        //debug
        PropertyCriterion.containCheck(this, m);
    }

    public Object remove(String key) {          
         return map.remove(key);
    }
    
    //</editor-fold>
    @Override
    public MapPropertys clone(){
        MapPropertys o=null;
        try {
            o=(MapPropertys) super.clone();        
            if (map==null) return null;
            LinkedHashMap<String,PropertyUnit> m=new LinkedHashMap();
            for(  Entry<String,PropertyUnit>  en:map.entrySet() ){
                String k=en.getKey(); 
                m.put(en.getKey() , en.getValue().clone());
            }
           o.map=  (LinkedHashMap<String, PropertyUnit>) m;            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(MapPropertys.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
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
    
    /**
     * 不会产生事件
     */
    public void setProperty(PropertyUnit propertyUnit){            
        map.put(propertyUnit.name, propertyUnit);        
    }
    
    /**
     * 会产生事件
     * objectId,当前操作物体的ID值。如果是更改ID，这是更改前的ID
     */
    public void setProperty(String sourceType,String objectId,
            PropertyUnit newValue)
    {
        if (newValue==null)  return;      
        
        String name1=newValue.name;    
        PropertyUnit last=map.get(name1);
        if (last!=null) last=last.clone();
        
        MapPropertyChangeEvent e=new MapPropertyChangeEvent(sourceType,objectId,this,
                name1, last, newValue.clone()
        );
        mapChanges.firePropertyChange(e);   
        map.put(name1, newValue);
        
    }
    
    /**
     * 不会产生事件
     */
    public void setPropertyValue(String name,String strValue,Object value)  {        
        PropertyUnit p =map.get(name);
        if (p==null) return;
        if (value.getClass().getName().equals(p.type.getName())){
            p.strValue=strValue;
            p.value=value;
        } else {
            return;
        }      
    }
    
    /**
     * 会产生事件
     */
    public void setPropertyValue(String sourceType,String objectId,
            String name,String strValue,Object value) 
    {
       PropertyUnit p =map.get(name);
       PropertyUnit p1=p.clone();
       if (p==null) return;
       if (value!=null&& value.getClass().getName().equals(p.type.getName())){
           p.value=value;
           p.strValue=strValue;
       } else if(value==null) {
           p.strValue="";
           p.value=null;
       }   else {
           return;
       }       
       MapPropertyChangeEvent e=new MapPropertyChangeEvent(sourceType,objectId,
               this,name, p1,p.clone());
       mapChanges.firePropertyChange(e);     
    }
    
    public PropertyUnit getProperty(String name){
       
        return map.get(name);
    }
    
    /**
     * 注:这里反回的是clone，牺牲了效率。不考虑非法改变值，可直接返回map
     */
    public Map<String,PropertyUnit> getPropertysClone(){
        LinkedHashMap m=(LinkedHashMap) map;        
        return (Map<String, PropertyUnit>) m;
    }
    
    /**
     * 初始值，不触发属性改变事件机制。
     * @param p 第二维格式示例：｛"width",“宽",12}
     */ 
    public void initPropertys(Object p[][]){
        PropertyUnit pu;
        int i=0;
        for (Object[] o:p){               
            pu=new PropertyUnit((String)o[0],(String)o[1],(Class)o[2],(String)o[3],o[4]);
            pu.index=i;
            i++;
            map.put(pu.name, pu);
        }
        //debug
        PropertyCriterion.containCheck(this, map);
    }
    
    /**
     * 初始值，不触发属性改变事件机制。
     * @param list  Object[]格式示例：｛"width",“宽","12",12}
     */
    public void initPropertys(List<Object[]> list){
        PropertyUnit pu;     
        int i=0;
        for (Object[] o:list){            
            pu=new PropertyUnit((String)o[0],(String)o[1],(String)o[2],o[3]);
            pu.index=i;
            i++;
            map.put(pu.name, pu);
        }
        //debug
        PropertyCriterion.containCheck(this, map);
    }
    
    // <editor-fold defaultstate="collapsed" desc="测试"> 
    
    class UseCase implements PropertyChangeListener  {
        public  int x=0;
        private  MapPropertys mp;
        public UseCase(MapPropertys mp){
            this.mp=mp;
            this.mp.addMapChangesListener(this);
        }
                
        @Override
        public void propertyChange(PropertyChangeEvent evt) {      
              //(evt.getPropertyName().equals("size")
            MapPropertyChangeEvent e=(MapPropertyChangeEvent) evt;
            if (  e.sourceType.equals("1")
                    ) {  
                 PropertyUnit pu=(PropertyUnit) evt.getOldValue();
                  System.out.println("事件正处理中===");
                 if (pu!=null) {                    
                     System.out.println("旧参数名:"+pu.name +
                                ";          值:"+pu.value.toString());  
                 }
                 pu=(PropertyUnit) evt.getNewValue();
                 if (pu!=null) {                     
                     System.out.println("新参数名:"+pu.name +
                                ";          值:"+pu.value.toString());                      
                 }
                 System.out.println("事件处理完毕===");
            }          
        }
        
       
    }
    
    public void run(){
        MapPropertys sdt=new MapPropertys();
        
        UseCase u=new UseCase(sdt);
        
        PropertyUnit pu=new PropertyUnit();
        pu.name="dd";
        pu.value="sxxdd";
        sdt.setProperty(pu);   
        pu=new PropertyUnit();
        pu.name="size";
        pu.value=12;
        sdt.setProperty(pu);        
        pu=sdt.getProperty("size");
        
        System.out.println("参数名:"+pu.name +
                ";          值:"+pu.value.toString());  
        
        System.out.println("========属性改变，并显示======");
        pu=new PropertyUnit();
        pu.name="size";
        pu.value=36;
        sdt.setProperty(String.class.getName(),"1",pu);
        
        pu=sdt.getProperty("size");
        System.out.println("参数名:"+pu.name +
                ";          值:"+pu.value.toString());  
        System.out.println("========属性改变，显示完毕=====");
         
         
    }    
    
    public void run1(){
        MapPropertys ma=new MapPropertys();
        PropertyUnit pu=new PropertyUnit();
        pu.name="name";
        pu.value="aa";
        ma.setProperty(pu);   
        
        MapPropertys ma1=ma.clone();
        pu=new PropertyUnit();
        pu.name="name";
        pu.value="bb";
        ma1.setProperty(pu);
       System.out.println("========属性改变，显示完毕=====");
        
    }
    public static void main(String args[]) {
        MapPropertys ma=new MapPropertys();
        
        ma.run1();
        
    }
     // </editor-fold >
}
