/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.table;

 
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;



class MyComboBoxRenderer extends JComboBox implements 
        TableCellRenderer
{
    public MyComboBoxRenderer(String[] items){   
        super(items);//使用父构造函数，给JComboBox.items 变量设初值。         
    }   
    
    @Override
    public Component getTableCellRendererComponent(JTable table,
         Object value,boolean isSelected,
         boolean hasFocus,int row,int column)
    {
        
            Color alternate = UIManager.getColor("Table.alternateRowColor");
            if (row % 2 == 1) {
                this.setBackground(Color.red);
            } else {
                this.setBackground(Color.WHITE);
            }
            //注意仅当value中的值，存在于JComboBox.itesms时，才能显示，否则
            //显示的是items的初值。即表格中的值 要在items中存在。
            this.setSelectedItem(value);
     
        return this;
    }   
}

/**
 * 要编辑时起作用，DefaultCellEditor 也应该是JComboBox
 */
class MyComboBoxEditor extends DefaultCellEditor{
    public MyComboBoxEditor(String[] items){
        super(new JComboBox(items));
    }
}

//测试
public class ComboBoxCellRendererJTableDemo{
    
    public static void main(String[] args){
        JTable table = new JTable();
        DefaultTableModel model = (DefaultTableModel)table.getModel();

        model.addColumn("A",new Object[]{"i1","i2","i3","i4"});
        model.addColumn("B",new Object[]{"b1","b2","b3","b4"});

        String[] values = {"i1","i2","i3"};
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setCellEditor(new MyComboBoxEditor(values));
        col.setCellRenderer(new MyComboBoxRenderer(values));
         
     
        JFrame frame = new JFrame();
        frame.setContentPane(new JScrollPane(table));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

