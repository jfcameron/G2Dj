/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Input;

import java.util.HashMap;

/**
 *
 * @author Joe
 */
public class KeyboardState 
{
    private final HashMap<KeyCode,Boolean> m_Keys = new HashMap<>();
    
    public KeyboardState()
    {
        initKeysHashMap();
        
    }
    
    public boolean getKey(KeyCode aKeyCode)
    {
        return m_Keys.getOrDefault(aKeyCode, Boolean.FALSE);
        
    }
    
    public void setKey(KeyCode aKeyCode, boolean aValue)
    {
        m_Keys.put(aKeyCode, aValue);
        
    }
    
    private void initKeysHashMap()
    {
        m_Keys.clear();
        
        KeyCode[] values = KeyCode.values();
        
        for (int i = 0; i < values.length;i++)
            m_Keys.put(values[i], Boolean.FALSE);
        
    }
    
}
