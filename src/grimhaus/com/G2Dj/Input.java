/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Type.Input.Touch;
import grimhaus.com.G2Dj.Imp.Input.TouchHandler;
import grimhaus.com.G2Dj.Type.Math.IntVector2;

//.if DESKTOP
import grimhaus.com.G2Dj.Imp.Input.KeyboardInputHandler;
import grimhaus.com.G2Dj.Imp.Input.MouseButtonCode;
import grimhaus.com.G2Dj.Imp.Input.MouseHandler;
//.endif

/**
 *
 * @author Joe
 */
public class Input 
{
    //**************
    //Implementation
    //**************
    //.if DESKTOP
    protected static final KeyboardInputHandler s_KeyboardInputHandler = new KeyboardInputHandler();
    //.endif
    protected static final TouchHandler S_TouchHandler = new TouchHandler();
    protected static final MouseHandler s_MouseHandler = new MouseHandler();

    protected static void update()
    {
        //.if DESKTOP
        s_KeyboardInputHandler.update();
        s_MouseHandler.update();
        //.endif

    }

    //****************
    //Public interface
    //****************
    //Mouse
    public static IntVector2 getMousePosition()
    {
        return s_MouseHandler.getPosition();
        
    }
    
    public static boolean getMouseButtonDown(final MouseButtonCode aMouseButtonCode)
    {
        return s_MouseHandler.getButtonDown(aMouseButtonCode);
        
    }
    
    public static boolean getMouseButton(final MouseButtonCode aMouseButtonCode)
    {
        return s_MouseHandler.getButton(aMouseButtonCode);
        
    }
    
    //Controller 
    public static boolean getButton(final int aController,final int aButton)//(replace int with more appropriate class types)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    
    }
    
    public static boolean getButtonDown(final int aController,final int aButton)//(replace int with more appropriate class types)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    
    }
    
    public static float getAxis(final int aController,final int aAxis)//(replace int with more appropriate class types)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    
    }
    
    public static int[] getControllerArray()//(replace int with more appropriate class types)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        
    }
    
    //Keyboard
    public static boolean getKeyDown(final KeyCode aKeyCode)
    {
        //.if DESKTOP
        return s_KeyboardInputHandler.getKeyDown(aKeyCode);
        //.elseif ANDROID
        //|throw new java.lang.UnsupportedOperationException("Not supported yet.");
        //.endif

    }

    public static boolean getKey(final KeyCode aKeyCode)
    {
        //.if DESKTOP
        return s_KeyboardInputHandler.getKey(aKeyCode);
        //.elseif ANDROID
        //|//throw new java.lang.UnsupportedOperationException("Not supported yet.");
        //|return false;
        //.endif

    }

    //Touch
    public static Touch[] getTouches()
    {
        return S_TouchHandler.getTouches();

    }

    public static int getTouchCount()
    {
        //.if DESKTOP
        return 0;
        //.elseif ANDROID
        //|return S_TouchHandler.getTouchCount();
        //.endif

    }

}
