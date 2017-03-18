/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Imp.Input.KeyboardInputHandler;

import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.newt.opengl.GLWindow;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

/**
 *
 * @author Joe
 */
public final class Window implements WindowListener
{
    private final GLWindow m_GLWindow;
    
    //***************
    // Initialization
    //***************
    public Window(KeyboardInputHandler aKeyboardInputHandler) 
    {
        //ICON TEST
        //IOUtil.ClassResources res = new ClassResources(new String[]{"brick.png"}, getClass().getClassLoader(), getClass());
        System.setProperty("newt.window.icons", "G2Dj/Resource/Graphics/icon-16.png G2Dj/Resource/Graphics/icon-32.png");
        //NewtFactory.setWindowIcons(res);
        
        //Configure the GL
        Display display = NewtFactory.createDisplay(null);
        Screen screen = NewtFactory.createScreen(display, 0);
        GLProfile glProfile = GLProfile.get(GLProfile./*GL4ES3*/GL2ES2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        
        //Create the GL context & create the window
        m_GLWindow = GLWindow.create(screen, glCapabilities);
        
        //Configure the window
        setTitle("G2Dj");
        setSize(1024, 768);
        setPosition(50, 50);
        setAlwaysOnTop(false);
        setDisplayMode(DisplayMode.Windowed);
        setPointerVisible(true);
        setPointerLockMode(PointerLockMode.Free);
                
        //Bypass JOGL's multithreaded event system
        m_GLWindow.setAutoSwapBufferMode(false);//timing of bufferswaps is up to me
        m_GLWindow.getContext().makeCurrent();//Ownership of the context is given to this thread
        
        //Attach window event listeners
        m_GLWindow.addWindowListener((WindowListener)this);
        m_GLWindow.addKeyListener((KeyListener)aKeyboardInputHandler);
        
        GL gl = m_GLWindow.getGL();
        //gl.glClearColor(1.0f, 0.5f, 0.0f, 1.0f);
        
        grimhaus.com.G2Dj.Imp.Graphics.GL.gl = m_GLWindow.getGL().getGL2ES2();
                
    }
    
    //*****************
    // Window interface
    //*****************
    public void draw()
    {
        m_GLWindow.display();
        m_GLWindow.swapBuffers();
        
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
    
    public GL  getGL()    { return m_GLWindow.getGL();     }
    public int getWidth() { return m_GLWindow.getWidth();  }
    public int getHeight(){ return m_GLWindow.getHeight(); }

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