/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author cloud
 */
public class MapSort1 {
    private HashMap map=new HashMap(); 
    private Set keySet=map.keySet(); 
  

    public Object get(String key)   { 
        return  map.get(key); 
    } 

    public   void   put(String key,  Object value){ 
        map.put(key,value); 
    } 

    public   void   sort()   { 
        List list=new ArrayList(map.keySet()); 

        Collections.sort(list,new Comparator(){ 
            public int compare(Object a,  Object b)   { 
            return   a.toString().toLowerCase().compareTo(b.toString() 
                .toLowerCase()); 
            } 
        }); 
        this.keySet=new TreeSet(list); 
    } 

    public Set keySet(){ 
        return this.keySet; 
    } 

    public static void main(String [] args){ 
        MapSort1 map=new MapSort1(); 
        map.put("1", "yi"); 
        map.put("8", "ba"); 
        map.put("9", "jiu"); 
        map.put("7", "qi"); 
        map.put("5", "wu"); 
        map.put("6", "liu"); 
        map.put("4", "si"); 
        map.put("3", "san"); 
        map.put("2", "er"); 
        map.put("4", "si11"); 
        
//        List<String> list=new ArrayList();
//        list.set(1, "aa");
//        System.out.println("=========list begin====");
//        for(String s:list){
//            System.out.println(s);
//        }
//        System.out.println("======list end========");
        
        for   (Iterator it=map.keySet().iterator();it.hasNext();) { 
            String key=(String) it.next();          
        } 
                
        // System.out.println( "\n "); 
        map.sort(); 
        for   (Iterator it=map.keySet().iterator();it.hasNext();) { 
            String key=(String) it.next(); 
            System.out.println("key["+key+"],value["+map.get(key)+"]"); 
        } 
    }
}
