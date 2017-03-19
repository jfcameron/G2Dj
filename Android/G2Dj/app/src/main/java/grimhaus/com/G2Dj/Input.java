/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import java.util.ArrayList;

import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Type.Input.Touch;

//.if DESKTOP
//|import grimhaus.com.G2Dj.Imp.Input.KeyboardInputHandler;
//.endif

/**
 *
 * @author Joe
 */
public class Input 
{
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
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        //.endif

    }

    public static ArrayList<Touch> getTouches()
    {


        throw new java.lang.UnsupportedOperationException("Not supported yet.");

    }

    //**************
    //Implementation
    //**************
    //.if DESKTOP
    //|protected static final KeyboardInputHandler s_KeyboardInputHandler = new KeyboardInputHandler();
    //.elseif ANDROID
    protected static final ArrayList<Touch> s_Touches = new ArrayList<>();
    //.endif

    protected static void init(){}

    protected static void update()
    {
        //.if DESKTOP
        //|s_KeyboardInputHandler.update();
        //.endif

    }


}
