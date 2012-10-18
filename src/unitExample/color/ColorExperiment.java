/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.color;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import org.fswingui.pubcomponent.JFontChooser;
import org.fswingui.utility.SpringUtilities;
import unitExample.dialog.DialogDemo1;

/**
 *
 * @author Administrator
 */
public class ColorExperiment {
     public static JColorChooser jcolor;
     public static int i;
      public static int x;
     public static List<JLabel> lbs = new ArrayList();
     public static List<JTextField> texts= new ArrayList();
     public static List<JButton> bts= new ArrayList();
     public static JTextField tx=new JTextField();
     public static  JFrame f = new JFrame("Wallpaper");
    //<editor-fold  desc="test"> 
    public static void createPanel(){

        
       
        JPanel root= (JPanel) f.getContentPane();
        SpringLayout layout=new SpringLayout();
        root.setLayout(layout);
        
        JPanel panel=new JPanel(new SpringLayout());
        panel.add(new JLabel("颜色深浅变化步数"));     
         
        panel.add(tx);
        Component c1=Box.createHorizontalBox();
        panel.add(c1);
        SpringUtilities.makeCompactGrid(panel,
                                        1,3, //rows, cols
                                        30,30,        //initX, initY
                                        15, 15);       //xPad, yPad
        
        root.add(panel);
        
        JPanel p1=new JPanel (new SpringLayout());
        for( i=0 ;i<=10;i++){
            lbs.add(new JLabel("颜色"+String.valueOf(i)));
            p1.add(lbs.get(i));
            
            texts.add(new JTextField());
            p1.add(texts.get(i));
            bts.add(new JButton("选色"));
            bts.get(i).setName(String.valueOf(i));
            bts.get(i).addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                     JButton b=(JButton) e.getSource();
                     i=Integer.valueOf(b.getName());
                     if(jcolor==null) jcolor=new JColorChooser();
                        
                        Color c=jcolor.showDialog((JComponent)e.getSource(), "", texts.get(0).getBackground());
                        if (c!=null){                            
                            texts.get(i).setText(Integer.toHexString(c.getRGB()));
                             
                        }
                        texts.get(i).setBackground(c);
                        f.revalidate();
                        f.repaint();
                }
            
            });
            
            p1.add(bts.get(i));
        }
        SpringUtilities.makeCompactGrid(p1,
                                        11,3, //rows, cols
                                        0, 0,        //initX, initY
                                        15, 15);       //xPad, yPad
        
        root.add(p1);
        
      
       
        
        JButton bt4=new JButton("更改成指定主色系");
        lbs.get(0).setText("原来主色");    
        lbs.get(1).setText("指定主色");    
        bt4.addActionListener(new ActionListener()
        {//此处算法，为更改成指定主色系
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tx.getText()!=null && !tx.getText().equals("") ){
                   x=Integer.valueOf(tx.getText());
                } else {
                    x=10;
                }
               
                Color c=texts.get(0).getBackground();
                float[] hsb={0,0,0};
                Color.RGBtoHSB(
                    c.getRed(), c.getGreen(),c.getBlue(), hsb
                );               
                                 
                
                c=texts.get(1).getBackground();
                float[] hsb1={0,0,0};
                Color.RGBtoHSB(
                    c.getRed(), c.getGreen(),c.getBlue(), hsb1
                );
                float dh=hsb1[0]-hsb[0];
                float ds=hsb1[1]-hsb[1];
                float db=hsb1[2]-hsb[2];
                
                for (int i=3;i<=10;i=i+2){
                    Color c2=texts.get(i-1).getBackground();
                    float[] hsb2={0,0,0};
                    Color.RGBtoHSB(
                        c2.getRed(), c2.getGreen(),c2.getBlue(), hsb2
                    );  
                    
                    
                    float[] hsb3={hsb2[0],0,0};
                    
                     
                    //原主色系中的色按主色差值，推出新的色. java中hsb的h是纯小数。
                    // 乘以360就得 HSB 颜色模式中的色彩角度
                    hsb3[0]=hsb2[0]+dh;
                    int fInt = (int) hsb3[0];//取小数部分--begin
                    BigDecimal b1 = new BigDecimal(Float.toString(hsb3[0]));
                    BigDecimal b2 = new BigDecimal(Integer.toString(fInt));
                    float fPoint = b1.subtract(b2).floatValue();//取小数部份--end
                    hsb3[0]=fPoint;
                    if(hsb3[0]<0){
                        hsb3[0]=1+hsb3[0];
                    }  
                   
                    
                    
                    hsb3[1]=hsb1[1]-
                            hsb1[1]*(hsb[1]-hsb2[1])
                            /hsb[1];
                    hsb3[2]=hsb1[2]-
                            hsb1[2]*(hsb[2]-hsb2[2])
                            /hsb[2];
                    
//                    hsb3[1]=hsb2[1]+ds;
                    if(hsb3[1]>1){
                        hsb3[1]=1;
                    } else if(hsb3[1]<0) {
                         hsb3[1]=0;
                    }
//                    hsb3[2]=hsb2[2]+db;
                    if(hsb3[2]>1){
                        hsb3[2]=1;
                    } else if(hsb3[2]<0) {
                         hsb3[2]=0;
                    }
                    Color c3=Color.getHSBColor(hsb3[0], hsb3[1], hsb3[2]);
                    texts.get(i).setBackground(c3);
                }
                Color c2=new java.awt.Color(255, 220, 220, 255);
                texts.get(10).setBackground(c2);
                
            }
        
        });
                
        panel=new JPanel(new FlowLayout(FlowLayout.RIGHT));       
        
        panel.add(bt4);
        c1=Box.createHorizontalGlue();
        panel.add(c1);
        c1=Box.createHorizontalGlue();
        panel.add(c1);
        root.add(panel);        
        
            
        
        root.add(new JLabel("说明1：从2开始,偶数为旧主色色系中的色。奇数是计算所得新主色色系中的色"));
        root.add(new JLabel("说明2：色相以主色之间色相差，在色相环中按差顺时取值。亮度、色饱和度之间相应变换。"));
        c1=Box.createHorizontalBox();        
        root.add(c1);
        
        SpringUtilities.makeCompactGrid(root,
                                        6,1, //rows, cols
                                        15, 15,        //initX, initY
                                        15, 15);       //xPad, yPad
        
        f.setSize(630, 750);
        f.setVisible (true);
    }
    
    public static List<Map.Entry<String, Integer>>  sortMapByValue(Map maps) {  
        List<Map.Entry<String, Integer>> info = new ArrayList<Map.Entry<String, Integer>>(maps.entrySet());  
        Collections.sort(info, new Comparator<Map.Entry<String, Integer>>() {  
            @Override
            public int compare(Map.Entry<String, Integer> obj1,Map.Entry<String, Integer> obj2) {  
                return obj2.getValue() - obj1.getValue();  
            }  
        });  
        return info; 
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
