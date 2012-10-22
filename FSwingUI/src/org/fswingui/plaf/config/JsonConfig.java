/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.utilities.Utility;

/**
 *
 * @author Administrator
 */
public class JsonConfig extends AbstractFSUIConfig{

    
    @Override
    public boolean writeConfigImpl(String filePathName) {
                
        boolean bl=false;
        toConfig(super.subjectStore);
        
        LinkedHashMap<String,Object> lmap=new  LinkedHashMap();
        lmap.put(AbstractFSUIConfig.SUBJECT_STORE, subjectEntityConfigs);
        lmap.put(AbstractFSUIConfig.PAINTS, paintConfigs);
        lmap.put(AbstractFSUIConfig.SUBJECT_INDEX,subjectIndex);
        lmap.put(AbstractFSUIConfig.STYLE_MAIN_COLOR,styleMainColor);
        
        String jsonString = JSON.toJSONString( lmap,SerializerFeature.WriteClassName,SerializerFeature.PrettyFormat);  
        OutputStreamWriter osw=null;
        try {                        
            try {
                 osw = new OutputStreamWriter(new FileOutputStream(filePathName),"UTF-8");
//                FileOutputStream o=new java.io.FileOutputStream(new File(filePathName));
//                osw = new OutputStreamWriter(o,"UTF-8");
//                osw = new OutputStreamWriter(new FileOutputStream(filePathName,true),"UTF-8");
            } catch (FileNotFoundException ex) {
                
                Logger.getLogger(JsonConfig.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        } catch (UnsupportedEncodingException ex) {            
            Logger.getLogger(JsonConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        if(osw!=null){
            BufferedWriter bw=new BufferedWriter(osw); 
            try {
                if(jsonString!=null&&!"".equals(jsonString)){
                    bw.write(jsonString);
                }
                bl=true;
                JOptionPane.showMessageDialog(null, "成功导出！");
            } catch (IOException e) {
            }finally{
                try {
                    bw.close();
                    osw.close();   
                    return bl;
                } catch (IOException e) {
                    Logger.getLogger(JsonConfig.class.getName()).log(Level.SEVERE, null, e);
                } 			
            }
        }
        return bl;
    }

    @Override
    protected boolean readConfigImpl(String filePathName) {
        boolean bl=false;
        InputStreamReader isr=null;
        
        try {
            try {
                isr = new InputStreamReader(new FileInputStream(filePathName),"UTF-8");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JsonConfig.class.getName()).log(Level.SEVERE, null, ex);
               
            }
        } catch (UnsupportedEncodingException ex) {
             
            Logger.getLogger(JsonConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
        String jsonString =""; 
        String temp=null;
        if(isr!=null){
            BufferedReader br=new BufferedReader(isr); 
            try {
                while( (temp = br.readLine()) != null)
                {
                    jsonString = jsonString + temp;
                } 
                LinkedHashMap<String,Object> lmap=(LinkedHashMap<String,Object>) JSON.parse(jsonString);
                
                super.paintConfigs=   (Map<String, PaintConfig>) 
                        lmap.get(AbstractFSUIConfig.PAINTS);
                super.subjectEntityConfigs=(Map<String, SubjectEntityConfig>)
                        lmap.get(AbstractFSUIConfig.SUBJECT_STORE);
                
                super.subjectIndex= (ConcurrentHashMap<String, String>) 
                        lmap.get(AbstractFSUIConfig.SUBJECT_INDEX);
                super.styleMainColor= (ConcurrentHashMap<String,Color>) 
                        lmap.get(AbstractFSUIConfig.STYLE_MAIN_COLOR);
                super.fromConfig(subjectEntityConfigs);
                bl=true;
//                JOptionPane.showMessageDialog(null, "成功导入！");
                 
            } catch (IOException e) {
            }finally{
                try {
                    br.close();
                    isr.close();
                } catch (IOException e) {
                } 			
            }
        }
        return bl;
       
         
    }

  
    public static void test1(String ss){
        String jsonString = ss;
        
        OutputStreamWriter osw=null;
        try {                        
            try {
                 osw = new OutputStreamWriter(new FileOutputStream("c:/a.json",true),"UTF-8");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        } catch (UnsupportedEncodingException ex) { 
            Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
        if(osw!=null){
            BufferedWriter bw=new BufferedWriter(osw); 
            try {
                if(jsonString!=null&&!"".equals(jsonString)){
                    bw.write(jsonString);
                }
                JOptionPane.showMessageDialog(null, "成功导出！"+ss);
            } catch (IOException e) {
            }finally{
                try {
                    bw.close();
                    osw.close();   
                } catch (IOException e) {
                    Logger log = Logger.getLogger(JsonConfig.class.getName());  
                    log.setLevel(Level.INFO);  
                    log.addHandler(Utility.getMyFileHandler("F:/testlog%g.log"));  
                    StringWriter sw = new StringWriter();  
                    e.printStackTrace(new PrintWriter(sw));  
                    log.severe(sw.toString());  
                    Logger.getLogger(JsonConfig.class.getName()).log(Level.SEVERE, null, e);
                } 			
            }
        }
    }
        
  
     
}
