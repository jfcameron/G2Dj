/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import G2Dj.Imp.Input.KeyboardInputHandler;

import G2Dj.Imp.Graphics.DisplayMode;
import G2Dj.Imp.Input.KeyCode;
import G2Dj.Input;

import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;

import com.jogamp.opengl.GL;

import com.jogamp.newt.opengl.GLWindow;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

/**
 *
 * @author Joe
 */
public final class Window implements WindowListener, GLEventListener
{
    private final GLWindow m_GLWindow;
    
    //***************
    // Initialization
    //***************
    public Window(KeyboardInputHandler aKeyboardInputHandler) 
    {
        //Configure the GL
        Display display = NewtFactory.createDisplay(null);
        Screen screen = NewtFactory.createScreen(display, 0);
        GLProfile glProfile = GLProfile.get(GLProfile.GL3);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        
        //Create the GL context & create the window
        m_GLWindow = GLWindow.create(screen, glCapabilities);
        
        //Configure the window
        setTitle("G2Dj Project");
        setSize(1024, 768);
        setPosition(50, 50);
        setAlwaysOnTop(false);
        setDisplayMode(DisplayMode.Windowed);
        setPointerVisible(true);
        setPointerLockMode(PointerLockMode.Free);
        //m_GLWindow.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG); //TODO: decide on this
        
        //Attach window event listeners
        m_GLWindow.addWindowListener((WindowListener)this);
        m_GLWindow.addKeyListener((KeyListener)aKeyboardInputHandler);
        m_GLWindow.addGLEventListener(this);
        
        ///????????????
        final Animator animator = new Animator(m_GLWindow);
        animator.setRunAsFastAsPossible(true);
        animator.start();
        
    }
    
    //*****************
    // Window interface
    //*****************
    @Override
    public void init(GLAutoDrawable drawable) 
    {
        GL gl = drawable.getGL();
        
        gl.glViewport(0, 0, m_GLWindow.getWidth(), m_GLWindow.getHeight());
        gl.glDepthMask(true);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glClearColor(1.0f, 0.5f, 0.0f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
    }
    
    @Override
    public void display(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        //Debug.log("drawing");
        
        //m_GLWindow.swapBuffers();
        //m_GLWindow.display();
        
        if (Input.getKey(KeyCode.A))
        {
            gl.glClearColor(0.0f, 0.5f, 0.0f, 1.0f);
            
        }
        
        if (Input.getKey(KeyCode.S))
        {
            gl.glClearColor(0.0f, 0.1f, 0.7f, 1.0f);
            
        }
        
    }
    
    public void draw()
    {
        
        
        //m_GLWindow.swapBuffers();
        //m_GLWindow.display();
        
        
    }
    
    public final void setSize(final int aX, final int aY){m_GLWindow.setSize(aX, aY);}
    public final void setPosition(final int aX, final int aY){m_GLWindow.setPosition(aX, aY);}
    public final void setAlwaysOnTop(final boolean aIsAlwaysOnTop){m_GLWindow.setAlwaysOnTop(aIsAlwaysOnTop);}
    public final void setPointerVisible(final boolean aPointerIsVisible){m_GLWindow.setPointerVisible(aPointerIsVisible);}
    public final void setTitle(final String aTitle){m_GLWindow.setTitle(aTitle);}
    
    public final void setPointerLockMode(final PointerLockMode aPointerLockMode)
    {
        m_GLWindow.confinePointer(false);
        //m_GLWindow
        
        switch(aPointerLockMode)
        {
            case Free:
            {
                                
            } break;
            
            case ConfinedToWindow:
            {
                m_GLWindow.confinePointer(true);
                
            } break;
            
            case Locked:
            default:
                throw new UnsupportedOperationException("Not supported yet.");
            
        }
        
    }
    
    public final void setDisplayMode(final DisplayMode aDisplayMode)
    {
        m_GLWindow.setFullscreen(false);
        m_GLWindow.setUndecorated(false);
        m_GLWindow.setVisible(true);
                
        switch(aDisplayMode)
        {
            case Windowed:
            {
                
            } break;
            
            case Borderless:
            {
                m_GLWindow.setUndecorated(true);
                
            } break;
            
            case FullScreen:
            {
                m_GLWindow.setFullscreen(true);
                
            } break;
            
            case Hidden:
            {
                m_GLWindow.setVisible(false);
                
            } break;
            
        }
        
    }

    //*******************************
    // GLEventListener implementation
    //*******************************
    /*@Override
    public void init(GLAutoDrawable glad) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }*/

    @Override
    public void dispose(GLAutoDrawable glad) 
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    /*@Override
    public void display(GLAutoDrawable glad) 
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }*/

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