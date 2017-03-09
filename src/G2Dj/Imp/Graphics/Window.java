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

/**
 *
 * @author Joe
 */
public class Window implements WindowListener, GLEventListener
{
    private GLWindow m_GLWindow;
    
    //***************
    // Initialization
    //***************
    public Window(KeyboardInputHandler aKeyboardInputHandler) 
    {
        //Configure the GL
        Display display = NewtFactory.createDisplay(null);
        Screen screen = NewtFactory.createScreen(display, 0);
        GLProfile glProfile = GLProfile.get(GLProfile.GL4);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        m_GLWindow = GLWindow.create(screen, glCapabilities);
        
        //Configure the window
        m_GLWindow.setSize(1024, 768);
        m_GLWindow.setPosition(50, 50);
        m_GLWindow.setUndecorated(false);
        m_GLWindow.setAlwaysOnTop(false);
        m_GLWindow.setFullscreen(false);
        m_GLWindow.setPointerVisible(true);
        m_GLWindow.confinePointer(false);
        m_GLWindow.setTitle("G2Dj Project");
        m_GLWindow.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);
        m_GLWindow.setVisible(true);
        
        //Attach listeners
        m_GLWindow.addWindowListener((WindowListener)this);
        m_GLWindow.addKeyListener((KeyListener)aKeyboardInputHandler);
        
    }
    
    //*****************
    // Window interface
    //*****************
    //public void setSize()

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