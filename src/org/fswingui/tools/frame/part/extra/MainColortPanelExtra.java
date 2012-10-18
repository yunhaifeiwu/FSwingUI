/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.extra;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.fswingui.plaf.Style;
import org.fswingui.plaf.UIEngine;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.part.base.MainColortPanel;
import org.fswingui.utility.Utility;

/**
 *
 * @author cloud
 */
public class MainColortPanelExtra extends MainColortPanel 
    implements PropertyChangeListener
{
   
        
    private CurrentData currentData;
    private JColorChooser jcolor;
    public CurrentData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(CurrentData currentData) {        
        this.currentData = currentData;
        
    }
    
    @Override
    public JPanel init(){
        super.init();
        super.styleBox.setEditable(true); 
        this.currentData.addMainColorSylteChangesListener(this);
          
        for(Component cm:styleBox.getComponents()){
            if( JButton.class.isAssignableFrom(cm.getClass())){
                JButton b=(JButton) cm;
                b.addActionListener(new ActionListener(){                  

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String str=(String) styleBox.getSelectedItem();
                        styleBox.removeAllItems();
                        styleBox.addItem("");
                        if(CurrentData.getStyles()!=null){
                            for(Map.Entry<String,Style> en:CurrentData.getStyles().entrySet()){
                                styleBox.addItem(en.getKey());                
                            }
                        }
                        styleBox.setSelectedItem(str);
                    }
                });
                break;
            }
        }
        
        colorText.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) { 
                String str= colorText.getText();
                if (str==null || str.equals("")){
                    UIManager.put(UIEngine.CURRENT_COLOR, null);   
                    return;
                }
                Color c1=Utility.fromHexString(str);
                if (c1==null) {
                    JOptionPane.showMessageDialog(colorText, "颜色值输入无效！");
                    colorText.setText("");
                } else{
                    currentData.setCurrentMainColor(c1);
                }
                

            }
            
        });
        styleBox.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                 String str=(String) styleBox.getSelectedItem();
                 currentData.setCurrentStyle(str);      
            }
        
        });
        colorButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c=Utility.fromHexString(colorText.getText());
                c=c==null?Color.BLUE:c;                
                   
                if(jcolor==null) jcolor=new JColorChooser();                
                Color c1=jcolor.showDialog(MainColortPanelExtra.this,"",c); 
                if(c1==null) {
                    return;
                } 
                colorText.setText(Integer.toHexString(c1.getRGB()));
                currentData.setCurrentMainColor(c1);       
            }        
        });
 
        return this;
        
    } 

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if( CurrentData.class.isAssignableFrom(evt.getSource().getClass()) &&
                evt.getPropertyName().equals(CurrentData.CURRENT_MANIN_COLOR)
        ){
            Color c=(Color)evt.getNewValue();
            colorText.setText(Integer.toHexString(c.getRGB()));
        } else if( CurrentData.class.isAssignableFrom(evt.getSource().getClass()) &&
                evt.getPropertyName().equals(CurrentData.CURRENT_MANIN_STYLE)
        ){
            String s=(String)evt.getNewValue();
            if(s!=null && !s.equals("")) {
                styleBox.removeItem(s);
                styleBox.addItem(s);
                styleBox.setSelectedItem(s);
            }
            
        }
    }

  
     
}
