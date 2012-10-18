/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd;

import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.gui.component.extra.ComDiv;
import org.fswingui.tools.gui.component.extra.Div;

/**
 *
 * @author cloud
 */
public class UnCombiCmd extends Command {

    public UnCombiCmd(DataBus dataBus, String id) {
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
        Div div;
        JComponent root=null;
        for(Map.Entry<String,BaseData> en:selects.entrySet()){
            bd0=en.getValue();
            div=bd0.getDiv();
            div.setOldBorder(false);
            root=(JComponent) bd0.getDivView().getParent();
            if (div==null ) continue;
            if ( !ComDiv.class.isAssignableFrom(div.getClass())) {
                continue;
            }
           
            Stack<BaseData> sk=new Stack();
            LinkedHashMap<String,Point> tempMap=new LinkedHashMap();
            Point point=new Point(bd0.getDiv().getX(),bd0.getDiv().getY());
            tempMap.put(bd0.getId(), point);                
            sk.push(bd0);
            BaseData bd;            
            while (!sk.isEmpty()){//取消全部组合，即组合中有组合都将全部取消
                bd=sk.pop();
                if ( !ComDiv.class.isAssignableFrom(bd.getDiv().getClass())){
                    continue;
                }
                if ( bd.getChildren()==null || bd.getChildren().isEmpty()){
                    continue;
                }
                    
                Iterator <Map.Entry<String,BaseData>> it=
                        bd.getChildren().entrySet().iterator();
                
                BaseData bd1;
               
                while(it.hasNext()){//分解孩子节点
                    Map.Entry<String,BaseData> en1=it.next(); 
                    bd1=en1.getValue();
                    Div pdiv=bd1.getParentDiv();
                    int x=tempMap.get(pdiv.getName()).x +bd1.getDiv().getX();
                    int y=tempMap.get(pdiv.getName()).y +bd1.getDiv().getY();
                    
                    if(bd1.getChildren()==null ||  bd1.getChildren().isEmpty())
                    {
                        bd1.setLeaf(true);
                    }
                    bd1.setContained(false);
                    
                    bd1.getDiv().setOldBorder(false);                    
                    bd1.getDiv().removeUpDispatchMouseEvent();   
                      
                    if(ComDiv.class.isAssignableFrom(bd1.getDiv().getClass()))
                    {//组合节点处理
                        point=new Point(tempMap.get(pdiv.getName()).x+bd1.getDiv().getX(),
                                tempMap.get(pdiv.getName()).y+bd1.getDiv().getY());
                        tempMap.put(bd1.getId(), point);
                        bd1.getParentDivView().remove(bd1.getDivView());
                        sk.push(bd1) ;
                    } else{//叶子节点处理
                        bd1.getParentDivView().remove(bd1.getDivView());
                        bd1.getDiv().addFocusAndDropMouseAdapter();
                        bd1.getDiv().addScaleMouseAdapte();
                       
                        bd1.getDivView().setBounds(x,y,
                                bd1.getDiv().getWidth(),  bd1.getDiv().getHeight());
                        root.add(bd1.getDivView());
                    }
                       
                    it.remove();
                    cd.removeBaseData(bd.getId());
                }
                root.remove(bd.getDivView()); 
                
                
            }
        }
        
        root.repaint();
        root.revalidate();
        selects.clear();
    }
    
}
  
