/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj;

import G2Dj.Imp.Input.KeyboardInputHandler;
import G2Dj.Imp.Input.KeyCode;

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
    
    
    protected static void init(){}
    
}
