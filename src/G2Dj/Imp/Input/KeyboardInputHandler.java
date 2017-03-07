/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Joe
 */
public class KeyboardInputHandler implements KeyListener
{
    public void keyTyped(KeyEvent e) 
    {
        System.out.print(e + "KEY TYPED: ");
    
    }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) 
    {
        System.out.print(e + "KEY PRESSED: ");
    
    }

    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) 
    {
        System.out.print(e + "KEY RELEASED: ");
    
    }

    public boolean getKeyDown(KeyCode aKeyCode)
    {
        //TODO: implementation
        //(KeyEvent e) {
        /*KeyEvent e = new KeyEvent("a");
        if(e.getKeyCode() == e.VK_DOWN)
            System.out.println("So far, so good..");
*/
        
        System.out.print("hello");
        
        return false;
        
    }
    
}
