/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd.sort;

import java.awt.Component;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JComponent;
import org.fswingui.tools.frame.cmd.Command;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.gui.component.adapter.DivSta;
import org.fswingui.tools.gui.component.extra.Div;
import org.fswingui.utility.MapSort;

/**
 *
 * @author cloud
 */
public class UpSort extends Command {

    public UpSort(DataBus dataBus, String id) {
        super(dataBus, id);
    }

    @Override
    public void excute() {
        if (id==null || dataBus==null || dataBus.getCurrentData()==null ||
                CurrentData.getSelectBaseDatas().size()<=0  ) {
            return;
        }
       
        CurrentData cd=this.getDataBus().getCurrentData();
        
        Map<String, BaseData> selects =CurrentData.getSelectBaseDatas();
        if (selects==null || selects.isEmpty()) return;
        
        BaseData bd0;
        BaseData rootbd;
        Div div;
        JComponent root=null;
        
        for(Map.Entry<String,BaseData> en:selects.entrySet()){
            rootbd=en.getValue().getParent();
            bd0=en.getValue();
            div=bd0.getDiv();
            root=(JComponent) bd0.getDivView().getParent();
            break;
        }
        MapSort<Integer,BaseData> zcld=new MapSort();
        int j=0;
        for(Component c:root.getComponents() ){ 
            if (!DivSta.class.isAssignableFrom(c.getClass())) continue;
            int z=root.getComponentZOrder(c);
            if(selects.containsKey(c.getName())){
                bd0=selects.get(c.getName());
                bd0.getDiv().setOldBorder(false);
                if (z<=0) {
                    bd0.setZorder(0, false);
                    zcld.put(new Integer(0), bd0);
                    continue;
                }
                bd0.setZorder(z-1, false);
                
                
                j=bd0.getZorder();
                BaseData bd;
                if (zcld.containsKey(new Integer(j))){
                    bd=zcld.get(j);
                    zcld.remove(j);
                    bd0.setZorder(j, false);
                    zcld.put(new Integer(j), bd0);
                    bd.setZorder(j+1, false);
                    zcld.put(new Integer(j+1), bd);
                } else {                    
                    zcld.put(new Integer(j), bd0);
                }
               
                
            } else {
                bd0= CurrentData.getDiv(c.getName()).getCurrentData().getBaseData(c.getName());
               
                j=bd0.getZorder();
                while (zcld.containsKey(new Integer(j))){
                    j++;
                }
                bd0.setZorder(j, false);
                zcld.put(new Integer(j), bd0);
            }
            
        }
        zcld.sort(true);
        root.removeAll();
        
        for(Iterator it=zcld.keySet().iterator();it.hasNext(); ){
            Integer key=(Integer) it.next(); 
            Component c=zcld.get(key).getDivView();
            root.add(c);
        }
        root.repaint();
        root.revalidate();
        selects.clear();
    }
    
}
