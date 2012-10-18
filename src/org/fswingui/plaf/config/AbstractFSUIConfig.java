/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.BaseParameterCoding;
import org.fswingui.plaf.tools.paint.Parameter;
import org.fswingui.tools.frame.part.extra.PropertyPanelExtra;
import org.fswingui.utility.Utility;

/**
 *
 * @author Administrator
 */
public abstract class AbstractFSUIConfig implements FSUIConfig{
    public final static String SUBJECT_STORE="subjectStore";
    public final static String PAINTS="paints";
    public final static String SUBJECT_INDEX="subjectIndex";
    public final static String STYLE_MAIN_COLOR="styleMainColor";
    
    private JFileChooser fileChooser;
     //主题索引。Key为组件名，Value为主题ID
    protected   ConcurrentHashMap<String,String> subjectIndex=new ConcurrentHashMap() ;
    //主题仓库。Key为 风格ID +主题ID，值为实题实体
    protected  ConcurrentHashMap<String,SubjectEntity> subjectStore=new ConcurrentHashMap() ;
    //风格主色 集。Key为 风格ID +主题ID，值为主色
    protected   ConcurrentHashMap<String,Color> styleMainColor=new ConcurrentHashMap() ;
    
    protected  Map <String,SubjectEntityConfig> subjectEntityConfigs=new LinkedHashMap();
    protected  Map<String,AbstractPaint> paints=new LinkedHashMap();
    protected  Map<String,PaintConfig> paintConfigs=new LinkedHashMap();

    /**
     *主题索引。Key为组件名，Value为主题ID 
     */  
    public  ConcurrentHashMap<String, String> getSubjectIndex() {
        return subjectIndex;
    }
    /**
     *主题索引。Key为组件名，Value为主题ID 
     */    
    public  void setSubjectIndex(ConcurrentHashMap<String, String> subjectIndex) {
        this.subjectIndex = subjectIndex;
    }
    
    /**
     *主题仓库。Key为 风格ID +主题ID，值为实题实体
     */  
    public  ConcurrentHashMap<String, SubjectEntity> getSubjectStore() {
        return subjectStore;
    }
    /**
     *主题仓库。Key为 风格ID +主题ID，值为实题实体
     */  
    public  void setSubjectStore(ConcurrentHashMap<String, SubjectEntity> subjectStore) {
        this.subjectStore = subjectStore;
    }

    //风格主色 集。Key为 风格ID ，值为主色
    public  ConcurrentHashMap<String, Color> getStyleMainColor() {
        return styleMainColor;
    }

    //风格主色 集。Key为 风格ID ，值为主色
    public  void setStyleMainColor(ConcurrentHashMap<String, Color> styleMainColor) {
        this.styleMainColor = styleMainColor;
    }

    
    
    public Map<String, SubjectEntityConfig> getSubjectEntityConfigs() {
        return subjectEntityConfigs;
    }

    public void setSubjectEntityConfigs(Map<String, SubjectEntityConfig> subjectEntityConfigs) {
        this.subjectEntityConfigs = subjectEntityConfigs;
    }

    public Map<String, AbstractPaint> getPaints() {
        return paints;
    }

    public void setPaints(Map<String, AbstractPaint> paints) {
        this.paints = paints;
    }

    public Map<String, PaintConfig> getPaintConfigs() {
        return paintConfigs;
    }

    public void setPaintConfigs(Map<String, PaintConfig> paintConfigs) {
        this.paintConfigs = paintConfigs;
    }

     
    @Override
   public boolean readConfig(){
       return readConfig(FSUIConfig.configName);
   }
    @Override
    public boolean writeConfig(){
        return writeConfig(FSUIConfig.configName);
    }
    
    @Override
    public boolean readConfig(String filePathName){
        if (filePathName==null ||  "".equals(filePathName)) return false;
        File f=new File(filePathName);
        if(!f.exists()) {
            if (fileChooser==null) fileChooser=new JFileChooser();
            FileNameExtensionFilter  ff=
                new FileNameExtensionFilter ("json file",
                "json","js" 
            );
            fileChooser.addChoosableFileFilter(ff); 
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                f=fileChooser.getSelectedFile();   
                filePathName=f.getName();
            }
        }
        return readConfigImpl(filePathName);
    }
    @Override
    public boolean writeConfig(String filePathName){
        if (filePathName==null ||  "".equals(filePathName)) return false;
        File f=new File(filePathName);
        if(f.exists()) f.delete();
        return writeConfigImpl(filePathName);
    }
   
    protected abstract boolean readConfigImpl(String filePathName);

    protected abstract boolean writeConfigImpl(String filePathName);
    
    
    
    public SubjectEntityConfig SubjectEntityToConfig(String key,SubjectEntity se){
        SubjectEntityConfig subeCfg=new SubjectEntityConfig();
        subeCfg.setSubjectID(key);
        subeCfg.setArrangement(se.getArrangement());
        if(se.getBackgroundIcon()==null){
            subeCfg.setBackgroundIcon("");
        } else {
            subeCfg.setBackgroundIcon(se.getBackgroundIcon().getDescription());
        }        
        subeCfg.setBackgroundTransparence(se.getBackgroundTransparence());
        subeCfg.setDisableIcon(se.isDisableIcon());
        subeCfg.setDisableText(se.isDisableText());  
        if(se.getFont()==null){
            subeCfg.setFont("");
        } else {
            subeCfg.setFont(se.getFont().toString());
        }
        if(se.getFontColor()==null){
            subeCfg.setFontColor("");
        } else {
            subeCfg.setFontColor(Integer.toHexString(se.getFontColor().getRGB()));
        }
        
        if(se.getIcon()==null){
            subeCfg.setIcon("");
        } else {
            subeCfg.setIcon(se.getIcon().getDescription());
        }
        subeCfg.setIconTransparence(se.getIconTransparence());
        subeCfg.setOpaque(se.isOpaque());
        if(se.getPaint()==null){
            subeCfg.setPaint("");
        } else {
            AbstractPaint paint=se.getPaint();
            subeCfg.setPaint(paint.getPaintID());
            paintConfigs.put(paint.getPaintID(),paintToConfig(paint));
            
        }
        
        subeCfg.setText(se.getText());
        subeCfg.setTextTransparence(se.getTextTransparence());
        
        if (!se.isEmpty()){
            LinkedHashMap<String,SubjectEntityConfig> rl=new LinkedHashMap();
            subeCfg.setRelateSubjectEntity(rl);
            for(Map.Entry<String,SubjectEntity> ent:se.entrySet()){
                SubjectEntityConfig sec1=SubjectEntityToConfig(ent.getValue().getSubjectID(),ent.getValue());
                rl.put(ent.getKey(), sec1);
            }
        }
       
        return subeCfg;
    }
     
    public PaintConfig paintToConfig(AbstractPaint paint){
        PaintConfig paintCfg=new PaintConfig();            

        paintCfg.setPaintID(paint.getPaintID());
        paintCfg.setType(paint.getClass().getName());
        paintCfg.setDescribe(paint.getDescribe());
        if (paintCfg.getArgs()==null) paintCfg.setArgs(new LinkedHashMap());
        for (Map.Entry<String,Parameter> en1:paint.getArgs().entrySet()){
            Parameter p=en1.getValue();
            ParameterConfig pc=new ParameterConfig();
            pc.setType(p.type.getName());
            pc.setBaseParameterCodingType(p.coding.getClass().getName());
            pc.setValue(p.coding.valueToString(p.value));
            pc.setDefaultValue(p.coding.valueToString(p.defaultValue));
            paintCfg.getArgs().put(en1.getKey(), pc);
        }
        return paintCfg;
    } 
 
    public   Map<String, SubjectEntityConfig> toConfig(Map<String, SubjectEntity> map){
       
        for(Map.Entry<String,SubjectEntity> ent:map.entrySet()){
            SubjectEntityConfig subeCfg=SubjectEntityToConfig(ent.getKey(),ent.getValue());
            subjectEntityConfigs.put(ent.getKey(), subeCfg);
              
        }          
        return subjectEntityConfigs;
        
    }
   
    public AbstractPaint configToPaint(PaintConfig paintCfg) {
        if (paintCfg==null) return null;
        AbstractPaint paint=null;
        if(paintCfg.getType()!=null && ! paintCfg.getType().equals("")){
            Class cls;
            try {
                cls = Class.forName(paintCfg.getType());
                try {
                    paint=(AbstractPaint) cls.newInstance();
                } catch (InstantiationException ex) {
                    Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return null;
        }
        paint.setPaintID(paintCfg.getPaintID());
        paint.setDescribe(paint.getDescribe());
        if(paintCfg.getArgs()!=null || !paintCfg.getArgs().isEmpty()){
            LinkedHashMap<String, Parameter> args =new LinkedHashMap();
            paint.setArgs(args);
            for(Map.Entry<String,ParameterConfig> en:
                    paintCfg.getArgs().entrySet())
            {
                Parameter par=new Parameter();
                ParameterConfig parCfg1=en.getValue();
                BaseParameterCoding bcode=null;
                if(parCfg1.getBaseParameterCodingType()==null|| 
                        parCfg1.getBaseParameterCodingType().equals(""))
                {
                    bcode=new BaseParameterCoding(par);
                } else {
                    Class cls=null;
                    try {
                        cls = Class.forName(parCfg1.getBaseParameterCodingType());
                        try {
                            bcode=(BaseParameterCoding) cls.newInstance();
                            bcode.setParameter(par);
                        } catch (InstantiationException ex) {
                            Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                        
                    
                }
                try {
                    par.type=Class.forName(  parCfg1.getType()) ;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                }
                par.coding=bcode;
                
                par.value=bcode.valueFromString(parCfg1.getValue());
                par.defaultValue=bcode.valueFromString(parCfg1.getDefaultValue());
                if(parCfg1.getAllowNull()==null || parCfg1.getAllowNull().equals("")){
                     par.allowNull=false;
                } else {
                    par.allowNull=Boolean.valueOf(parCfg1.getAllowNull());
                }
               args.put(en.getKey(), par);
                
            }
        }
        return paint;
        
    }
    
    public SubjectEntity   configToSubjectEntity   (SubjectEntityConfig subeCfg){
        if (subeCfg==null) return null;
        SubjectEntity se=new SubjectEntity();
        se.setArrangement(subeCfg.getArrangement());
        if(subeCfg.getBackgroundIcon()!=null && !subeCfg.getBackgroundIcon().equals("")){
            ImageIcon icon= new ImageIcon(subeCfg.getBackgroundIcon());   
            se.setBackgroundIcon(icon);
        }else {
            se.setBackgroundIcon(null);
        } 
        se.setBackgroundTransparence(subeCfg.getBackgroundTransparence());
        se.setDisableIcon(subeCfg.isDisableIcon());
        se.setDisableText(subeCfg.isDisableText());
        if(subeCfg.getFont()!=null && !subeCfg.getFont().equals("")){
            Font font=Font.decode(subeCfg.getFont());
            se.setFont(font);
        }else {
            se.setFont(null);
        } 
        if(subeCfg.getFontColor()!=null && !subeCfg.getFontColor().equals("")){
            Color c=Utility.fromHexString(subeCfg.getFontColor());
            se.setFontColor(c);
        }else {
            se.setFontColor(null);
        } 
        if(subeCfg.getIcon()!=null && !subeCfg.getIcon().equals("")){
            ImageIcon icon= new ImageIcon(subeCfg.getIcon());   
            se.setIcon(icon);
        }else {
            se.setIcon(null);
        } 
        se.setIconTransparence(subeCfg.getIconTransparence());
        se.setOpaque(subeCfg.isOpaque());
        
         
        se.setPaint(paints.get(subeCfg.getPaint()));//==================================================
        
        se.setSubjectID(subeCfg.getSubjectID());
        se.setText(subeCfg.getText());
        se.setTextTransparence(subeCfg.getTextTransparence());
        
        if(subeCfg.getRelateSubjectEntity()!=null){
            for(Map.Entry<String,SubjectEntityConfig> en:
                    subeCfg.getRelateSubjectEntity().entrySet())
            {
                 SubjectEntity se1=configToSubjectEntity(en.getValue());
                 se.put(en.getKey(), se1);
            }
        }
       
        
        
         
        return se;
        
    }
    
    public  ConcurrentHashMap<String, SubjectEntity> fromConfig(Map<String,
            SubjectEntityConfig> map)
    {
        if(map==null || map.isEmpty()) return null;
        
        if(paintConfigs!=null ){
            for(Map.Entry<String,PaintConfig> en:
                   paintConfigs.entrySet() )
            {
                PaintConfig paintCfg=en.getValue();
                AbstractPaint paint=configToPaint(paintCfg);
                paints.put(en.getKey(), paint);
                
            }
            
        }
        
        
         
        for(Map.Entry<String,SubjectEntityConfig> en:map.entrySet()){
            SubjectEntityConfig  subeCfg=en.getValue();
            SubjectEntity se=configToSubjectEntity(subeCfg);
            if (se==null) se=new SubjectEntity();
            subjectStore.put(en.getKey(), se);            
            
        }
        return subjectStore;
        
    }
    
}
