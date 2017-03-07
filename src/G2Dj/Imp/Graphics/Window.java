/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Joe
 */
public class Window extends Frame implements WindowListener
{
    public Window() 
    {
        super("OpenGL");

        setLayout(new BorderLayout());
        
        addWindowListener(this);
        //addKeyListener((KeyListener)this);

        setSize(600, 600);
        setLocation(40, 40);

        setVisible(true);

        //setupJOGL();
        
        System.out.print("WINDOW CREATED");
        
    }
    
    /*public static void main(String[] args) 
    {
        //HelloWorldDemo demo = new HelloWorldDemo();
        //demo.setVisible(true);
    
    }*/

    //
    // WindowListener implementation
    //
    @Override
    public void windowOpened(WindowEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public void windowClosing(WindowEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    
    }

    @Override
    public void windowIconified(WindowEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public void windowDeiconified(WindowEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public void windowActivated(WindowEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public void windowDeactivated(WindowEvent e) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //System.out.print(e);
    
    }
    
}