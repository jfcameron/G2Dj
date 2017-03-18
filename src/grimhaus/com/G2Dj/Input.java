/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import grimhaus.com.G2Dj.Imp.Input.KeyCode;

//.if DESKTOP
import grimhaus.com.G2Dj.Imp.Input.KeyboardInputHandler;
//.endif

/**
 *
 * @author Joe
 */
public class Input 
{
    //.if DESKTOP
    //**********************
    //Implementation objects
    //**********************

    protected static KeyboardInputHandler s_KeyboardInputHandler = new KeyboardInputHandler();


    //***********
    //Passthoughs
    //***********
    public static boolean getKeyDown(KeyCode aKeyCode){return s_KeyboardInputHandler.getKeyDown(aKeyCode);}
    public static boolean getKey(KeyCode aKeyCode){return s_KeyboardInputHandler.getKey(aKeyCode);}
    
    //.endif
    protected static void init(){}

    protected static void update()
    {
        //.if DESKTOP
        s_KeyboardInputHandler.update();
        //.endif

    }

}
