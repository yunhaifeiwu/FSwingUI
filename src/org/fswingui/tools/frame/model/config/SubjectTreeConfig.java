/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author cloud
 */
public class SubjectTreeConfig {
    private String id;
    private String title;
    /**
     * K----实体类别，V---SubjectEntity的ID
     */
    private LinkedHashMap<String,NodeCofig> subjectEnity=new LinkedHashMap();
    private List<NodeCofig> components=new ArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedHashMap<String, NodeCofig> getSubjectEnity() {
        return subjectEnity;
    }

    public void setSubjectEnity(LinkedHashMap<String, NodeCofig> subjectEnity) {
        this.subjectEnity = subjectEnity;
    }

    public List<NodeCofig> getComponents() {
        return components;
    }

    public void setComponents(List<NodeCofig> components) {
        this.components = components;
    }

     

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
}
