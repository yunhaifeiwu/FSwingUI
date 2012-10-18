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
 *排序，移动最后
 */
public class LastSort extends Command {

    public LastSort(DataBus dataBus, String id) {
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
        
        BaseData bd0=null;
        BaseData rootbd;
        Div div;
        JComponent root=null;
        
        int i=0;
        int sc=0;
        MapSort<Integer,BaseData> zcld=new MapSort();
        for(Map.Entry<String,BaseData> en:selects.entrySet()){
            i++;
            bd0=en.getValue();
            bd0.getDiv().setOldBorder(false);      
            if(i==selects.size()){
                rootbd=en.getValue().getParent();
                div=bd0.getDiv();
                bd0.setZorder(0, false);
                root=(JComponent) bd0.getDivView().getParent();
                sc=root.getComponentZOrder(bd0.getDivView());
                
                 
            }
        }
        
        
        int k=0;
        for(Component c:root.getComponents() ){ 
            if (!DivSta.class.isAssignableFrom(c.getClass())) continue;
            k++;
            int z=root.getComponentZOrder(c);
            if(c.getName().equals(bd0.getId())) continue;
            
            BaseData bd2= CurrentData.getDiv(c.getName()).getCurrentData().getBaseData(c.getName());
            
            if(z<=sc)  {
                bd2.setZorder(z, false);
                zcld.put(new Integer(z), bd2);
                continue;
            }
            bd2.setZorder(z-1, false);
            
            int j=bd2.getZorder();
            while (zcld.containsKey(new Integer(j))){
                j--;
            }
            bd2.setZorder(j, false);
            zcld.put(new Integer(j), bd2);
            
        }
        zcld.put(new Integer(k), bd0);
        
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
