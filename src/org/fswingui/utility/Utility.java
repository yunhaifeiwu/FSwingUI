/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.utility;

import java.awt.Color;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import sun.applet.Main;

/**
 *
 * @author cloud
 */
public class Utility {
    public final static String PAINT_PACKAGE
            =AbstractPaint.class.getPackage().getName();
    /*
     * 把16进字符串颜色编码转换成颜色
     */
    public static Color fromHexString(String s){
        Color c=null;
            
        try {
            String t1=s.substring(2);
            Map<Integer,Integer> map=new HashMap<Integer,Integer>();
            int i=0;
            do {
                t1=s.substring(2);
                String t2=s.substring(0, 2);
                s=t1;
                map.put(i,Integer.valueOf(t2,16)  );
                i++;
            } while (!s.isEmpty());
            
            switch (i){
                case 3:
                    c=new java.awt.Color(map.get(0), map.get(1), map.get(2));
                    break;
                case 4:
                    c=new java.awt.Color(map.get(1), map.get(2), map.get(3),map.get(0));
                    break;
                default:
                    c=null;

            }
        
            
        } catch (Exception e){
            
            
        } finally {
            return c;
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Test"> 
    public static void fromHexString_Test(){
        Color c=  Utility.fromHexString("ffffc800");
        System.out.println(c);
    }
    
    /** * 根据指定包名，得包含子包中所有的类名（不含目录）
     * @param path   包名，形如：org.fswingui.plaf.tools.paint
     * @param classNameType 类名格式，true 为完整类名，false为简写类名
     * @return 返回类名
     */
    public static List<String> getFiles(String packageName,Boolean classNameType){
        
        List<String> rlist=new ArrayList();      
        String str=packageName;
        try {
           URL url = Main.class.getResource("/");
           URI uri = url.toURI();
           str=uri.getPath()+str.replaceAll("[.]", "/");
           File f=new File(str);
           if ( f==null) return null;
          
           Queue<File> queue=new LinkedList<File>();  
           queue.offer(f);
        
           while (queue.size()>0){               
               f=queue.poll();
               if (f==null) continue;                
               if (f.listFiles()!=null) {
                  for(File ft:f.listFiles())//添加所有直接子文件到队列
                      queue.offer(ft); 
               }
               if (!f.isDirectory()){
                   String temp=f.getPath();
                   if (!temp.contains(".class")) continue;    
                   if (classNameType) {
                       //按包名分割子路径
                       String[] p=temp.split(packageName.replace("[.]", "\\\\"));                   
                       p=p[p.length-1].split(".class");
                       temp=p[0].substring(1);//去除路径中的第一个"\"符号
                       //把路径分界符\" 转换成包分界符
                       temp=temp.replaceAll("\\\\", "."); 
                       temp=packageName+"."+temp;
                       rlist.add(temp);  
                   } else {
                       String[] p=temp.split("\\\\");
                       p=p[p.length-1].split(".class");
                       rlist.add(p[0]);  
                   }
               }  

           }
           
        } catch (URISyntaxException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        return rlist;
    }
    
    /**
     * 在 Utility.PAINT_PACKAGE 所指定包中，找到所有继承了AbstractPaint的类，
     * 并生成实例返回。
     */
    public static List<AbstractPaint> getExpandPaint(){     
           List<String> liststr=getFiles(Utility.PAINT_PACKAGE+".expand",true); 
           List<AbstractPaint> list=new ArrayList();
            
           for (String s:liststr){
               try {
                    Class c=Class.forName(s);
                    if (AbstractPaint.class.isAssignableFrom(c)){
                        AbstractPaint p;
                        try {
                            p = (AbstractPaint) c.newInstance();
                            list.add(p);
                        } catch (InstantiationException ex) {
                        } catch (IllegalAccessException ex) {}
                      
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
                }
           } 
           System.out.println();
           return list;
        
    }
    
    public static  void main(String[] args){
       fromHexString_Test();
     
//       List<AbstractPaint> lis=getExpandPaint();
   
       System.out.println();
    }
  //</editor-fold>
}
