
package unitExample.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import org.fswingui.utility.Utility;

public class JTextAndJComboCellEditorDemoJTable2 extends javax.swing.AbstractCellEditor implements 
        TableCellEditor { 
    private JPanel editPanel;
    private JTextField editText; 
    private JComboBox comboBox;
    private JButton editButton;
    private JTextAndJComboCellEditorDemoJTable2 me;
    private JComponent currEdior;
    
 
 
    public JTextAndJComboCellEditorDemoJTable2(){       
         
        editPanel = new javax.swing.JPanel();
        editButton = new javax.swing.JButton();
        editText = new javax.swing.JTextField();
        comboBox=new JComboBox(Utility.getExpandPaint().toArray());        
        me=this;
        editButton.setText("*");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                 String dd=JOptionPane.showInputDialog(null, me.getCellEditorValue() );   
                 editText.setText(dd);
  
            }
        });
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
            return cb.getModel().getSelectedItem();
        }  
        return editText.getText();
    } 
    
    //重点：这个方法返回JPanel编辑器（包括JPanel上的其他控件）
    @Override
    public java.awt.Component getTableCellEditorComponent( 
            JTable table, Object value, boolean isSelected, 
            int row, int column) { 
        if (row==1){
            editPanel.removeAll();
            editPanel.add(comboBox,BorderLayout.CENTER);
            currEdior=comboBox;
            comboBox.setPreferredSize(editText.getPreferredSize());
        } else {
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
    
     public static  void createPanel() {
        JFrame f = new JFrame("Wallpaper");
        f.getContentPane().setLayout(new FlowLayout());
        Object[] heads = {"d","s","dd"};  
        
         
        DefaultTableModel model = new  DefaultTableModel  (heads,5);         
        JTable t=new JTable();         
        t.setModel(model);
        
        //重点是这里设置 自定义的单元格
        JTextAndJComboCellEditorDemoJTable2  tedt=new JTextAndJComboCellEditorDemoJTable2 ();
        t.getColumnModel().getColumn(2).setCellEditor(tedt);
       
        //用JScrollPane 能正常顺利显示处JTable
        final JScrollPane scrollPane = new JScrollPane(); 
        scrollPane.setViewportView(t);
        f.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        f.setSize(450, 300);
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
}