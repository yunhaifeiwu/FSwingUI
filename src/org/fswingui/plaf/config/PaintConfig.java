/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.config;

import java.util.LinkedHashMap;
import org.fswingui.plaf.tools.paint.AbstractPaint;

/**
 *
 * @author Administrator
 */
public class PaintConfig {
    private String type;
    private String paintID;
    private String describe;     
    private LinkedHashMap<String,ParameterConfig> args;
    
    
    /**
     * 画笔类型，该画笔必须是继承自AbstractPaint
     */
    public String getType() {
        
        return type;
    }

    /**
     * 画笔类型，该画笔必须是继承自AbstractPaint
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getPaintID() {
        return paintID;
    }

    public void setPaintID(String paintID) {
        this.paintID = paintID;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
 

    
    public LinkedHashMap<String, ParameterConfig> getArgs() {
        return args;
    }

    public void setArgs(LinkedHashMap<String, ParameterConfig> args) {
        this.args = args;
    }
    
    
}
