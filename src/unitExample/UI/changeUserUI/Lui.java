/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.UI.changeUserUI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicLabelUI;
import sun.swing.SwingUtilities2;

/**
 *
 * @author cloud
 */
public class Lui extends BasicLabelUI {
    protected static  Lui lui = new Lui();
    private static Rectangle viewRect = new Rectangle();
    private static Rectangle textRect = new Rectangle();
    private static Rectangle iconRect = new Rectangle();
    /**
     * 必须有createUI方法，否则框架无法调用生成
     */
    public static  Lui createUI(JComponent c) {         
          return lui;
    }

   
    @Override
    public  void paint(Graphics g,JComponent c) {  
        int x=c.getX();
        int y=c.getY();
        int w=c.getWidth();
        int h=c.getHeight();

        Graphics2D  g2 =  (Graphics2D)g;  
        Color c2= Color.ORANGE;  
        g2.setColor(c2);
        //GradientPaint,第1与第4个参数将会改变性线渐变的方向；第
        //5个参数，将取渐变的中间中某处色

        g2.setPaint(new  GradientPaint(0,0,c2,0,5*h/4,Color.WHITE)); 
        g2.fillRoundRect(0, 0, w, h, 100, 100);
        g2.fillRect(0,0,w,h );  
        g2.setColor(new Color(255, 255, 255, 180));    
        g2.fillRect(1,1,w-2 ,h/3);   
        
        JLabel l=(JLabel)c;
        
        FontMetrics fm = SwingUtilities2.getFontMetrics(l, g);        
        g2.setColor(Color.darkGray);
        g2.drawRect(0, 0, w, h);
        
        layout(l, l.getText(),SwingUtilities2.getFontMetrics(l, g),
               l.getWidth(), l.getHeight());
         paintText(g, c, textRect, l.getText());
        
      
    }  
    
    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        JLabel b = (JLabel) c;
//        ButtonModel model = b.getModel();
        FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);
        int mnemonicIndex = b.getDisplayedMnemonicIndex();


        /*** paint the text normally */
        g.setColor(b.getForeground());
        SwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
                                      textRect.x ,
                                      textRect.y + fm.getAscent() );
         
    }
       
    private String layout(JLabel l,String text, FontMetrics fm,
                      int width, int height) {
        Insets i = l.getInsets();
        viewRect.x = i.left;
        viewRect.y = i.top;
        viewRect.width = width - (i.right + viewRect.x);
        viewRect.height = height - (i.bottom + viewRect.y);

        textRect.x = textRect.y = textRect.width = textRect.height = 0;
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;

        return SwingUtilities.layoutCompoundLabel(
            l, fm, text, null,
            SwingConstants.TOP, SwingConstants.RIGHT,
            SwingConstants.TOP, SwingConstants.RIGHT,
            viewRect, iconRect, textRect,
            0);
//        return SwingUtilities.layoutCompoundLabel(
//            b, fm, b.getText(), b.getIcon(),
//            b.getVerticalAlignment(), b.getHorizontalAlignment(),
//            b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
//            viewRect, iconRect, textRect,
//            b.getText() == null ? 0 : b.getIconTextGap());
    }
}
