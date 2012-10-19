/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.lang;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
 
/**
 *
 * @author Administrator
 */
public class ConcurrentHashMapTest {
    
    public static void main(String[] args){
        Map<String,String> map =   new ConcurrentHashMap(); 
        
        map.put("guest1", "123456"); 
        map.put("guest2", "234567"); 
        map.put("guest3", "345678"); 

        Iterator<String> ite=map.keySet().iterator(); 
        String key=""; 
        String value = ""; 
        while(ite.hasNext()){ 
            key=ite.next(); 
            value=map.get(key); 
            map.remove(key); 
            System.out.println("remove key: " + key); 
        } 
        System.out.println("remove key: " + key); 
    }
}
