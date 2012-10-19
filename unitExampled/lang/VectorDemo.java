/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.lang;

import java.util.Enumeration;
import java.util.Vector;

public class VectorDemo{
    public static void main(String[] args){

        //Vector的创建
        //使用Vector的构造方法进行创建
        Vector<String> v = new Vector(4);

        //向Vector中添加元素
        //使用add方法直接添加元素
        v.addElement("Test0");
        v.addElement("Test1");
        v.addElement("Test2");
        v.addElement("Test3");
        v.addElement("Test4");
        v.setElementAt("Test5", 2);

        //从Vector中删除元素
        v.removeElement("Test0"); //删除指定内容的元素
        v.removeElementAt(0);
        

        //获得Vector中已有元素的个数
        int size = v.size();
        System.out.println("size:" + size);

        //遍历Vector中的元素
        for(int i = 0;i < v.size();i++){
            System.out.println(v.get(i));
        }
        System.out.println("==================");
        for (Enumeration<String> e = v.elements(); e.hasMoreElements();){
            System.out.println(e.nextElement());
        }
       System.out.println("**********************");    
        for(String s:v){
             System.out.println(s);
        }
    }
}
