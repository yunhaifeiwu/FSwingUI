
package unitExample.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class PanelCellEditorJTableDemo extends javax.swing.AbstractCellEditor implements 
        TableCellEditor { 
    private JPanel editPanel;
    private JTextField editText;
    private JButton editButton;
    private PanelCellEditorJTableDemo me;
  
 
    public PanelCellEditorJTableDemo(){       
         
        editPanel = new javax.swing.JPanel();
        editButton = new javax.swing.JButton();
        editText = new javax.swing.JTextField();
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
        return editText.getText();
    } 
    
    //重点：这个方法返回JPanel编辑器（包括JPanel上的其他控件）
    @Override
    public java.awt.Component getTableCellEditorComponent( 
            JTable table, Object value, boolean isSelected, 
            int row, int column) { 
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
        PanelCellEditorJTableDemo  tedt=new PanelCellEditorJTableDemo ();
        t.getColumnModel().getColumn(2).setCellEditor(tedt);
       
        //用JScrollPane 能正常顺利显示处JTable
        final JScrollPane scrollPane = new JScrollPane(); 
        scrollPane.setViewportView(t);
        f.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
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
}