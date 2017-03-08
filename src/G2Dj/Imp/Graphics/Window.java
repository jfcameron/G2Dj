/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Imp.Input.KeyboardInputHandler;

import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.opengl.GLWindow;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import java.awt.BorderLayout;
import java.awt.Frame;
//import java.awt.event.KeyListener;
//import java.awt.event.WindowListener;

/**
 *
 * @author Joe
 */
public class Window extends Frame implements WindowListener, GLEventListener
{
    private GLWindow glWindow;
    
    public Window(KeyboardInputHandler aKeyboardInputHandler) 
    {
        Display display = NewtFactory.createDisplay(null);
        Screen screen = NewtFactory.createScreen(display, 0);
        GLProfile glProfile = GLProfile.get(GLProfile.GL4);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        
        glWindow = GLWindow.create(screen, glCapabilities);

        glWindow.setSize(1024, 768);
        glWindow.setPosition(50, 50);
        glWindow.setUndecorated(false);
        glWindow.setAlwaysOnTop(false);
        glWindow.setFullscreen(false);
        glWindow.setPointerVisible(true);
        glWindow.confinePointer(false);
        glWindow.setTitle("Hello Triangle");
        glWindow.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);
        glWindow.setVisible(true);
        
        ///////////////
        
        //super("OpenGL");
/*
        setLayout(new BorderLayout());
        
        addWindowListener(this);
        addKeyListener((KeyListener)aKeyboardInputHandler);

        setSize(600, 600);
        setLocation(40, 40);

        setVisible(true);

        //setupJOGL();
        
        System.out.print("WINDOW CREATED");
        */

        glWindow.addWindowListener((WindowListener)this);
        glWindow.addKeyListener((KeyListener)aKeyboardInputHandler);
        
        
        
        
        
    }
    
    /*public static void main(String[] args) 
    {
        //HelloWorldDemo demo = new HelloWorldDemo();
        //demo.setVisible(true);
    
    }*/

    //*******************************
    // GLEventListener implementation
    //*******************************
    @Override
    public void init(GLAutoDrawable glad) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void dispose(GLAutoDrawable glad) 
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void display(GLAutoDrawable glad) 
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    //******************************
    // WindowListener implementation
    //******************************
    @Override
    public void windowResized(com.jogamp.newt.event.WindowEvent we) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void windowMoved(com.jogamp.newt.event.WindowEvent we) 
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void windowDestroyNotify(com.jogamp.newt.event.WindowEvent we) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.exit(0);
        
    }

    @Override
    public void windowDestroyed(com.jogamp.newt.event.WindowEvent we) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void windowGainedFocus(com.jogamp.newt.event.WindowEvent we) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void windowLostFocus(com.jogamp.newt.event.WindowEvent we) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void windowRepaint(WindowUpdateEvent wue) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
    
}