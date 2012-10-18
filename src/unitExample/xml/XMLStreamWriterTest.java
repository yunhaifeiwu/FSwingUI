/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.stream.XMLOutputFactory;

public class XMLStreamWriterTest {
    public void write() throws IOException, XMLStreamException{
        XMLOutputFactory outputFactory=XMLOutputFactory.newInstance();
        FileWriter output=new FileWriter(new File("catalog.xml"));
        XMLStreamWriter writer=outputFactory.createXMLStreamWriter(output);
       
        writer.writeStartDocument("UTF-8","1.0");
        writer.writeComment("A OReilly Journal Catalog");
        writer.writeProcessingInstruction("catalog","journal='OReilly'");
        writer.writeStartElement("journal","catalog","http://OnJava.com/Journal");
        writer.writeNamespace("journal","http://OnJava.com/Journal");
        writer.writeNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
        writer.writeAttribute("xsi:noNamespaceSchemaLocation","file://c:/Schemas/catalog.xsd");
        writer.writeAttribute("publisher","OReilly");
        writer.writeStartElement("journal","journal","http://OnJava.com/Journal");
       
        writer.writeCharacters("Data Binding with XMLBeans");
        writer.writeEndElement();
        writer.writeEndElement();
        writer.flush();
        writer.close();
        output.close();
    }
    public  static void main(String[] args) throws IOException, XMLStreamException{
        XMLStreamWriterTest t=new XMLStreamWriterTest();
        t.write();
    }
    
}
