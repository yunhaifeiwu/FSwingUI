/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.extra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableModel;
import org.fswingui.utility.Utility;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.RootPaint;
import org.fswingui.plaf.tools.paint.RootPaint.PaintChangeEvent;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.part.base.PaintPanel;

/**
 *
 * @author cloud
 */
public class PaintPanelExtra extends PaintPanel {    
    PaintPanelExtra me;
    private CurrentData currentData;
    JComboBox idText;
    JComboBox paintStyle;

    public CurrentData getCurrData() {
        return currentData;
    }

    public void setCurrData(CurrentData currentData) {
        this.currentData = currentData;
    }

    public PaintPanelExtra(AbstractPaint paint) {
         
        super(paint);
        me=this;
    }

    public PaintPanelExtra() {
 
        super();
        me=this;
    }
    
    
    
    @Override
    public void init(){  
        
        if (currentData==null)  {
             super.init();
             return;
        }
        paintStyle=new JComboBox(Utility.getExpandPaint().toArray());
  
        idText=new JComboBox(currentData.getPaints().getItems().
                keySet().toArray());
        idText.setEditable(true);
        idText.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractPaint paint=currentData.getPaints().getPaint((String)idText.getSelectedItem());
                if (paint==null) return;
                me.setPaint(paint);
                me.refresh();
            }
            
        });
        super.idText=idText;
        super.paintStyle=paintStyle;
        super.saveBT.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (idText==null || paintStyle==null) return;
                 String txt=idText.getEditor().getItem().toString();
                 AbstractPaint paint=
                         (AbstractPaint) paintStyle.getEditor().getItem();
            
            
                 
                 paint= getPaintFromTable(paint);//由表格中的属生生成paint
                 paint.setPaintID(txt);
                 //将产生一个关联属性事件，paint将克隆后保存
                 currentData.getPaints().putPaint(PaintPanelExtra.class.getName(), 
                         (String)idText.getSelectedItem(), paint);
               
               
                 paint.setPaintID(txt);
                 
                 idText.removeItem(txt);
                 idText.addItem(txt);
                 idText.getModel().setSelectedItem(txt);
                 me.table.getColumnModel().getColumn(1).getCellEditor().stopCellEditing();
            }
            
        });
        
       super.deleteBT.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (idText==null || paintStyle==null) return;
                 String txt=idText.getEditor().getItem().toString();
                 AbstractPaint paint=
                         (AbstractPaint) paintStyle.getEditor().getItem();
                 paint= getPaintFromTable(paint);//由表格中的属生生成paint
                 paint.setPaintID(txt);
                 //将产生一个关联属性事件，paint将克隆后传递给事件接收者，并把原来的删除掉
                 currentData.getPaints().removePaint(PaintPanelExtra.class.getName(), 
                         (String)idText.getSelectedItem(), null);
               
               
                 idText.removeItem(txt);
                 String temp=(String) idText.getModel().getElementAt(0);
                 idText.getModel().setSelectedItem(temp);
            }
            
        });
       
        super.init();
         
        
    }
    /**
     * 把表格中的参数转换成具体的AbstractPaint对象中。由于要确定paint类型，
     * 需要调用者传入具体对象.
     * @param paint  
     *   仅提供一个有默认参数与具体类型的paint,个性化参数由本函数实现完成
     * @return  对指定paint对象填充个性化参数后，然后返回之
     */
    private AbstractPaint getPaintFromTable(AbstractPaint paint){
        if (paint==null) return null ;
         
        TableModel model=table.getModel();
        for (int i=0;i<model.getRowCount();i++ ){
            String name=(String) model.getValueAt(i, 0);
            
            Object v=model.getValueAt(i, 2);
            try {
                paint.addArg(name, v);
            } catch (Exception ex) {
                Logger.getLogger(PaintPanelExtra.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return paint.clone();
    }
    
 
    
    
}
