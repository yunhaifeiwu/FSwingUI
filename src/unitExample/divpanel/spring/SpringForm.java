/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.divpanel.spring;

import javax.swing.*;
import java.awt.*;
import org.fswingui.utility.SpringUtilities;

public class SpringForm {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
//        String[] labels = {"Name: ", "Fax: ", "Email: ", "Address: "};
//        int numPairs = labels.length;
//
//        //Create and populate the panel.
        JPanel p = new JPanel(new SpringLayout());
//        for (int i = 0; i < numPairs; i++) {
//            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
//            p.add(l);
//            JTextField textField = new JTextField(10);
//            l.setLabelFor(textField);
//            p.add(textField);
//        }
        
        p.add(new JLabel("aaaa"));
        p.add(new JTextField("aaaa"));
        p.add(new JLabel("bbbb"));
        p.add(new JTextField("bbbb"));
        
        p.add(Box.createHorizontalBox());
        p.add(Box.createVerticalBox());
        p.add(new JLabel("cccc"));
        p.add(new JTextField("cccc"));

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                                        2, 4, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad
//        SpringUtilities.makeGrid(p,
//                                        numPairs/2, 4, //rows, cols
//                                        6, 6,        //initX, initY
//                                        6, 6);       //xPad, yPad

        //Create and set up the window.
        JFrame frame = new JFrame("SpringForm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        p.setOpaque(true);  //content panes must be opaque
        frame.setContentPane(p);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}


 