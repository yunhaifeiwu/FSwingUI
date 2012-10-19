/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 主键是整型K 
 */
public class MapSort <Intger,V>{
    
    private HashMap<Intger,V> map=new HashMap<Intger,V>(); 
    private Set keySet=map.keySet(); 
    private Intger MaxKey;
    private  ArrayList<Intger> list;
    private boolean desc1=false;
    private static  boolean desc2=false;

    
    public static Map sortMapByKey(Map map,boolean desc) {  
        MapSort.desc2=desc;
        Map<Object, Object> mapVK = new TreeMap<Object, Object>(  
                new Comparator<Object>() {  
                    public int compare(Object obj1, Object obj2) {  
                        Integer v1 = (Integer) obj1;  
                        Integer v2 = (Integer) obj2;  
                        int s=0;
                        if (MapSort.desc2){
                            s = v2.compareTo(v1);
                        }  else {
                            s = v1.compareTo(v2);
                        }
                        return s;  
                    }  
                });  
        Set col = map.keySet();  
        Iterator iter = col.iterator();  
        while (iter.hasNext()) {  
            Object   key =   iter.next();  
            Object value =  map.get(key);  
            mapVK.put(key, value);  
        }  
        return mapVK;  
    }  
    
    public V get(Intger key)   { 
       
        return  map.get(key); 
    } 

    public Intger getMaxKey() {
        if (list==null)  this.sort();
        
        return list.get(list.size()-1);
    }

  
 

    
    public   void   put(Intger key,  V value){ 
         map.put(key, value);
         
       
    } 
    
    public   boolean   containsKey(Intger key){ 
        return map.containsKey(key);
    } 
    
   
    public void clear() {
        map.clear();
    }
 
    public boolean containsValue(V value) {
        return map.containsValue(value);
    }
 
    public Object remove(Intger key) {
        return map.remove(key);
    }
 
    public boolean isEmpty() {
        return map.isEmpty();
    }

    public int size() {
        return map.size();
    }

    public Collection values() {
       
        return map.values();
    }
        
        
        
    
    public   void   sort(){
        sort(false);
    }
    public   void  sort(boolean desc)   { 
        Map<Integer, V> mapVK = new TreeMap<Integer, V>(  
        new Comparator<Object>() {  
            public int compare(Object obj1, Object obj2) {  
                Integer v1 = (Integer) obj1;  
                Integer v2 = (Integer) obj2;  
                int s=0;
                if (desc1){
                    s = v2.compareTo(v1);
                }  else {
                    s = v1.compareTo(v2);
                }
                return s;  
            }  
        });  
        Set col = map.keySet();  
        Iterator iter = col.iterator();  
        while (iter.hasNext()) {  
            Integer key = (Integer) iter.next();  
            V value = (V) map.get(key);  
            mapVK.put(key, value);  
            
        }  
       keySet=mapVK.keySet();
        
       
    } 

    public Set keySet(){ 
        return this.keySet; 
    } 

    public static void main(String [] args){ 
        
        MapSort map=new MapSort(); 
        map.put(new Integer(1), "yi"); 
        map.put(new Integer(8), "ba"); 
        map.put(new Integer(9), "jiu"); 
        map.put(new Integer(7), "qi"); 
        map.put(new Integer(5), "wu"); 
        map.put(new Integer(6), "liu"); 
        map.put(new Integer(4), "si"); 
        map.put(new Integer(3), "san"); 
        map.put(new Integer(2), "er"); 
        map.put(new Integer(10), "10a"); 
        map.put(new Integer(19), "19a"); 
        map.put(new Integer(23), "23a"); 
        
        
                
        // System.out.println( "\n "); 
        
        map.sort(true); 
        
        for   (Iterator it=map.keySet().iterator();it.hasNext();) { 
            Integer key=(Integer) it.next(); 
            System.out.println("key["+key+"],value["+map.get(key)+"]"); 
        } 
    }
}
