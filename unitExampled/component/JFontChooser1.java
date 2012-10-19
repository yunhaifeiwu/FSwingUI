/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.component;

import java.awt.*; 
import java.awt.event.*; 
import javax.swing.JColorChooser; 
//import javax.swing.border.*; 
public class JFontChooser1 extends Frame implements ActionListener 
{ 
    Choice font,size,bolder; 
    Button bb; 
    JFontChooser1(String s)  { 
        setTitle(s); 
        font=new Choice(); 
        bolder=new Choice(); 
        size=new Choice(); 
        //bolder.add加监视器 
        //font.add加监视器 
        //size.add加监视器 
        Panel p1=new Panel(); 
        Panel p2=new Panel(); 
        bb=new Button("点击打开"); 
        bb.addActionListener(this); 
        p1.setLayout(new GridLayout(4,1)); 
        p2.setLayout(new GridLayout(4,1)); 

        GraphicsEnvironment gg=GraphicsEnvironment.getLocalGraphicsEnvironment(); 
        String ss[]=gg.getAvailableFontFamilyNames(); 
        String bold[]={"Font.BOLD","Font.CENTER_BASELINE","Font.CENTER_BASELINE","Font.ITALIC", 
        "Font.PLAIN","Font.ROMAN_BASELINE","Font.TRUETYPE_FONT"}; 
        for(int i=126;i<ss.length;i++)  font.add(ss[i]); 
        for(int i=12;i<=64;i+=2) 
        { 
            String w=String.valueOf(i); 
            size.add(w); 
        } 
        for(int i=0;i<bold.length;i++) { 
            bolder.add(bold[i]); 
        } 
        p1.add(new Label("请选择字体")); 
        p1.add(font); 
        p1.add(new Label("请选择大小")); 
        p1.add(size); 
        p2.add(new Label("请选择字型")); 
        p2.add(bolder); 
        p2.add(new Label("请选择字体颜色")); 
        p2.add(bb); 
        add(p2,BorderLayout.WEST); 
        add(p1,BorderLayout.EAST); 
        setSize(250,150); 
        setVisible(true); 
        pack(); 
        addWindowListener(new WindowAdapter()  { 
            public void windowClosing(WindowEvent ee) { 
                System.exit(0); 
            } 
        }); 
    } 
    
        public void actionPerformed(ActionEvent e) { 
            Color cc=JColorChooser.showDialog(this,"颜色对话框",null); 
            bb.setBackground(cc);//应用举例 
        } 
        
        public static void main(String[] args)  { 
            new JFontChooser1("字体对话框"); 
        } 
    } 
 
