/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.UI.layUI;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;
import sun.swing.SwingUtilities2;

/**
 *
 * @author cloud
 */
public class MyLayerUI extends LayerUI<JComponent>{
    private static Rectangle viewRect = new Rectangle();
    private static Rectangle textRect = new Rectangle();
    private static Rectangle iconRect = new Rectangle();
 
    @Override
  public void installUI(JComponent c) {
    super.installUI(c);
    JLayer jlayer = (JLayer)c;
    
    jlayer.setLayerEventMask(
      AWTEvent.MOUSE_EVENT_MASK |
      AWTEvent.MOUSE_MOTION_EVENT_MASK |
      AWTEvent.COMPONENT_EVENT_MASK
       
    );
  }

  @Override
  public void uninstallUI(JComponent c) {
    JLayer jlayer = (JLayer)c;
    jlayer.setLayerEventMask(0);
    super.uninstallUI(c);
  }
 
 

 

    @Override
    protected void processMouseEvent(MouseEvent e, JLayer<? extends JComponent> l) {
        super.processMouseEvent(e, l);
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e, JLayer<? extends JComponent> l) {
        super.processMouseMotionEvent(e, l);
    }
    @Override
    protected void processFocusEvent(FocusEvent e, JLayer<? extends JComponent> l) {
         
        super.processFocusEvent(e, l);
    }
    @Override
    public void paint(Graphics g, JComponent c) {  
        
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
        int mnemonicIndex=-1;

 
        // c 明义为JComponent，实际是JLayer
        Class cls=c.getClass();//得类反射操作器

        Field f =null ;
        try {
            f = cls.getDeclaredField("view"); //得到名为"view"的私有属性
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(MyLayerUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MyLayerUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        f.setAccessible(true);//允许改变属性；仅当为true，才能改变该属性的值
        JComponent view=null;
        try {
            view =(JComponent) f.get(c);//得到c的名为"view"私有属性的值；
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MyLayerUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MyLayerUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cls=view.getClass();
         Method m=null;
         String text="";
        try {
           m=cls.getMethod("getText", new Class[]{});
            try {
                text=(String) m.invoke(view, new Object[] {});
            } catch (IllegalAccessException ex) {
            } catch (IllegalArgumentException ex) {
            } catch (InvocationTargetException ex) {
            }
          
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MyLayerUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MyLayerUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       System.out.println();
        
        
        
        layout(c, text,SwingUtilities2.getFontMetrics(view, g),
               c.getWidth(), c.getHeight());
        
         paintText(g, c, mnemonicIndex,textRect, text);
         
//        g2.dispose();
    }
    
     protected void paintText(Graphics g, JComponent c, int mnemonicIndex,Rectangle textRect, String text) {
      
//        ButtonModel model = b.getModel();
        FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);        


        /*** paint the text normally */
        g.setColor(c.getForeground());
        SwingUtilities2.drawStringUnderlineCharAt(c, g,text, mnemonicIndex,
                                      textRect.x ,
                                      textRect.y + fm.getAscent() );
         
    }
       
    private String layout(JComponent l,String text, FontMetrics fm,
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
            SwingConstants.CENTER, SwingConstants.RIGHT,
            SwingConstants.CENTER, SwingConstants.RIGHT,
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
