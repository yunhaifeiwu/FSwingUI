/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.lang;

import java.util.ArrayList;
import java.util.List;

public class TestFactory<T> {
    private Class <T> cls;
    public String testabc="";
    private ArrayList<T> list =new ArrayList();
    
    //---------------注意构造函数传的是一个类型名------------------------
    public TestFactory(String clsName) throws ClassNotFoundException{
        cls=(Class<T>) Class.forName(clsName);
    }

    //--------------------------生成了cls的实例---------------------------------
    //--------  -汗, 由类型名一样能生成cls的实例,如：-------------------
//-------  Class.forName(clsName).newInstance()--------------------------
//-----------------------------------------------------------------------------------
    public T getInstance() throws InstantiationException, IllegalAccessException{        
        return cls.newInstance();        
    }
    
     public List<T> getList()  {      
       
         return list;        
    }
     
    public static void main(String[] args) 
                throws ClassNotFoundException, 
                InstantiationException, IllegalAccessException 
    {
        
        TestFactory tf=new TestFactory(String.class.getName().toString());
        String aa=(String)tf.getInstance();
       
        List<String> l= tf.getList();
        l.add("ddd");
        System.out.println(aa);
        System.out.println(l.get(0));
    }
}

