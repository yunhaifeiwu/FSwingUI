/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base.pub;

import java.util.Vector;
import org.fswingui.tools.frame.part.extra.SubjectDialog;

/**
 *
 * @author cloud
 */
public class VectorData {
    private String displayName = null;
        private Object value = null;
        private Vector<VectorData> vector;
        public VectorData(){}
        
        public VectorData(String displayName,Object value){
            this.displayName=displayName;
            this.value=value;
        }
        public void put(String displayName,Object value){
            if (vector==null) vector=new Vector();
            VectorData pb=new VectorData(displayName,value);
            vector.addElement(pb);
        }
        public void put(VectorData  vd){
            if (vector==null) vector=new Vector();            
            vector.addElement(vd);
        }
        public void clear(){
            vector.removeAllElements();
        }
        public Vector<VectorData> getVector(){
            return vector;
        }
        public String toString() {
//            return this.displayName.toString();
            return this.displayName.toString();
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public Object getValue() {
            return this.value;
        }
}
