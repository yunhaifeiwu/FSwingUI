/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.component;

import org.fswingui.tools.frame.part.base.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import org.fswingui.tools.frame.model.MapPropertys;
import org.fswingui.tools.frame.model.MapPropertys.PropertyUnit;
import org.fswingui.tools.frame.model.PropertyCriterion;
import org.fswingui.utility.Utility;
import unitExample.filedialog.FileChooserDemo;

/**
 *
 * @author cloud
 */
public class PropertyPanel extends JPanel{
    //<editor-fold defaultstate="collapsed" desc="in class">   
    public class PaintComboBox {
        private String displayName = null;
        private Object value = null;
        private Vector<PaintComboBox> vector;
        public PaintComboBox(){}
        
        public PaintComboBox(String displayName,Object value){
            this.displayName=displayName;
            this.value=value;
        }
        public void put(String displayName,Object value){
            if (vector==null) vector=new Vector();
            PaintComboBox pb=new PaintComboBox(displayName,value);
            vector.addElement(pb);
        }
        public void put(PaintComboBox pb){
            if (vector==null) vector=new Vector();            
            vector.addElement(pb);
        }
        public void clear(){
            vector.removeAllElements();
        }
        public Vector<PaintComboBox> getVector(){
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
    protected class TextCellEditor extends  AbstractCellEditor implements 
            TableCellEditor { 
        public JPanel editPanel;
        public JTextField editText;
        public JButton editButton;
        public JComboBox comboBox;
        public JComboBox comBool;
        public TextCellEditor me;
        protected JTable table;
        public int row,col;
        public  JComponent currEdior;
     
        public TextCellEditor(){      
            editPanel = new javax.swing.JPanel();
            editButton = new javax.swing.JButton();
            editText = new javax.swing.JTextField();            
            me=this;             
            editButton.setText("*");     
            
    
           
            Dimension d=new Dimension(15,editButton.getHeight());
            editButton.setSize(d);
            editButton.setPreferredSize(d);
            editButton.setMaximumSize(d);
            
            
            
            
            editPanel.setLayout(new BorderLayout());
            editPanel.add(editText,BorderLayout.CENTER);
            editPanel.add(editButton,BorderLayout.EAST);
        }

        @Override
        public Object getCellEditorValue() {
            if (currEdior==null) return "";
            if (JComboBox.class.isAssignableFrom(
                currEdior.getClass())) {
                JComboBox cb=(JComboBox) currEdior;
                if( cb.getSelectedItem()!=null &&
                        PaintComboBox.class.isAssignableFrom(
                        cb.getSelectedItem().getClass())
                ){
                    PaintComboBox pb=(PaintComboBox) cb.getSelectedItem();
                    return pb.getDisplayName();
                }
                return cb.getModel().getSelectedItem();
            }  
            
            return editText.getText();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table=table;
            this.row=row;
            this.col=column;
            if (  AbstractPaint.class.equals(
                    table.getModel().getValueAt(row, 3))
            ){            
                editPanel.removeAll();
                PaintComboBox pb=new PaintComboBox("aa","bb");
                pb.put(pb);
             
                if (comboBox==null ) comboBox=new JComboBox(pb.getVector());
                editPanel.add(comboBox,BorderLayout.CENTER);
                currEdior=comboBox;
                comboBox.setPreferredSize(editText.getPreferredSize());
            } else if ( Boolean.class.equals (
                    table.getModel().getValueAt(row, 3)))
            {            
                editPanel.removeAll();                
                String[] dd={"true","false"};
                if (comBool==null ) comBool=new JComboBox(dd);
                editPanel.add(comBool,BorderLayout.CENTER);
                currEdior=comBool;
                comBool.setPreferredSize(editText.getPreferredSize());
            } else{
                editPanel.removeAll();
                editPanel.add(editText,BorderLayout.CENTER);
                editPanel.add(editButton,BorderLayout.EAST);
                currEdior=editText;
            }
            if (value == null) 
                editText.setText(""); 
            else 
                editText.setText(value.toString()); 
        
            //返回JPanel编辑器
            return editPanel; 
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" var"> 

    /**
     * 存储paint所用参数；String -----参数名,Parameter----参数值；
     * 实现者（该类的子类) 必须把要使用的参数通过addParameter函数填入args中，
     *   然后在paintImp方法中实用这些参数；<br>
     * 调用时，必须先通过addArg把实参填入到args中，方可调用paint方法。
     */
    protected MapPropertys propertys;
    private PropertyPanel me;
    protected TextCellEditor cellEditor;
    protected JTable table;
    protected DefaultTableModel model;
    
  
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="constructor"> 
    public PropertyPanel(){
        me=this;   
    }
    
    public PropertyPanel(MapPropertys propertys){
        me=this;
        this.propertys=propertys;
    }


    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter"> 
    public MapPropertys getPropertys() {
        return propertys;
    }

    public void setPropertys(MapPropertys propertys) {
        this.propertys = propertys;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" function"> 
    public void init(){
        me=this;
        
        
        if (propertys==null){
            propertys =new MapPropertys();
            ImageIcon icon=new ImageIcon ();
            Object[][] p={
                {PropertyCriterion.NAME,"名称",String.class,"",""},
                {PropertyCriterion.SUBJECT_ID,"主题",String.class,"",""},
                {PropertyCriterion.WIDTH,"宽",Integer.class,"0",0},
                {PropertyCriterion.HEIGHT,"高",Integer.class,"0",0},
                {PropertyCriterion.X,"横坐标",Integer.class,"0",0},
                {PropertyCriterion.Y,"纵坐标",Integer.class,"0",0},
                {PropertyCriterion.OPAQUE,"透明",Boolean.class,"false",false},  
                {PropertyCriterion.ARRANGEMENT,"图文位置",Integer.class,"0",0},            
                {PropertyCriterion.DISABLE_TEXT,"禁止文本",Boolean.class,"true",true},
                {PropertyCriterion.TEXT,"文本",String.class,"",""},
                {PropertyCriterion.FONT,"字体",Font.class,"",null},
                {PropertyCriterion.FONT_COLOR,"字体颜色",Color.class,"",null},
                {PropertyCriterion.TEXT_TRANSPARENCE,"文本透明度",Float.class,"1",1f},
                {PropertyCriterion.DISABLE_ICON,"禁止图标",Boolean.class,"true",true},
                {PropertyCriterion.ICON,"图标",Icon.class,"",null},
                {PropertyCriterion.ICON_TRANSPARENCE,"图标透明度",Float.class,"1",1f},
                {PropertyCriterion.BACKIMG,"背景图",Icon.class,"",null},                
                {PropertyCriterion.BACKGROUD_TRANSPARENCE,"背景透明度",Float.class,"1",1f},
                {PropertyCriterion.PAINT,"背景绘制",AbstractPaint.class,"",null}
            };
            propertys.initPropertys(p);
           
            
        }
        Map<String,PropertyUnit>  map=propertys.getPropertysClone();        
        List<Object[]> parameters=new ArrayList();        
        for(Map.Entry<String,PropertyUnit> e:map.entrySet()){
            List t=new ArrayList();
            PropertyUnit p=  e.getValue();
            t.add(p.name);
            t.add(p.title);
            Object o=p.value;
            if (o!=null && o.getClass().getName().equals(Color.class.getName())
               ){
                 Color c=(Color) o;
                 t.add(Integer.toHexString(c.getRGB()));
            } else {
                t.add(o);
            }
            t.add(p.type.getName());
            t.add(p.value);
            parameters.add(t.toArray());  
        }
        
        
        Object d[][]=parameters.toArray(  new Object[0][0] );
   
        Object[] heads =  {"name","参数","值","类型","实值"}; 
        model = new  DefaultTableModel  (d,heads);   
       
        table=new JTable();       
       
        
        table.setModel(model);
//        table.setPreferredScrollableViewportSize(new Dimension(200,300));
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        TableColumnModel tcm=table.getColumnModel(); 
        tcm.removeColumn(tcm.getColumn(4));//隐藏第五列
        tcm.removeColumn(tcm.getColumn(3));//隐藏第四列        
        tcm.removeColumn(tcm.getColumn(0));//隐藏第一列
        this.cellEditor =new TextCellEditor();
        table.getColumnModel().getColumn(1).setCellEditor(this.cellEditor);
        
         JScrollPane jsp=new JScrollPane();
         jsp.setViewportView(table); 
         
         BorderLayout layout=new BorderLayout();
         this.setLayout(layout); 
         this.add(jsp,BorderLayout.CENTER);       
         
        
    }
    
  
    //</editor-fold>
    
    public void refresh(){
        if (propertys==null) return;
        model.getDataVector().clear();
        for(Map.Entry<String,PropertyUnit> en:propertys.getPropertysClone().entrySet()){
            Object[] heads = {"name","参数","值","类型","实值"};  
            PropertyUnit pu=en.getValue();
            Object []rowValues = {pu.name,pu.title,pu.strValue,pu.type,pu.value};           
            model.addRow(rowValues);
        }   
        table.repaint();
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="test"> 
    public static void createPanel(){

        
        JFrame f = new JFrame("Wallpaper");
       
        PropertyPanel scrollPane = new PropertyPanel(); 
        scrollPane.init();
        f.add(scrollPane);
        f.setSize(400, 300);
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo (null);
        
        f.setVisible (true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {      
                createPanel();
            }
        });
    } 
    //</editor-fold>
    
}
