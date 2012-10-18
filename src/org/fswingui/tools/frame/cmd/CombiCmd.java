/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.fswingui.tools.frame.MainFrame;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.gui.component.ComDivPanel;
import org.fswingui.tools.gui.component.extra.ComDiv;
import org.fswingui.tools.gui.component.extra.Div;


public class CombiCmd extends Command{
    class Data{
        public int mLeft=Integer.MAX_VALUE,mRight=0,mTop=Integer.MAX_VALUE,
                mHeight=0,zOrder=Integer.MAX_VALUE;
        List<Div> list;
        
    }
    private Data data;
    public CombiCmd(DataBus dataBus, String id) {
        super(dataBus, id);
    }
    
    /*
     * 计算最左、最上、最下、最右值
     */
    private void getM(Map<String, BaseData> BaseDatas){
       
        data=new Data();
        data.list=new ArrayList();
        
        for (Entry<String,BaseData> en:BaseDatas.entrySet()){
            BaseData bd=en.getValue();
            
            Div d=CurrentData.getDiv(bd.getId());
            if (d.getX()<data.mLeft){
                data.mLeft=d.getX();                
            }
            if (d.getY()<data.mTop){
                data.mTop=d.getY();                
            }
            if (d.getX()+d.getWidth()>data.mRight){
                data.mRight=d.getX()+d.getWidth();               
            }
            if (d.getY()+d.getHeight()>data.mHeight){
                data.mHeight=d.getY()+d.getHeight();               
            }
            if(d.getZOrder()<data.zOrder){
                data.zOrder=d.getZOrder();
            }
            data.list.add(d);
        }
        Collections.sort(data.list, new  Comparator<Div>(){          
            @Override
            public int compare(Div o2, Div o1) {
               return Integer.compare(o2.getZOrder(), o1.getZOrder());
            }
        });
        
    }
    @Override
    public void excute() {
        if (id==null || dataBus==null || dataBus.getCurrentData()==null ||
                CurrentData.getSelectBaseDatas().size()<=0  ) {
            return;
        }
       
        CurrentData cd=this.getDataBus().getCurrentData();
         
        String rootname=Double.toString(Math.random());    
        Map<String, BaseData> selects =CurrentData.getSelectBaseDatas();
        JPanel centerPanel =(JPanel) dataBus.getGuiParts().get(MainFrame.CENTER_PANEL);
        getM(selects);
        
        ComDiv comdiv=new ComDiv(super.dataBus.getCurrentData(),centerPanel,rootname);
    
        
        comdiv.getView().setBounds(data.mLeft, data.mTop, data.mRight-data.mLeft, data.mHeight-data.mTop);
        comdiv.getView().setBorder(BorderFactory.createLineBorder(Color.red));
        comdiv.init();
        
        //为组合根节点
        BaseData root=this.dataBus.getBaseData(rootname);
        root.setParent(null);
        
        
         //得组合孩子节点存储空间
        LinkedHashMap<String,BaseData> children=new LinkedHashMap();    
        root.setChildren(children);
      
        root.setLeaf(false);
        comdiv.getView().setLayout(null);
        
        int i=data.list.size()-1;
       
        String name;
        int c=0;
        for (Div d:data.list){
            name=d.getName();
            BaseData dbd=cd.getBaseData(name);
            if ( ! ComDiv.class.isAssignableFrom(dbd.getDiv().getClass()) ){
                dbd.setLeaf(true);
            }
            
            children.put(name, dbd);
            CurrentData.putRootBaseData(name, root,cd);        
            dbd.getDiv().removeFocusAndDropMouseAdapter();
            dbd.getDiv().removeScaleMouseAdapte();
            dbd.setContained(true);
            dbd.setParent(root);
            dbd.setZorder(c,false);
            c++;
                  
            d.setBounds(d.getX()-data.mLeft, d.getY()-data.mTop,
                    d.getWidth(), d.getHeight()
            );
            d.setOldBorder(false);   
            dbd.getDiv().addUpDispatchMouseEvent();
//            comdiv.getView().add(d.getView());  
            
            
            
        }
        root.paintViewZorder();       
        selects.clear();
        cd.getCurrBaseData().setParent(null);
        this.data=null;
//        cd.saveCurrBaseData();
        
        centerPanel.add(comdiv.getView());
        centerPanel.revalidate();
    }
    
}
