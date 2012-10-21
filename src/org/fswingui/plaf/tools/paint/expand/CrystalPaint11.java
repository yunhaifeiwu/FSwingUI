/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.tools.paint.expand;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import org.fswingui.plaf.tools.paint.AbstractPaint;

/**
 *
 * @author cloud
 */
public class CrystalPaint11 extends AbstractPaint{
    
    public CrystalPaint11(){
        super();
        super.setPaintID("org.fswingui.paint.1000");
        super.setDescribe("水晶效果");
         
    }
    
    @Override
    public  boolean isNodeFlag(){
        return false;
    }
    
    @Override
    protected void paintImp(Graphics g, JComponent c) {
        int x=c.getX();
        int y=c.getY();
        int w=c.getWidth();
        int h=c.getHeight();
        
        
        int azimuth= (Integer)  super.getParameterValue("azimuth");//方向    
        Color cs=(Color)  super.getParameterValue("ColorSource");//颜色源
        Color cd=(Color)  super.getParameterValue("ColorDestination");//颜色目的地
        float ratio= (Float)   super.getParameterValue("ratio");//渐变比  
        int gray= (Integer)  super.getParameterValue("gray");//灰度
        float highlightRatio = (Float)  super.getParameterValue("highlightRatio");//高亮所占比例
        float alpha = (Float)  super.getParameterValue("alpha");//高亮所占比例 

        Graphics2D  g2 =  (Graphics2D)g;  
        Color c2= Color.ORANGE;  
        g2.setColor(c2);
        //GradientPaint,第1与第4个参数将会改变性线渐变的方向；第
        //5个参数，将取渐变的中间中某处色
        Composite alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(alpha2);
        switch(azimuth){
            case 0://从上到下渐变
                g2.setPaint(new  GradientPaint(0,0,cs,0,ratio*h,cd)); 
                g2.fillRect(0,0,w,h );  
                g2.setColor(new Color(255, 255, 255, gray));  
                g2.fillRect(0,0,w-2 ,Math.round( h*highlightRatio));           
                break;
            case 1://从左到右
                g2.setPaint(new  GradientPaint(0,0,cs,ratio*w,0,cd)); 
                g2.fillRect(0,0,w,h );  
                g2.setColor(new Color(255, 255, 255, gray));   
                g2.fillRect(0,0,Math.round(w*highlightRatio ),h);    
                break;
            case 2://从下到上渐变
                g2.setPaint(new  GradientPaint(0,0,cd,0,ratio*h,cs)); 
                g2.fillRect(0,0,w,h );  
                g2.setColor(new Color(255, 255, 255, 180));  
                g2.fillRect(0,Math.round(highlightRatio*h),w-2 ,h);    
                break;
            default://从右到左
                g2.setPaint(new  GradientPaint(ratio*w,0,cd,0,0,cs)); 
                g2.fillRect(0,0,w,h );  
                g2.setColor(new Color(255, 255, 255, gray));   
                g2.fillRect(0,0,Math.round(w*highlightRatio),h);    
                break;
        }   
        
  
    }

    @Override
    protected void setParameter() {
        try {
            
            super.addParameter("azimuth", "方向",0);
            
            super.addParameter("ColorSource", "颜色1",Color.orange);
            super.addParameter("ColorDestination","颜色2", Color.WHITE);
           
            super.addParameter("ratio","比率",1.25f);
            super.addParameter("gray", "高光区灰度",180);
            super.addParameter("highlightRatio","高光区比",0.33f);
            super.addParameter("alpha","透明度",0.9f);
            
        } catch (Exception ex) {
            Logger.getLogger(CrystalPaint11.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) throws Exception{
         AbstractPaint paint =new CrystalPaint11 ();
         AbstractPaint paint1=paint.clone();System.out.println(paint.getArgs().get("azimuth").value);
         System.out.println(paint1.getArgs().get("azimuth").value);
         paint1.addArg("azimuth", 333);
        
         System.out.println(paint.getArgs().get("azimuth").value);
         System.out.println(paint1.getArgs().get("azimuth").value);
    }
    
}
