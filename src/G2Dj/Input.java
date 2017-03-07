/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj;

import G2Dj.Implementation.Input.KeyboardInputHandler;

/**
 *
 * @author Joe
 */
public class Input 
{
    //**********************
    //Implementation objects
    //**********************
    private static KeyboardInputHandler s_KeyboardInputHandler = new KeyboardInputHandler();
    
    //***********
    //Passthoughs
    //***********
    public static boolean getKeyDown(){return s_KeyboardInputHandler.getKeyDown();}
    
}
