/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Type.Input.Touch;

import java.util.ArrayList;

//.if DESKTOP
//|import grimhaus.com.G2Dj.Imp.Input.KeyboardInputHandler;
//.elseif ANDROID
import grimhaus.com.G2Dj.Imp.Input.TouchHandler;
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
    //|protected static final KeyboardInputHandler s_KeyboardInputHandler = new KeyboardInputHandler();
    //.elseif ANDROID
    protected static final TouchHandler S_TouchHandler = new TouchHandler();
    //.endif

    protected static void init(){}

    protected static void update()
    {
        //.if DESKTOP
        //|s_KeyboardInputHandler.update();
        //.endif

    }

    //****************
    //Public interface
    //****************
    public static boolean getKeyDown(final KeyCode aKeyCode)
    {
        //.if DESKTOP
        //|return s_KeyboardInputHandler.getKeyDown(aKeyCode);
        //.elseif ANDROID
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        //.endif

    }

    public static boolean getKey(final KeyCode aKeyCode)
    {
        //.if DESKTOP
        //|return s_KeyboardInputHandler.getKey(aKeyCode);
        //.elseif ANDROID
        //throw new java.lang.UnsupportedOperationException("Not supported yet.");
        return false;
        //.endif

    }

    public static Touch[] getTouches()
    {
        //.if DESKTOP
        //|throw new java.lang.UnsupportedOperationException("Not supported yet.");
        //.elseif ANDROID
        return S_TouchHandler.getTouches();
        //.endif

    }

    public static int getTouchCount()
    {
        //.if DESKTOP
        //|return 0;
        //.elseif ANDROID
        return S_TouchHandler.getTouchCount();
        //.endif

    }

}
