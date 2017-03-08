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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Joe
 */
public class KeyboardInputHandler implements KeyListener
{
    //
    // Data members
    //
    private static final int c_NumberOfFrames = 2;
    private static final HashMap<Integer,KeyCode> s_AWTKeyToG2DjKeyMap = new HashMap<>();
    
    private final ArrayList<KeyboardState> m_KeyboardStates = new ArrayList<>();
    private int m_CurrentKeyboardStateIndex = 0;
    
    
    //
    // init
    //
    public KeyboardInputHandler()
    {
        for(int i = 0; i < c_NumberOfFrames; i++)
            m_KeyboardStates.add(new KeyboardState());
                
    }
    
    static
    {
        //Top row
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ESCAPE, KeyCode.Escape);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F1, KeyCode.F1);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F2, KeyCode.F2);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F3, KeyCode.F3);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F4, KeyCode.F4);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F5, KeyCode.F5);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F6, KeyCode.F6);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F7, KeyCode.F7);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F8, KeyCode.F8);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F9, KeyCode.F9);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F10, KeyCode.F10);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F11, KeyCode.F11);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F12, KeyCode.F12);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PRINTSCREEN, KeyCode.PrintScreen);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SCROLL_LOCK, KeyCode.ScrollLock);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PAUSE, KeyCode.PauseBreak);
        
        //Number row
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_1, KeyCode.One);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_2, KeyCode.Two);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_3, KeyCode.Three);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_4, KeyCode.Four);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_5, KeyCode.Five);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_6, KeyCode.Six);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_7, KeyCode.Seven);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_8, KeyCode.Eight);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_9, KeyCode.Nine);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_0, KeyCode.Zero);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_BACK_QUOTE, KeyCode.Tilda);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_MINUS, KeyCode.Minus);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_EQUALS, KeyCode.Equals);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_BACK_SPACE, KeyCode.Backspace);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_HOME, KeyCode.Home);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_END, KeyCode.End);

        //Q Row
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_TAB, KeyCode.Tab);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_OPEN_BRACKET, KeyCode.OpenBracket);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_CLOSE_BRACKET, KeyCode.CloseBracket);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_BACK_SLASH, KeyCode.Backslash);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_INSERT, KeyCode.Insert);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PAGE_UP, KeyCode.PageUp);
        
        //A row
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_CAPS_LOCK, KeyCode.Capslock);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SEMICOLON, KeyCode.SemiColon);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_QUOTE, KeyCode.Quote);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ENTER, KeyCode.Enter);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_DELETE, KeyCode.Delete);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PAGE_DOWN, KeyCode.PageDown);
        
        //Z row
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SHIFT, KeyCode.LeftShift);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_COMMA, KeyCode.Comma);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PERIOD, KeyCode.Period);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SLASH, KeyCode.ForwardSlash);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SHIFT, KeyCode.RightShift); //NOTE: AWT offers no way to differentiate shift keys
        
        //Alphabet
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_Q, KeyCode.Q);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_W, KeyCode.W);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_E, KeyCode.E);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_R, KeyCode.R);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_T, KeyCode.T);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_Y, KeyCode.Y);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_U, KeyCode.U);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_I, KeyCode.I);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_O, KeyCode.O);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_P, KeyCode.P);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_A, KeyCode.A);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_S, KeyCode.S);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_D, KeyCode.D);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_F, KeyCode.F);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_G, KeyCode.G);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_H, KeyCode.H);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_J, KeyCode.J);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_K, KeyCode.K);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_L, KeyCode.L);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_Z, KeyCode.Z);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_X, KeyCode.X);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_C, KeyCode.C);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_V, KeyCode.V);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_B, KeyCode.B);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_N, KeyCode.N);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_M, KeyCode.M);
        
        //Bottom row
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_CONTROL, KeyCode.LeftControl); //NOTE: AWT offers no way to differentiate ctrl keys
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ALT, KeyCode.LeftAlt); //NOTE: AWT offers no way to differentiate alt keys
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SPACE, KeyCode.Space);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ALT, KeyCode.RightAlt);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_CONTROL, KeyCode.RightControl);
        
        //Arrow keys
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_LEFT, KeyCode.LeftArrow);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_RIGHT, KeyCode.RightArrow);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_UP, KeyCode.UpArrow);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_DOWN, KeyCode.DownArrow);
        
        //Numpad
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUM_LOCK, KeyCode.Numlock);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_SLASH, KeyCode.NumSlash); //NOTE: AWT offers no way to differentiate slash keys
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ASTERISK, KeyCode.NumAsterisk); 
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_MINUS, KeyCode.NumMinus); //NOTE: AWT offers no way to differentiate minus keys
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD7, KeyCode.Num7);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD8, KeyCode.Num8);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD9, KeyCode.Num9);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PLUS, KeyCode.NumPlus);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD4, KeyCode.Num4);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD5, KeyCode.Num5);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD6, KeyCode.Num6);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD1, KeyCode.Num1);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD2, KeyCode.Num2);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD3, KeyCode.Num3);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_ENTER, KeyCode.NumEnter);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_NUMPAD0, KeyCode.Num0);
        s_AWTKeyToG2DjKeyMap.put(KeyEvent.VK_PERIOD, KeyCode.NumPeriod);
        
    }
    
    //
    // Implementation
    //
    public void update()
    {
        //calculate the last frame index
        int lastFrameIndex = calcLastFrameIndex();
        {
            //System.out.print(m_CurrentKeyboardStateIndex+", "+lastFrameIndex+"\n");
            //m_KeyboardStates.get(m_CurrentKeyboardStateIndex).forEach((k,v) -> System.out.println("key: "+k+" value:"+v));
            
            //System.out.print(m_CurrentKeyboardStateIndex+": "+m_KeyboardStates.get(m_CurrentKeyboardStateIndex).getKey(KeyCode.Tilda)+"\n");
            
            m_KeyboardStates.get(m_CurrentKeyboardStateIndex).forEach
            (
                (k,v) -> 
                {
                    
                    //if (v == KeyState.JustPressed || v == KeyState.Up)
                    //{    
                        if (m_KeyboardStates.get(lastFrameIndex).getKey(k) == KeyState.JustPressed)
                        {
                            m_KeyboardStates.get(m_CurrentKeyboardStateIndex).setKey(k, KeyState.Down);
                    
                        }
                        else if (m_KeyboardStates.get(lastFrameIndex).getKey(k) == KeyState.JustReleased)
                        {
                            m_KeyboardStates.get(m_CurrentKeyboardStateIndex).setKey(k, KeyState.Up);
                    
                        }
                        else
                        {
                            m_KeyboardStates.get(m_CurrentKeyboardStateIndex).setKey(k,m_KeyboardStates.get(lastFrameIndex).getKey(k));
                            
                        }
                        
                        
                    
                }
        
            );
        
        }
        
        m_CurrentKeyboardStateIndex = calcNextFrameIndex(); //update the current frame
        
    }
    
    private int calcLastFrameIndex()
    {
        return m_CurrentKeyboardStateIndex > 0 ? m_CurrentKeyboardStateIndex-1 : m_KeyboardStates.size()-1;
        
    }
    
    private int calcNextFrameIndex()
    {
        return m_CurrentKeyboardStateIndex >= m_KeyboardStates.size()-1 ? 0 : ++m_CurrentKeyboardStateIndex;
        
    }
    
    
    public boolean getKeyDown(KeyCode aKeyCode)
    {
        if (m_KeyboardStates.get(m_CurrentKeyboardStateIndex).getKey(aKeyCode) == KeyState.JustPressed)
            return true;
        
        return false;
        
    }
    
    public boolean getKey(KeyCode aKeyCode)
    {
        if (m_KeyboardStates.get(m_CurrentKeyboardStateIndex).getKey(aKeyCode) == KeyState.Down)
            return true;
        
        return false;
        
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
        //if ()
        
        m_KeyboardStates.get(m_CurrentKeyboardStateIndex).setKey(s_AWTKeyToG2DjKeyMap.get(e.getKeyCode()), KeyState.JustPressed);

        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        m_KeyboardStates.get(m_CurrentKeyboardStateIndex).setKey(s_AWTKeyToG2DjKeyMap.get(e.getKeyCode()), KeyState.JustReleased);
                
    }
    
}
