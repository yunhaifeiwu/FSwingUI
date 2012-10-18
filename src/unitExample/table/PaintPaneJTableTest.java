/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.table;

import org.fswingui.tools.frame.part.base.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import org.fswingui.utility.SpringUtilities;
import org.fswingui.utility.Utility;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.Parameter;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
 


/**
 * 把AbstractPaint实例所需要的参数，显示在表格中。<br>
 * 单独设计原因：为与主题块的操作类交互数据
 * @author cloud
 */
public class PaintPaneJTableTest extends JPanel{
    //<editor-fold defaultstate="collapsed" desc="in class"> 
    class TextCellEditor extends javax.swing.AbstractCellEditor implements 
            TableCellEditor { 
        private JPanel editPanel;
        private JTextField editText;
        private JButton editButton;
        private TextCellEditor me;
        private JTable table;
        private int row,col;
        
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
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    String o=(String) table.getModel().getValueAt(row, 3);        
                    if (o.equals(Color.class.getName()) ){                          
                        String v=editText.getText();
                        Color c=Utility.fromHexString(v);
                        c=JColorChooser.showDialog(editPanel, "", c);
                        if (c!=null)
                            editText.setText(Integer.toHexString(c.getRGB()));
                      
                     
                    } 
                    

                     
                }
            });
            editPanel.setLayout(new BorderLayout());
            editPanel.add(editText,BorderLayout.CENTER);
            editPanel.add(editButton,BorderLayout.EAST);
        }

        @Override
        public Object getCellEditorValue() {
            return editText.getText();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table=table;
            this.row=row;
            this.col=column;
            if (value == null) 
                editText.setText(""); 
            else 
                editText.setText(value.toString()); 
            
             String o=(String) table.getModel().getValueAt(row, 3);        
             if (o.equals(Color.class.getName()) ){     
                editButton.setVisible(true);        
             } else {
                editButton.setVisible(false);    
             }
             

             
            //返回JPanel编辑器
            return editPanel; 
        }
    }    
  
 
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="var"> 
    private PaintPaneJTableTest me;
    protected JTable table=new JTable();
    protected AbstractPaint paint;
    List<Object[]> parameters = new ArrayList<Object[]>();
    private  JLabel paintLable;         
    public JComboBox paintStyle;
    private JLabel idLabel;        
    public JTextField  idText;
    //</editor-fold>
    
            
    public PaintPaneJTableTest(){
        me=this;
        this.add(table);
        init();
        
        
    }
    
    public PaintPaneJTableTest(AbstractPaint paint){
        this.paint=paint;
        me=this;
        this.add(table);
        init();
    }

    
    //<editor-fold defaultstate="collapsed" desc="getter and setter"> 
    public void setPaint(AbstractPaint paint) {
        this.paint = paint;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
    
    public AbstractPaint getPaint() {
        return paint;
    }
    
    //</editor-fold>
    
    private void init(){
        
        parameters.clear();      
        for(Map.Entry e:paint.getParameters().entrySet()){
            List t=new ArrayList();
            t.add(e.getKey());
            
            Parameter p=(Parameter) e.getValue();
            t.add(p.title);
            Object o=p.value==null?p.defaultValue:p.value;
            if (o.getClass().getName().equals(Color.class.getName())
               ){
                 Color c=(Color) o;
                 t.add(Integer.toHexString(c.getRGB()));
             
                
            } else {
                t.add(o);
            }
            
            t.add(p.type.getName());
            
            parameters.add(t.toArray());  
        }
        
        
        Object d[][]=parameters.toArray(  new Object[0][0] );
   
        Object[] heads = {"name","参数","值","类型"};  
        DefaultTableModel model = new  DefaultTableModel  (d,heads);   
        
        table=new JTable();       
        
        table.setModel(model);
//        table.setPreferredScrollableViewportSize(new Dimension(200,300));
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        TableColumnModel tcm=table.getColumnModel(); 
        tcm.removeColumn(tcm.getColumn(3));//隐藏第四列
        tcm.removeColumn(tcm.getColumn(0));//隐藏第一列
        
        table.getColumnModel().getColumn(1).setCellEditor(new TextCellEditor());
        
         JScrollPane jsp=new JScrollPane();
         jsp.setViewportView(table);
           
      

  

         BorderLayout layout=new BorderLayout();
         this.setLayout(layout); 
         this.add(jsp,BorderLayout.CENTER);
         
         paintLable=new JLabel("绘制方式",JLabel.RIGHT);
         paintStyle=new JComboBox(Utility.getExpandPaint().toArray());
         idLabel=new JLabel("ID",JLabel.RIGHT);
         idText=new JTextField();
         paintStyle.setPreferredSize(idText.getPreferredSize());
         
         JPanel headPanel=new JPanel(new SpringLayout());
         headPanel.add(paintLable);
         headPanel.add(paintStyle);
         headPanel.add(Box.createHorizontalStrut(40));
         headPanel.add(idLabel);         
         headPanel.add(idText);        
         headPanel.add(Box.createVerticalBox());

         SpringUtilities.makeCompactGrid(headPanel, 2, 3, 10, 10, 10, 6);
         this.add(headPanel,BorderLayout.NORTH);
        
        
    }
    
   
    
    //<editor-fold defaultstate="collapsed" desc="test"> 
    public static void createPanel(){

        
        JFrame f = new JFrame("Wallpaper");
       
        //用JScrollPane 能正常顺利显示处JTable
         PaintPaneJTableTest scrollPane = new PaintPaneJTableTest(new CrystalPaint()); 
          
         f.add(scrollPane);
//        javax.swing.JColorChooser.showDialog(scrollPane, VIEWPORT, Color.yellow);
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
