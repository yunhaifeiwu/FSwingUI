/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.utilities;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.fswingui.plaf.tools.paint.AbstractPaint;
 

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
    
   
    public static void fromHexString_Test(){
        Color c=  Utility.fromHexString("ffffc800");
        System.out.println(c);
    }
    
    /** * 根据指定包名，得包含子包中所有的类名（不含目录）
     * @param path   包名，形如：org.fswingui.plaf.tools.paint
     * @param classNameType 类名格式，true 为完整类名，false为简写类名
     * @return 返回类名
     */
    public static List<String> getFiles(String packageName,Boolean classNameType) {
         
        List<String> rlist=new ArrayList();  
        String str=packageName;
        str=str.replaceAll("[.]", "/")+"/";
        URL url = AbstractPaint.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
            //java.util.jar.JarFile file = new JarFile("E:\\frame\\jdbc\\mysql.jar");
        java.util.jar.JarFile file = null;
        try {
            file = new JarFile(filePath);
        } catch (IOException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
            Enumeration<JarEntry> entrys = file.entries();
            while(entrys.hasMoreElements()){
                JarEntry jar = entrys.nextElement();
                if(jar==null || jar.getName()==null ||
                        !jar.getName().contains(".class")
                ){//不是类，忽略掉
                    continue;
                }
                               
                
                String[] bags=jar.getName().split(str);                
                if(bags.length<=1)  continue;
                String p=jar.getName();     
                if (classNameType) {
                   //把路径分界符\" 转换成包分界符
                   String[] strs=p.split(".class");
                   p=strs[0];
                   p=p.replaceAll("\\\\", "."); 
                   p=p.replaceAll("/", "."); 
                   rlist.add(p);  
               } else {
                   String[] strs=p.split("/");
                   strs=strs[strs.length-1].split(".class");
                   rlist.add(strs[0]);  
               }
             
                 
                
            }
        try {

            file.close();
        } catch (IOException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        String str=packageName;
//        System.out.println("ddddd");
//        try {
//           
//           URI uri = AbstractPaint.class.getResource("/").toURI();
//           str=uri.getPath()+str.replaceAll("[.]", "/");
////           File f=new File(str);
//            File f=new File("file:"+uri.getSchemeSpecificPart());
//           if ( f==null) return null;
//          
//           Queue<File> queue=new LinkedList<File>();  
//           queue.offer(f);
//           while (queue.size()>0){               
//               f=queue.poll();
//               if (f==null) continue;                
//               if (f.listFiles()!=null) {
//                  for(File ft:f.listFiles()) {
//                       queue.offer(ft);
//                       System.out.println("ddddd");
//                       System.out.println(ft.isDirectory());
//                   } 
//               }
//               if (!f.isDirectory()){
//                   String temp=f.getPath();
//                   if (!temp.contains(".class")) continue;    
//                   if (classNameType) {
//                       //按包名分割子路径
//                       String[] p=temp.split(packageName.replace("[.]", "\\\\"));                   
//                       p=p[p.length-1].split(".class");
//                       temp=p[0].substring(1);//去除路径中的第一个"\"符号
//                       //把路径分界符\" 转换成包分界符
//                       temp=temp.replaceAll("\\\\", "."); 
//                       temp=packageName+"."+temp;
//                       rlist.add(temp);  
//                   } else {
//                       String[] p=temp.split("\\\\");
//                       p=p[p.length-1].split(".class");
//                       rlist.add(p[0]);  
//                   }
//               }  
//
//           }
//           
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
//        }
              
        return rlist;
    }
    
    /**
     * 在 Utility.PAINT_PACKAGE 所指定包中，找到所有继承了AbstractPaint的类，
     * 并生成实例返回。
     */
    public static List<AbstractPaint> getExpandPaint() {     
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
           
           return list;
        
    }
    
     /**
     * 
     * @param filename-----日志记录所在文件
     * @return 返回一个能日志处理
     */
    public static FileHandler getMyFileHandler (String filename){
        
        if( filename==null) filename="E:/testlog%g.log";
        FileHandler fileHandler = null;  
        try {
            fileHandler = new FileHandler(filename, true);
        } catch (IOException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
        fileHandler.setLevel(Level.SEVERE);  
        fileHandler.setFormatter(new Formatter(){  
            public String format(LogRecord record){  
                SimpleDateFormat sd = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");    
                String d = sd.format(new Date());    
                return d + record.getLevel() + ":" + record.getMessage() + "/n";  
            }  
        });    
        return fileHandler;
    }
     //<editor-fold defaultstate="collapsed" desc=" Test"> 
    
    public static  void main(String[] args) {
        
       fromHexString_Test();
     
       List<AbstractPaint> lis=getExpandPaint();
    }
  //</editor-fold>
}
