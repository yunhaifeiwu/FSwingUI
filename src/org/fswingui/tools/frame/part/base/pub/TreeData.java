/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base.pub;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cloud
 */
public class TreeData implements Cloneable,Serializable {       
        public String id;
        public String title;
        public Object userData;
        public String type="";
        
        public TreeData(){}
        public TreeData(String id, String title, Object userData) {
            this.id = id;
            this.title = title;
            this.userData = userData;
        }
 
        
        public  TreeData clone(){
            TreeData o=null;            
            try {
                o = (TreeData) super.clone();         
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(TreeData.class.getName()).log(Level.SEVERE, null, ex);
            }
            return o;
        }
         
        @Override
        public String toString() {
            return title;
        }    
    }
