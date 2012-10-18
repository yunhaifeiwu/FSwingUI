/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd.file;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.fswingui.tools.frame.cmd.Command;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.frame.model.config.FGuiConfig;
import org.fswingui.tools.frame.model.config.JSonGuiConfig;

/**
 *
 * @author cloud
 */
public class OpenProject extends Command  {
    private transient FGuiConfig config;
    private JFileChooser fileChooser;
    public OpenProject(DataBus dataBus, String id) {
        super(dataBus, id);
        if(dataBus.getCurrentData().getConfig()==null){
            config=new JSonGuiConfig(super.dataBus);
            dataBus.getCurrentData().setConfig(config);
            
        }  else {
            config=dataBus.getCurrentData().getConfig();
        }
//        jcfg=(JSonGuiConfig) config;
        
        
    }
    @Override
    public void excute() { 

        if (fileChooser==null) fileChooser=new JFileChooser();
        FileNameExtensionFilter  ff=
            new FileNameExtensionFilter ("json file",
            "json","js" 
        );
        
        fileChooser.addChoosableFileFilter(ff); 
        String filePathName=FGuiConfig.configName;
        File f=new File(filePathName);
        fileChooser.setCurrentDirectory(f);
        int returnVal = fileChooser.showOpenDialog(null);
       
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            f=fileChooser.getSelectedFile();   
            filePathName=f.getPath();
        }
         
        config.readConfig(filePathName);
    }
}

