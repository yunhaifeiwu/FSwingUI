/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malachite;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;

//public class MalachiteButtonUI extends MetalButtonUI
public class MalachiteButtonUI extends BasicButtonUI
//当然你可以继承BasicButtonUI
    implements java.io.Serializable, MouseListener, KeyListener
{

    private final static MalachiteButtonUI m_buttonUI = new MalachiteButtonUI();
    protected Border m_borderRaised = null;
    protected Border m_borderLowered = null;
    protected Color  m_backgroundNormal = null;
    protected Color  m_backgroundPressed = null;
    protected Color  m_foregroundNormal = null;
    protected Color  m_foregroundActive = null;
    protected Color  m_focusBorder = null;

    public MalachiteButtonUI() {}
    public static ComponentUI createUI( JComponent c ) {return m_buttonUI;  }
    
    public void installUI(JComponent c) {
        super.installUI(c);
        m_borderRaised = UIManager.getBorder("Button.border");

        m_borderLowered = UIManager.getBorder("Button.borderPressed");

        m_backgroundNormal = UIManager.getColor("Button.background");
        m_backgroundPressed = UIManager.getColor("Button.pressedBackground");
        m_foregroundNormal = UIManager.getColor("Button.foreground");
        m_foregroundActive = UIManager.getColor("Button.activeForeground");
        m_focusBorder = UIManager.getColor( "Button.focusBorder");
        c.addMouseListener(this);
        c.addKeyListener(this);

    }

    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        c.removeMouseListener(this);
        c.removeKeyListener(this);
    }

    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        super.paint(g, c);
        b.setBorder(BorderFactory.createLineBorder(Color.red));
        b.getBorder().paintBorder(b, g, 1, 1, b.getWidth(), b.getHeight());
    }

    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);
        if (m_borderRaised != null) {
            Insets ins = m_borderRaised.getBorderInsets(c);
            d.setSize(d.width+ins.left+ins.right,
            d.height+ins.top+ins.bottom);
        }
        return d;
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        JComponent c = (JComponent)e.getComponent();
        c.setBorder(m_borderLowered);
        c.setBackground(m_backgroundPressed);
    }

    public void mouseReleased(MouseEvent e) {
        JComponent c = (JComponent)e.getComponent();
        c.setBorder(m_borderRaised);
        c.setBackground(m_backgroundNormal);
    }

    public void mouseEntered(MouseEvent e) {
        JComponent c = (JComponent)e.getComponent();
        c.setForeground(m_foregroundActive);
        c.repaint();
    }

    public void mouseExited(MouseEvent e) {
        JComponent c = (JComponent)e.getComponent();
        c.setForeground(m_foregroundNormal);
        c.repaint();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            JComponent c = (JComponent)e.getComponent();
            c.setBorder(m_borderLowered);
            c.setBackground(m_backgroundPressed);
        }

    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            JComponent c = (JComponent)e.getComponent();
            c.setBorder(m_borderRaised);
            c.setBackground(m_backgroundNormal);
        }

    }

}