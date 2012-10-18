/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf;

import java.awt.Color;
import java.math.BigDecimal;

/**
 *
 * @author cloud
 */
public class SimpleHueAutoChange extends AbstractColorModel {

    public SimpleHueAutoChange() {
        super();
    }
    
    public SimpleHueAutoChange(Color oldMainColor, Color newMainColor) {
        super(oldMainColor, newMainColor);
    }
   
    @Override
    protected Color getColorFromChangeImpl(Color c) {
        return getColorFromChangeImpl(this.getOldMainColor(),this.getNewMainColor(),c);
    }
    
    public static  Color getColorFromChangeImpl(Color oldMainColor,
            Color newMainColor,Color c)
    {//本算法，先把新旧主色转变成HSB色彩模式，再根据色们的差值，相应调整。
     //核心关键在于，亮度 包饱和度变换的数学模式。 以亮度为例本算法的公式为：
     // Db_old/b_old ==Db_new/b_new. 其中：       
     // Db_old-----在〈旧主色系的待变换色的亮 度〉 减去 〈旧主色的亮度〉的差值
     // b_old------旧主色的亮度
     // Db_new----《变化后的色 在新主色系的亮度》 减去 《新主色的亮度》的差值
     // b_new------新主色的亮度
        
        if (oldMainColor==null || newMainColor==null || c==null )
        {
            return c;
        }
        
        Color oldmc=oldMainColor;
        Color newmc=newMainColor;
        Color oldc=c;
        Color newc=null;//存放转换后的色，RGB
        
        float[] oldMHsb={0,0,0};//旧主色的hsb值
        Color.RGBtoHSB(
            oldmc.getRed(), oldmc.getGreen(),oldmc.getBlue(), oldMHsb
        );               
 
        float[] newMHsb={0,0,0};//新主色的hsb值
        Color.RGBtoHSB(
            newmc.getRed(), newmc.getGreen(),newmc.getBlue(), newMHsb
        );
        float dh=newMHsb[0]-oldMHsb[0];//色度差值---以变色相环中变换色相（色相指红绿蓝等）
        float ds=newMHsb[1]-oldMHsb[1];//色饱和度差值---以确定色的纯度转换
        float db=newMHsb[2]-oldMHsb[2];//色的亮度差值----以确定色的亮度转换
                 
        float[] oldCHsb={0,0,0};
        Color.RGBtoHSB(
            oldc.getRed(), oldc.getGreen(),oldc.getBlue(), oldCHsb
        );                      

        float[] newCHsb={0,0,0};//存放转换后的色 的HSB值
        //色相转换----开始-------               
        //原主色系中的色按主色差值，推出新的色. java中hsb的h是纯小数。
        // 乘以360就得 HSB 颜色模式中的色彩角度        
        newCHsb[0]=oldCHsb[0]+dh;
        int fInt = (int) newCHsb[0];//取小数部分--begin
        BigDecimal b1 = new BigDecimal(Float.toString(newCHsb[0]));
        BigDecimal b2 = new BigDecimal(Integer.toString(fInt));
        float fPoint = b1.subtract(b2).floatValue();//取小数部份--end
        newCHsb[0]=fPoint;
        if(newCHsb[0]<0){
            newCHsb[0]=1+newCHsb[0];
        }           
        //色相转换----结束-------    
        
        //色饱和度转换
        newCHsb[1]=newMHsb[1]-
                newMHsb[1]*(oldMHsb[1]-oldCHsb[1])
                /oldMHsb[1];
        
        //色的亮度转换
        newCHsb[2]=newMHsb[2]-
                newMHsb[2]*(oldMHsb[2]-oldCHsb[2])
                /oldMHsb[2];
                    
        //色饱和度值超界处理
        if(newCHsb[1]>1){
            newCHsb[1]=1;
        } else if(newCHsb[1]<0) {
             newCHsb[1]=0;
        } 
        
        //亮度值超界处理
        if(newCHsb[2]>1){
            newCHsb[2]=1;
        } else if(newCHsb[2]<0) {
             newCHsb[2]=0;
        }
        
        newc=Color.getHSBColor(newCHsb[0], newCHsb[1], newCHsb[2]);        
        return newc;        
    }
    
}
