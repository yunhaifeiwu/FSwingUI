/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.lang;
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.Collections;  
import java.util.Comparator;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Scanner;  
import java.util.Set;  
import java.util.SortedMap;  
import java.util.TreeMap;  
import java.util.Map.Entry;  

/** 
 * 可能会遇到这样的情况，我可能要对Map<key,value>的集合进行排序，而这种排序又分为两种情况，你可能按key值排序； 
 * 另外你也可能会遇到按value值进行排序的情况。 
 * 大家都知道，默认的情况下，TreeMap：是按key升序,进行排序的；LinkedHashMap：是按加入顺序进行排序的 
 * ；HashMap：内部数值的顺序并不是以存放的先后顺序为主 
 * ，而是以hash值的顺序为主，其次才是存放的先后顺序。在这里我们只讨论如何实现HashMap的排序。 
 *  
 */  
public class MapSortTest {
  
    public static void main(String[] args) {  
        //初始化map  
        Map<String, Integer> map=getMapInstance();  
        printMap(map);  
          
        //选择操作  
        Scanner input=new Scanner(System.in);  
        int num=input.nextInt();  
        switch (num) {  
            case 0:  
                System.exit(0);  
                break;  
            case 1:  
                Map<String, Integer> sortMaps = sortMapByKey(map);  
                printMap(sortMaps);  
                break;  
            case 2:  
                sortMapByValue(map);  
                break;  
            case 3:  
                mapSortByKey(map);  
                break;  
            case 4:  
                mapSortByValue(map);  
                break;  
            default:  
                System.out.println("error input!");  
                break;  
        }  
          
    }  
  
    public static Map sortMapByKey(Map map) {  
        Map<Object, Object> mapVK = new TreeMap<Object, Object>(  
                new Comparator<Object>() {  
                    public int compare(Object obj1, Object obj2) {  
                        String v1 = (String) obj1;  
                        String v2 = (String) obj2;  
                        int s = v2.compareTo(v1);  
                        return s;  
                    }  
                });  
        Set col = map.keySet();  
        Iterator iter = col.iterator();  
        while (iter.hasNext()) {  
            String key = (String) iter.next();  
            Integer value = (Integer) map.get(key);  
            mapVK.put(key, value);  
        }  
        return mapVK;  
    }  
  
    public static void sortMapByValue(Map maps) {  
        List<Map.Entry<String, Integer>> info = new ArrayList<Map.Entry<String, Integer>>(maps.entrySet());  
        Collections.sort(info, new Comparator<Map.Entry<String, Integer>>() {  
            public int compare(Map.Entry<String, Integer> obj1,Map.Entry<String, Integer> obj2) {  
                return obj2.getValue() - obj1.getValue();  
            }  
        });  
        for (int j = 0; j < info.size(); j++) {  
            System.out.println(info.get(j).getKey() + "------->"+ info.get(j).getValue());  
        }  
    }  
  
    // ====================================================================================  
    private static SortedMap<String, Integer> mapSortByKey(Map<String, Integer> unsort_map) {  
        TreeMap<String, Integer> result = new TreeMap<String, Integer>();  
        Object[] unsort_key = unsort_map.keySet().toArray();  
        Arrays.sort(unsort_key);  
        for (int i = 0; i < unsort_key.length; i++) {  
            result.put(unsort_key[i].toString(), unsort_map.get(unsort_key[i]));  
        }  
        return result.tailMap(result.firstKey());  
    }  
      
    public static void mapSortByValue(Map map) {  
        List arrayList = new ArrayList(map.entrySet());  
        Collections.sort(arrayList,new Comparator(){  
            @Override  
            public int compare(Object o1, Object o2) {  
                Map.Entry obj1 = (Map.Entry)o1;  
                Map.Entry obj2 = (Map.Entry)o2;           
                return obj1.getValue().toString().compareTo(obj2.getValue().toString());  
            }  
        });  
        for(Iterator it = arrayList.iterator();it.hasNext();){  
            Map.Entry entry = (Map.Entry)it.next();  
            System.out.println(entry.getKey()+":"+entry.getValue());  
        }  
    }  
    public static void abc(Map map){  
        TreeMap treemap = new TreeMap(map);  
    }  
    public static void abcd() {  
        Map<String, Integer> keyfreqs = new HashMap<String, Integer>();  
        ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(keyfreqs.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {  
            public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2) {  
                return (o2.getValue() - o1.getValue());  
            }  
        });  
        for (Entry<String, Integer> e : list) {  
            System.out.println(e.getKey() + "::::" + e.getValue());  
        }  
    }  
    public static Map getMapInstance(){  
        Map<String, Integer> map = new HashMap<String, Integer>();  
        map.put("apple",40);  
        map.put("boy",30);  
        map.put("cat",20);  
        map.put("dog",10);  
        return map;  
    }  
    public static void printMap(Map map){  
        Iterator i = map.entrySet().iterator();  
        while (i.hasNext()) {  
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) i.next();  
            System.out.println(entry.getKey() + ":"+ entry.getValue());  
        }  
        System.out.println("========================================================");  
    }  
}  

