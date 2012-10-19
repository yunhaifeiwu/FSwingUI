/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.Writer;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.OutputStreamWriter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.swing.PopupFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
 
public class MenTest {
    private Men men;
    
    //这是测试方法
    @Test
    public void dd() throws UnsupportedEncodingException, FileNotFoundException, IOException{
        
        String str;
        //用utf-8编码
        byte[] b="中華人民共和國;中华人民共和国".getBytes("utf-8");
        //用utf-8译码成unicode码
         str=new String(b,"utf-8");
        
        //用big5编码
        b=str.getBytes("big5");        
        
         File f = new File("c:/aa.txt"); 
         FileOutputStream out = new java.io.FileOutputStream(f);
         
         out.write(b);       
         out.write("xxx中華人民共和國;中华人民共和国".getBytes());
         out.close();
         
         //方式二，直接由unicode编码成GBK输出
         Writer o = new OutputStreamWriter(
                 new FileOutputStream(f,true), "GBK");
         o.write(str);
         o.close();
         
         
    }
    
    //这是类初始化
    @Before
    public void setUp() throws UnsupportedEncodingException {
         men=new Men(); 
         //得到utf-8形式
         
         byte[] b="中華人民共和國;中华人民共和国".getBytes("utf-8");
         //把utf-8编码 转成unicode编码
         men.setName(new String(b,"utf-8"));
    }

}

