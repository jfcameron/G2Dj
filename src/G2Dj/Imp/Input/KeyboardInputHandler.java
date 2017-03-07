/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Input;

import G2Dj.Imp.Input.KeyCode;
import G2Dj.Imp.Input.KeyboardState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 *
 * @author Joe
 */
public class KeyboardInputHandler implements KeyListener
{
    //
    // Data members
    //
    private final KeyboardState m_KeyboardState = new KeyboardState();
    private final HashMap<Integer,KeyCode> m_AWTKeyToG2DjKeyMap = new HashMap<>();
    
    public KeyboardInputHandler()
    {
        initAWTKeyToG2DjKeyMap();
        
    }
    
    //
    // Implementation
    //
    private void initAWTKeyToG2DjKeyMap()
    {
        //Top row
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ESCAPE, KeyCode.Escape);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F1, KeyCode.F1);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F2, KeyCode.F2);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F3, KeyCode.F3);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F4, KeyCode.F4);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F5, KeyCode.F5);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F6, KeyCode.F6);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F7, KeyCode.F7);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F8, KeyCode.F8);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F9, KeyCode.F9);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F10, KeyCode.F10);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F11, KeyCode.F11);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F12, KeyCode.F12);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PRINTSCREEN, KeyCode.PrintScreen);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SCROLL_LOCK, KeyCode.ScrollLock);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PAUSE, KeyCode.PauseBreak);
        
        //Number row
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_1, KeyCode.One);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_2, KeyCode.Two);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_3, KeyCode.Three);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_4, KeyCode.Four);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_5, KeyCode.Five);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_6, KeyCode.Six);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_7, KeyCode.Seven);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_8, KeyCode.Eight);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_9, KeyCode.Nine);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_0, KeyCode.Zero);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_BACK_QUOTE, KeyCode.Tilda);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_MINUS, KeyCode.Minus);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_EQUALS, KeyCode.Equals);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_BACK_SPACE, KeyCode.Backspace);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_HOME, KeyCode.Home);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_END, KeyCode.End);

        //Q Row
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_TAB, KeyCode.Tab);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_OPEN_BRACKET, KeyCode.OpenBracket);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_CLOSE_BRACKET, KeyCode.CloseBracket);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_BACK_SLASH, KeyCode.Backslash);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_INSERT, KeyCode.Insert);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PAGE_UP, KeyCode.PageUp);
        
        //A row
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_CAPS_LOCK, KeyCode.Capslock);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SEMICOLON, KeyCode.SemiColon);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_QUOTE, KeyCode.Quote);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ENTER, KeyCode.Enter);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_DELETE, KeyCode.Delete);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PAGE_DOWN, KeyCode.PageDown);
        
        //Z row
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SHIFT, KeyCode.LeftShift);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_COMMA, KeyCode.Comma);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PERIOD, KeyCode.Period);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SLASH, KeyCode.ForwardSlash);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SHIFT, KeyCode.RightShift); //NOTE: AWT offers no way to differentiate shift keys
        
        //Alphabet
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_Q, KeyCode.Q);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_W, KeyCode.W);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_E, KeyCode.E);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_R, KeyCode.R);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_T, KeyCode.T);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_Y, KeyCode.Y);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_U, KeyCode.U);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_I, KeyCode.I);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_O, KeyCode.O);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_P, KeyCode.P);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_A, KeyCode.A);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_S, KeyCode.S);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_D, KeyCode.D);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F, KeyCode.F);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_G, KeyCode.G);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_H, KeyCode.H);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_J, KeyCode.J);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_K, KeyCode.K);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_L, KeyCode.L);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_Z, KeyCode.Z);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_X, KeyCode.X);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_C, KeyCode.C);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_V, KeyCode.V);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_B, KeyCode.B);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_N, KeyCode.N);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_M, KeyCode.M);
        
        //Bottom row
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_CONTROL, KeyCode.LeftControl); //NOTE: AWT offers no way to differentiate ctrl keys
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ALT, KeyCode.LeftAlt); //NOTE: AWT offers no way to differentiate alt keys
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SPACE, KeyCode.Space);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ALT, KeyCode.RightAlt);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_CONTROL, KeyCode.RightControl);
        
        //Arrow keys
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_LEFT, KeyCode.LeftArrow);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_RIGHT, KeyCode.RightArrow);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_UP, KeyCode.UpArrow);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_DOWN, KeyCode.DownArrow);
        
        //Numpad
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUM_LOCK, KeyCode.Numlock);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SLASH, KeyCode.NumSlash); //NOTE: AWT offers no way to differentiate slash keys
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ASTERISK, KeyCode.NumAsterisk); 
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_MINUS, KeyCode.NumMinus); //NOTE: AWT offers no way to differentiate minus keys
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD7, KeyCode.Num7);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD8, KeyCode.Num8);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD9, KeyCode.Num9);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PLUS, KeyCode.NumPlus);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD4, KeyCode.Num4);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD5, KeyCode.Num5);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD6, KeyCode.Num6);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD1, KeyCode.Num1);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD2, KeyCode.Num2);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD3, KeyCode.Num3);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ENTER, KeyCode.NumEnter);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD0, KeyCode.Num0);
        m_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PERIOD, KeyCode.NumPeriod);
        
    }
    
    public boolean getKeyDown(KeyCode aKeyCode)
    {
        return m_KeyboardState.getKey(aKeyCode);
        
    }
    
    //*************************************
    // KeyListener interface implementation
    //*************************************
    @Override
    public void keyTyped(KeyEvent e) 
    {
        //System.out.print(e + "KEY TYPED: ");
        
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        m_KeyboardState.setKey(m_AWTKeyToG2DjKeyMap.get(e.getKeyCode()), true);
        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        m_KeyboardState.setKey(m_AWTKeyToG2DjKeyMap.get(e.getKeyCode()), false);
                
    }
    
}
