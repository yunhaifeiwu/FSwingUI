/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.fswingui.plaf.config.AbstractFSUIConfig;
import org.fswingui.plaf.config.JsonConfig;
import org.fswingui.plaf.config.PaintConfig;
import org.fswingui.plaf.config.SubjectEntityConfig;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.frame.model.MapPropertys;

/**
 *
 * @author cloud
 */
public class JSonGuiConfig extends AbstractFGuiConfig {

    public JSonGuiConfig(DataBus databus) {
        super(databus);
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
                CurrentDataCofig cfg= (CurrentDataCofig) JSON.parse(jsonString);
                cofingToCurrentData(cfg);
                
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

    @Override
    protected boolean writeConfigImpl(String filePathName) {
        boolean bl=false;
        CurrentDataCofig cdCofig=currentDataToConfig(
            super.dataBus.getCurrentData()
        );

        String jsonString = JSON.toJSONString( cdCofig,
            SerializerFeature.WriteClassName,SerializerFeature.PrettyFormat
        );  
        
        OutputStreamWriter osw=null;
        try {                        
            try {
                osw = new OutputStreamWriter(new FileOutputStream(filePathName,true),"UTF-8");
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
                } 			
            }
        }
        return bl;
    }

    
    
}
