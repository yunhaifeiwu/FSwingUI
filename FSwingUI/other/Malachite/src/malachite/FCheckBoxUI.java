/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malachite;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;

public class FCheckBoxUI extends BasicCheckBoxUI{
    private final static FCheckBoxUI myUI = new FCheckBoxUI();
    public static ComponentUI createUI( JComponent c ) {return myUI;  }

    @Override
    public synchronized void paint(Graphics g, JComponent c) {
        super.paint(g, c);
    }
 
}
