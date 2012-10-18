/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import javax.swing.JFrame;
import javax.swing.JTree;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.ui.FSwingLF;
import org.fswingui.tools.frame.MainFrame;
import org.fswingui.tools.frame.cmd.Command;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.frame.model.config.FGuiConfig;
import org.fswingui.tools.frame.model.config.JSonGuiConfig;

/**
 *
 * @author cloud
 */
public class SaveProject extends Command  {
    private transient FGuiConfig config;
    public SaveProject(DataBus dataBus, String id) {
        super(dataBus, id);
        if(dataBus.getCurrentData().getConfig()==null){
            config=new JSonGuiConfig(super.dataBus);
            dataBus.getCurrentData().setConfig(config);
            
        }  else {
            config=dataBus.getCurrentData().getConfig();
        }
        
        
    }
    @Override
    public void excute() {
//        String jsonString = JSON.toJSONString( super.dataBus.getAllData(),SerializerFeature.WriteClassName,SerializerFeature.PrettyFormat);  
//        String s=JSON.toJSONString(super.getDataBus().getCurrentBaseData().getMapPropertys(),
//             SerializerFeature.WriteClassName,SerializerFeature.PrettyFormat
//        );
        
        config.writeConfig();
        System.out.println();
    }
}
