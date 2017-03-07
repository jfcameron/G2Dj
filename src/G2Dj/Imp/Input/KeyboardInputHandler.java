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
