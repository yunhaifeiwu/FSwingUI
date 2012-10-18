/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.lang;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author Administrator
 */
public class InputStreamAndOutStreamTest {
    public static void main(String[] args) throws Exception{
		
            /**
             * InputStream与OutputStream的使用例子
             * 
             * (缓冲文件输入流)BufferedInputStream → 
             * (文件输入流)FileInputStream → (输入流)java.io.InputStream
             * 
             * (缓冲文件输出流)BufferedOuputStream →
             * (文件输出流)FileOuputStream → (输出流)java.io.OutputStream
             */

            /**
             * 1.通过流复制一个图片的例子
             */
/*            File file = new File("1.jpg");
            File outfile = new File("temp.jpg");

            FileInputStream inputStream = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(outfile);

            int i = 0;
            while(i != -1) {
                    i = inputStream.read();
                    outputStream.write(i);
            }
            //注意流的关闭(★必须的)
            inputStream.close();
            outputStream.close();
*/
		
        /**
         * 2.如果我们想提高要提高复制的速度，可以采用缓冲文件输入\输出流，如下：
         */
/*        File file = new File("C:/images/1.png");
        File outfile = new File("C:/temp1.jpg");

        //文件输入流
        FileInputStream inputStream = new FileInputStream(file);
        //文件输出流
        FileOutputStream outputStream = new FileOutputStream(outfile);

        //缓冲文件输入流
        BufferedInputStream bufferedInputStream = 
            new BufferedInputStream(inputStream);
        //缓冲文件输出流
        BufferedOutputStream bufferedOutputStream = 
            new BufferedOutputStream(outputStream);

        int i = 0;
        while(i != -1) {
            i = bufferedInputStream.read();
            bufferedOutputStream.write(i);
        }
        //流的关闭
        bufferedOutputStream.flush();//强制清除缓冲区的内容
        bufferedInputStream.close();
        bufferedOutputStream.close();
*/
		
        /**
         * 3.当文件很大,我们要做一个缓冲处理来提高速度。如下：当文件的大小大于512个
         * 字节时，每次读入512个字节后再做处理
         * 
         */
/*        File file = new File("1.jpg");
        File outfile = new File("temp2.jpg");

        //文件输入流
        FileInputStream inputStream = new FileInputStream(file);
        //文件输出流
        FileOutputStream outputStream = new FileOutputStream(outfile);

        int i = 0;
        //缓冲大小为512字节
        byte[] buffer = new byte[512];
        while(true) {
            if(inputStream.available() < 512) {
                while(i != -1) {
                        i = inputStream.read();
                        outputStream.write(i);
                }
                break;//注意此处不能忘记哦
            } else {
                //当文件的大小大于512字节时
                inputStream.read(buffer);
                outputStream.write(buffer);
            }
        }

        //流的关闭
        //注意流的关闭(★必须的)
        inputStream.close();
        outputStream.close();

		
        /**
         * 4.根据上面的例子，我们可以知道：我们可以做一个双缓冲的文件复制
         */

        File file = new File("1.jpg");
        File outfile = new File("temp3.jpg");

        //文件输入流
        FileInputStream inputStream = new FileInputStream(file);
        //文件输出流
        FileOutputStream outputStream = new FileOutputStream(outfile);

        //缓冲文件输入流
        BufferedInputStream bufferedInputStream = 
                new BufferedInputStream(inputStream);
        //缓冲文件输出流
        BufferedOutputStream bufferedOutputStream = 
                new BufferedOutputStream(outputStream);

        int i = 0;
        //缓冲区的大小
        byte[] buffer = new byte[512];

        while(true) {
            if(bufferedInputStream.available() < 512) {
                while(i != -1) {
                    i = bufferedInputStream.read();
                    bufferedOutputStream.write(i);
                }
                break;
            } else {
                //当文件的大小还大于512字节时
                bufferedInputStream.read(buffer);
                bufferedOutputStream.write(buffer);

            }
        }

        //强制清空缓冲区的内容
        bufferedOutputStream.flush();
        //流的关闭
        bufferedInputStream.close();
        bufferedOutputStream.close();
	
    }
}
