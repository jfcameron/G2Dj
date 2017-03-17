/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import grimhaus.com.G2Dj.Imp.Input.KeyboardInputHandler;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;

/**
 *
 * @author Joe
 */
public class Input 
{    
    //**********************
    //Implementation objects
    //**********************
    protected static KeyboardInputHandler s_KeyboardInputHandler = new KeyboardInputHandler();
    
    //***********
    //Passthoughs
    //***********
    public static boolean getKeyDown(KeyCode aKeyCode){return s_KeyboardInputHandler.getKeyDown(aKeyCode);}
    public static boolean getKey(KeyCode aKeyCode){return s_KeyboardInputHandler.getKey(aKeyCode);}
    
    protected static void init(){}
    
    protected static void update()
    {
        s_KeyboardInputHandler.update();
        
    }
    
}
