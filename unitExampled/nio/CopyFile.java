/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.nio;

import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.nio.channels.FileChannel;
  
public class CopyFile {  
    public static void main(String[] args) throws Exception {  
        try {
            // Create channel on the source
            FileChannel srcChannel = new FileInputStream("C:\\\\swing ui.jpg").getChannel();

            // Create channel on the destination
            FileChannel dstChannel = new FileOutputStream("C:\\\\swing uidd.jpg").getChannel();

            // Copy file contents from source to destination
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

            // Close the channels
            srcChannel.close();
            dstChannel.close();
        } catch (IOException e) {
        }
       
    }  
}  