/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.config;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.fswingui.plaf.SubjectEntity;

/**
 *
 * @author Administrator
 */
public interface FSUIConfig {
    public static String configName="subjectStock.json";
//    public Map<String,SubjectEntityConfig> toConfig(Map<String,SubjectEntity> map);
//    public Map<String,SubjectEntity> fromConfig(Map<String,SubjectEntityConfig> map);
    public boolean readConfig(String filePathName);
    public boolean writeConfig(String filePathName);
    public boolean readConfig();
    public boolean writeConfig();
    
    public Map <String,SubjectEntityConfig> getSubjectEntityConfigs();    
    public Map <String,PaintConfig> getPaintConfigs();
    public ConcurrentHashMap<String, SubjectEntity> getSubjectStore();
    public ConcurrentHashMap<String, String> getSubjectIndex() ;
    public ConcurrentHashMap<String, Color> getStyleMainColor();
}
