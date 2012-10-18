/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model.config;

import org.fswingui.tools.frame.model.DataBus;

/**
 *
 * @author cloud
 */
public interface FGuiConfig {
    public static String configName="cfg/project/config.json"; 
//    public Map<String,SubjectEntityConfig> toConfig(Map<String,SubjectEntity> map);
//    public Map<String,SubjectEntity> fromConfig(Map<String,SubjectEntityConfig> map);
    public boolean readConfig(String filePathName);
    public boolean writeConfig(String filePathName);
    public boolean readConfig();
    public boolean writeConfig();
    public void setDataBus(DataBus databus);
    
}
