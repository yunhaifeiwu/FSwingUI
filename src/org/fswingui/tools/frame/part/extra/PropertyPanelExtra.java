/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.extra;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
 
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.RootPaint;
import org.fswingui.plaf.tools.paint.RootPaint.PaintChangeEvent;
import org.fswingui.pubcomponent.JFontChooser;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.model.MapPropertys;
import org.fswingui.tools.frame.model.MapPropertys.MapPropertyChangeEvent;
import org.fswingui.tools.frame.model.MapPropertys.PropertyUnit;
import org.fswingui.tools.frame.model.PropertyCriterion;
import org.fswingui.tools.frame.part.base.PropertyPanel;
import org.fswingui.tools.frame.part.base.pub.VectorData;
import org.fswingui.tools.gui.component.extra.AbstractDiv;

/**
 *
 * @author cloud
 */
public class PropertyPanelExtra extends PropertyPanel 
    implements PropertyChangeListener 
{ 
    
    private BaseData baseData;
    private  JTable tb;
    private PropertyPanelExtra me;
    private MapPropertys mpPropertys;
    private JFileChooser fc;
    private JFontChooser jfont;
    private JColorChooser jcolor;
    private  SubjectDialog dlg;
    public PropertyPanelExtra() {
        super (); 
        me=this;
    }

    public PropertyPanelExtra(MapPropertys propertys) {
        super(propertys);         
    }

    public BaseData getBaseData() {
        return baseData;
    }

    public void setBaseData(BaseData baseData) {
        this.baseData = baseData;
    }
    
       @Override
    public void init(){        
        super.setPropertys(baseData.getMapPropertys());        
        baseData.getMapPropertys().addMapChangesListener(this);
        baseData.getCurrentData().addDivBindChangesListener(this);
        mpPropertys=this.baseData.getMapPropertys();
        RootPaint rootp=CurrentData.getPaints();
        rootp.addMapChangesListener(this);
        Map <String,SubjectEntity >  subjectEntitys=CurrentData.getSubjectEntitys();
        
        super.init(); 
       
      
        PaintComboBox pb=new PaintComboBox();
         pb.put("",null);
        for(Map.Entry<String,AbstractPaint > en:rootp.getItems().entrySet()){
            pb.put(en.getKey(),en.getValue().clone());
        }
        super.cellEditor.comboBox=new JComboBox (pb.getVector() );        
        super.cellEditor.comboBox.addActionListener(new ActionListener(){         
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb=(JComboBox) e.getSource(); 
                PaintComboBox pb=(PaintComboBox) cb.getSelectedItem();
                if (pb==null) return;
                me.cellEditor.editText.setText(pb.getDisplayName());
                int row=mpPropertys.getProperty(PropertyCriterion.PAINT).index;
                if(row!=me.cellEditor.row) return;
                me.model.setValueAt(pb.getDisplayName(),me.cellEditor.row, 2);
                me.model.setValueAt(pb.getValue(),me.cellEditor.row, 4);
                me.cellEditor.stopCellEditing();
                changeProperty();                        
            }
            
        });
        
          
        String[] dd={"true","false"};
        if (super.cellEditor.comBool==null ) {
            super.cellEditor.comBool=new JComboBox(dd);
        }
        super.cellEditor.comBool.addActionListener(new ActionListener(){         
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb=(JComboBox) e.getSource(); 
                String s= (String) cb.getSelectedItem();
                if (s==null) return;
                me.cellEditor.editText.setText(s);
                me.model.setValueAt(s,me.cellEditor.row, 2);
                if (s.equals("true")) {
                    me.model.setValueAt(true,me.cellEditor.row, 4);
                } else {
                    me.model.setValueAt(false,me.cellEditor.row, 4);
                }
                
                me.cellEditor.stopCellEditing();
                changeProperty();                        
            }
            
        });
        super.cellEditor.editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row =me.cellEditor.row;
                    Class o=(Class) table.getModel().getValueAt(row, 3);        
                    if (o.equals(Icon.class) ){   
                        if (fc==null) fc=new JFileChooser();
                        FileNameExtensionFilter  ff=
                            new FileNameExtensionFilter ("picture file",
                            "jpg","jpeg","png","gif"
                        );
//                        fc.addChoosableFileFilter(ff);
                        fc.setFileFilter(ff);
                        int returnVal = fc.showOpenDialog(PropertyPanelExtra.this);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File f=fc.getSelectedFile();                                 
                            ImageIcon icon= new ImageIcon(f.getPath());                            
                            table.getModel().setValueAt(icon, row, 4);
                            me.cellEditor.editText.setText(icon.toString()); 
                            me.cellEditor.stopCellEditing();
                            changeProperty();  
                        }
                        
                    } else if(o.equals(Font.class)) {
                        if(jfont==null) jfont=new JFontChooser();
                        jfont.setVisible(true);
                        Font f=jfont.getFont();
                        if (f!=null){
                            table.getModel().setValueAt(f, row, 4);
                            table.getModel().setValueAt(f.toString(), row, 2);
                            me.cellEditor.editText.setText(f.toString());   
                            me.cellEditor.stopCellEditing();
                            changeProperty();  
                        }
                      
                    } else if(o.equals(Color.class)) {
                        if(jcolor==null) jcolor=new JColorChooser();
                        
                        Color c=jcolor.showDialog(me.cellEditor.editText, "", Color.BLUE);
                        if (c!=null){
                            table.getModel().setValueAt(c, row, 4);
                            table.getModel().setValueAt(Integer.toHexString(c.getRGB()), row, 2);
                            me.cellEditor.editText.setText(Integer.toHexString(c.getRGB()));   
                            me.cellEditor.stopCellEditing();
                            changeProperty();  
                        }
                      
                    } else if(o.equals(SubjectEntity.class) && 
                            CurrentData.getSubjectEntitys()!=null) 
                    
                    {
                         if(dlg==null){
                             dlg=new SubjectDialog();
                             dlg.vectorDatas.removeAllElements();
                             for(Map.Entry<String,SubjectEntity> en:
                                     CurrentData.getSubjectEntitys().entrySet())
                             {
                                 VectorData vd=new VectorData(en.getKey(),en.getValue());
                                 dlg.vectorDatas.addElement(vd);
                             }
                             dlg.init();
                             dlg.setModal(true);
                             dlg.setVisible(true);
                         } else {
                             dlg.vectorDatas.removeAllElements();
                             for(Map.Entry<String,SubjectEntity> en:
                                     CurrentData.getSubjectEntitys().entrySet())
                             {
                                 VectorData vd=new VectorData(en.getKey(),en.getValue());
                                 dlg.vectorDatas.addElement(vd);
                             }
                             dlg.setModal(true);
                             dlg.setVisible(true);
                         }
                         if (dlg.selectVectorData!=null){
                             subjectChange((SubjectEntity)dlg.selectVectorData.getValue());
                         }
                         dlg.selectVectorData=null;
                      
                    } 
                    

                     
                }
            });
        
        super.cellEditor.editText.addActionListener(new ActionListener(){         
            @Override
            public void actionPerformed(ActionEvent e) {
                int row =me.cellEditor.row;
                JTextField jt=(JTextField) e.getSource();
                me.cellEditor.editText.setText( jt.getText());
                Class o=(Class) table.getModel().getValueAt(row, 3);        
                if (o.equals(String.class) ){   
                    table.getModel().setValueAt(jt.getText(), row, 4);
                    
                } else if(o.equals(Float.class) ){   
                    table.getModel().setValueAt(Float.valueOf(jt.getText()), row, 4);
                    
                } else if(o.equals(Integer.class) ){   
                    table.getModel().setValueAt(Integer.valueOf(jt.getText()), row, 4);
                }
                
                int nameIndex=me.mpPropertys.getProperty(PropertyCriterion.NAME).index;                
                if(row!=nameIndex )
                   me.cellEditor.stopCellEditing();
                changeProperty();                        
            }
            
        });
        
        
    }
   
   private void subjectChange(SubjectEntity se){
        if(se==null) return;
        
        
        int row=mpPropertys.getProperty(PropertyCriterion.SUBJECT_ID).index;
        cellEditor.row=row; 
        super.cellEditor.editText.setText(se.getSubjectID());
        me.model.setValueAt(se.getSubjectID(),me.cellEditor.row, 2);
        me.model.setValueAt(se,me.cellEditor.row, 4);
        changeProperty();
        
        row=mpPropertys.getProperty(PropertyCriterion.ARRANGEMENT).index;
        cellEditor.row=row; 
        super.cellEditor.editText.setText(String.valueOf(se.getArrangement()));
        me.model.setValueAt(String.valueOf(se.getArrangement()),me.cellEditor.row, 2);
        me.model.setValueAt(se.getArrangement(),me.cellEditor.row, 4);
        changeProperty();
        
        row=mpPropertys.getProperty(PropertyCriterion.OPAQUE).index;
        cellEditor.row=row; 
        super.cellEditor.editText.setText(String.valueOf(se.isOpaque()));
        me.model.setValueAt(String.valueOf(se.isOpaque()),me.cellEditor.row, 2);
        me.model.setValueAt(se.isOpaque(),me.cellEditor.row, 4);
        changeProperty();
        
        row=mpPropertys.getProperty(PropertyCriterion.DISABLE_TEXT).index;
        cellEditor.row=row; 
        super.cellEditor.editText.setText(String.valueOf(se.isDisableText()));
        me.model.setValueAt(String.valueOf(se.isDisableText()),me.cellEditor.row, 2);
        me.model.setValueAt(se.isDisableText(),me.cellEditor.row, 4);
        changeProperty();
        
        row=mpPropertys.getProperty(PropertyCriterion.TEXT).index;
        cellEditor.row=row; 
        super.cellEditor.editText.setText(String.valueOf(se.getText()));
        me.model.setValueAt(se.getText(),me.cellEditor.row, 2);
        me.model.setValueAt(se.getText(),me.cellEditor.row, 4);
        changeProperty();
        
        if(se.getFont()!=null){
            row=mpPropertys.getProperty(PropertyCriterion.FONT).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText(se.getFont().toString());
            me.model.setValueAt(se.getFont().toString(),me.cellEditor.row, 2);
            me.model.setValueAt(se.getFont(),me.cellEditor.row, 4);
            changeProperty();
        } else {
            row=mpPropertys.getProperty(PropertyCriterion.FONT).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText("");
            me.model.setValueAt("",me.cellEditor.row, 2);
            me.model.setValueAt(se.getFont(),me.cellEditor.row, 4);
            changeProperty();
        }
        
        if(se.getFontColor()!=null){
            row=mpPropertys.getProperty(PropertyCriterion.FONT_COLOR).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText(Integer.toHexString(se.getFontColor().getRGB()));
            me.model.setValueAt(Integer.toHexString(se.getFontColor().getRGB()),me.cellEditor.row, 2);
            me.model.setValueAt(se.getFontColor(),me.cellEditor.row, 4);
            changeProperty();
        }else {
            row=mpPropertys.getProperty(PropertyCriterion.FONT_COLOR).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText("");
            me.model.setValueAt("",me.cellEditor.row, 2);
            me.model.setValueAt(se.getFontColor(),me.cellEditor.row, 4);
            changeProperty();
        }
        
        row=mpPropertys.getProperty(PropertyCriterion.TEXT_TRANSPARENCE).index;
        cellEditor.row=row; 
        super.cellEditor.editText.setText(String.valueOf(se.getTextTransparence()));
        me.model.setValueAt(String.valueOf(se.getTextTransparence()),me.cellEditor.row, 2);
        me.model.setValueAt(se.getTextTransparence(),me.cellEditor.row, 4);
        changeProperty();
        
      
        row=mpPropertys.getProperty(PropertyCriterion.DISABLE_ICON).index;
        cellEditor.row=row; 
        super.cellEditor.editText.setText(String.valueOf(se.isDisableIcon()));
        me.model.setValueAt(String.valueOf(se.isDisableIcon()),me.cellEditor.row, 2);
        me.model.setValueAt(se.isDisableIcon(),me.cellEditor.row, 4);
        changeProperty();
       
        if(se.getIcon()!=null){
            row=mpPropertys.getProperty(PropertyCriterion.ICON).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText(se.getIcon().toString());
            me.model.setValueAt(se.getIcon().toString(),me.cellEditor.row, 2);
            me.model.setValueAt(se.getIcon(),me.cellEditor.row, 4);
            changeProperty();
        }else{
            row=mpPropertys.getProperty(PropertyCriterion.ICON).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText("");
            me.model.setValueAt("",me.cellEditor.row, 2);
            me.model.setValueAt(se.getIcon(),me.cellEditor.row, 4);
            changeProperty();
        }
        
        row=mpPropertys.getProperty(PropertyCriterion.ICON_TRANSPARENCE).index;
        cellEditor.row=row; 
        super.cellEditor.editText.setText(String.valueOf(se.getIconTransparence()));
        me.model.setValueAt(String.valueOf(se.getIconTransparence()),me.cellEditor.row, 2);
        me.model.setValueAt(se.getIconTransparence(),me.cellEditor.row, 4);
        changeProperty();
        
        if(se.getBackgroundIcon()!=null){
            row=mpPropertys.getProperty(PropertyCriterion.BACKIMG).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText(se.getBackgroundIcon().toString());
            me.model.setValueAt(se.getBackgroundIcon().toString(),me.cellEditor.row, 2);
            me.model.setValueAt(se.getBackgroundIcon(),me.cellEditor.row, 4);
            changeProperty();
        }else {
            row=mpPropertys.getProperty(PropertyCriterion.BACKIMG).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText("");
            me.model.setValueAt("",me.cellEditor.row, 2);
            me.model.setValueAt(se.getBackgroundIcon(),me.cellEditor.row, 4);
            changeProperty();
        }
        
        row=mpPropertys.getProperty(PropertyCriterion.BACKGROUD_TRANSPARENCE).index;
        cellEditor.row=row; 
        super.cellEditor.editText.setText(String.valueOf(se.getBackgroundTransparence()));
        me.model.setValueAt(String.valueOf(se.getBackgroundTransparence()),me.cellEditor.row, 2);
        me.model.setValueAt(se.getBackgroundTransparence(),me.cellEditor.row, 4);
        changeProperty();
       
        if(se.getPaint()!=null){
            row=mpPropertys.getProperty(PropertyCriterion.PAINT).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText(se.getPaint().getPaintID());
            me.model.setValueAt(se.getPaint().getPaintID(),me.cellEditor.row, 2);
            me.model.setValueAt(se.getPaint(),me.cellEditor.row, 4);
            changeProperty();
        }else {
            row=mpPropertys.getProperty(PropertyCriterion.PAINT).index;
            cellEditor.row=row; 
            super.cellEditor.editText.setText("");
            me.model.setValueAt("",me.cellEditor.row, 2);
            me.model.setValueAt(se.getPaint(),me.cellEditor.row, 4);
            changeProperty();
        }
        
        
        
        
        
        super.cellEditor.editText.setText(se.getSubjectID());
        me.cellEditor.stopCellEditing();
   }
   /**
     *  属性发生改变时，保存当前属到到currData中去。并通过MapPropertys通知物体<rb/>
     * 按该属性发生变化。
     */
   public void changeProperty (){     
        
        MapPropertys mp=this.baseData.getMapPropertys();
        PropertyUnit p=null;  
        PropertyUnit op=null;  
        String txt=super.cellEditor.editText.getText();
        Object v=this.model.getValueAt(cellEditor.row,4);
        String id =(String) this.model.getValueAt(0,2);//DIV的实际ID，当ID被更改，指的是更改前ID
               
        for(Map.Entry<String,PropertyUnit> en:mp.getPropertysClone().entrySet()){
            if(cellEditor.row==en.getValue().index){
                op=en.getValue();
                if (op==null) return;
                p=op.clone();
                p.value=v;
                p.strValue=txt;  
//                if(p.type.equals(Integer.class)) {
//                    p.value=v;
//                    p.strValue=txt;  
//                } else if(p.type.equals(String.class)){
//                    p.value=v;
//                    p.strValue=txt;  
//                } else if(p.type.equals(Float.class)){
//                    p.value=v;
//                    p.strValue=txt;  
//                }else if(p.type.equals(Boolean.class)){
//                    p.value=v;
//                    p.strValue=txt;  
//                }else if(p.type.equals(AbstractPaint.class)){
//                    p.value=v;
//                    p.strValue=txt;  
//                }else   {
//                    p.value=v;
//                    p.strValue=txt;  
//                }
                mp.setProperty(MapPropertys.TYPE_EDIT,id, p);
            }
        }


    }
    
 
    
    /**
     * 响应属性变化，把变化的属性展现出来。
     */
    private void propertyShow(MapPropertyChangeEvent evt){
        MapPropertyChangeEvent e=(MapPropertyChangeEvent) evt;
        if (e.sourceType.equals(MapPropertys.TYPE_EDIT)) return;
        if (e.sourceType.equals(MapPropertys.TYPE_INIT)) return;
        PropertyUnit p=(PropertyUnit) evt.getNewValue(); 
        super.cellEditor.comboBox.setSelectedItem(null);
        super.cellEditor.stopCellEditing();
        if(p.value==null){
            model.setValueAt("", p.index, 2);
            model.setValueAt(null, p.index, 4);
        } else {
            model.setValueAt(p.strValue, p.index, 2);
            model.setValueAt(p.value.getClass(), p.index, 3);
            model.setValueAt(p.value, p.index, 4);
        }
        
    
    }
   
    /**
     * 响应绘制函数属性发生变；变化后，更改属性展示下拉列表内容，与currData中数据。
     */
    private void paintChange(PaintChangeEvent e){
                // 是RootPaint类型且发布且是PaintChangeEvent事件，才处理
        if ( e.getSource().getClass().getName().equals(
                RootPaint.class.getName())
                )
        { 
            AbstractPaint oldp=(AbstractPaint) e.getOldValue();
            AbstractPaint newp=(AbstractPaint) e.getNewValue();
            RootPaint rootp=CurrentData.getPaints();
            MapPropertys mp=this.baseData.getMapPropertys();
            PropertyUnit currp=mp.getProperty(PropertyCriterion.PAINT);
            
            DefaultComboBoxModel cmbModel= (DefaultComboBoxModel) 
                    super.cellEditor.comboBox.getModel();
            
           int row= mpPropertys.getProperty(PropertyCriterion.PAINT).index;
            PaintComboBox pb=(PaintComboBox) cmbModel.getSelectedItem();
            String s=(String) super.model.getValueAt(row, 2);
             
            
            
            cmbModel.removeAllElements();
            PaintComboBox pb1;
            
            pb1=new PaintComboBox("",null); 
            cmbModel.addElement(pb1);
           
            super.cellEditor.editText.setText(s);
            for(Map.Entry<String,AbstractPaint> en:rootp.getItems().entrySet()){
                pb1=new PaintComboBox (en.getValue().getPaintID(),en.getValue().clone());  
                if(s.equals(pb1.getDisplayName())){
                    pb=pb1;                    
                }
                cmbModel.addElement(pb1);                
            }
                
            if(pb==null){
                this.model.setValueAt(null, row, 4);        
            } else {
                this.model.setValueAt(pb.getValue(), row, 4);        
            }
            
            cmbModel.setSelectedItem(pb);
         
           
        }
      
    }
            
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getClass().getName().equals(
                MapPropertyChangeEvent.class.getName()))
        { // 响应属性变化，把变化的属性展现出来。  
            MapPropertyChangeEvent e=(MapPropertyChangeEvent) evt;
            propertyShow(e);
        } else if (evt.getClass().getName().equals(
                PaintChangeEvent.class.getName()))
        {//响应绘制函数变化，更新绘制函数列表。
           PaintChangeEvent e=(PaintChangeEvent) evt;
           paintChange(e);
        } else if (evt.getSource().equals(
                AbstractDiv.class.getSimpleName())&&
                evt.getPropertyName().equals(CurrentData.DIVBIND_EVENT_NAME)
                )
        {//切换DIV时，按DIV中设置好的当前属性，重新展现在MainFrame的属性UI界面上
           super.setPropertys(baseData.getMapPropertys());
           super.refresh();
        }
        
         
    }

  
    
}
