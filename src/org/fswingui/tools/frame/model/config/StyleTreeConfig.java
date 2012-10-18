/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model.config;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.fswingui.plaf.Subject;

/**
 *
 * @author cloud
 */
public class StyleTreeConfig {
     
    private String id;
    private String title;
    private String mainColor;
    /**
     * KEY----主题索引号，Value----SubjectTreeConfig的ID
     */
    private LinkedHashMap<String,SubjectTreeConfig> subjects=new LinkedHashMap();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainColor() {
        return mainColor;
    }

    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }

    
    public LinkedHashMap<String, SubjectTreeConfig> getSubjects() {
        return subjects;
    }

    public void setSubjects(LinkedHashMap<String, SubjectTreeConfig> subjects) {
        this.subjects = subjects;
    }

    
    
    
    
}
