/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.lang;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cloud
 */
public class TreeClone implements Cloneable {
    public TreeClone parent;
    public String id;
    public TreeClone(String id){
        this.id=id;
    }
    @Override
    public  TreeClone clone()  {
         TreeClone o=null;
        try {
            o=(TreeClone) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(TreeClone.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }
  
    public static void main(String [] args){ 
        TreeClone a1=new TreeClone("1");
        TreeClone a2=new TreeClone("1");
        a1.parent=a2;
        a2.parent=a2.clone();
        System.out.println(a1.id);
    } 
}
