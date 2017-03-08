/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Input;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 *
 * @author Joe
 */
public class KeyboardState 
{
    private final HashMap<KeyCode,KeyState> m_Keys = new HashMap<>();
    
    public KeyboardState()
    {
        initKeysHashMap();
        
       // m_Keys.forEach(action);
        
    }
    
    public void forEach(BiConsumer<KeyCode,KeyState> action)
    {
        m_Keys.forEach(action);
        
    }
    
    
    public KeyState getKey(KeyCode aKeyCode)
    {
        return m_Keys.getOrDefault(aKeyCode, KeyState.Up);
        
    }
    
    public void setKey(KeyCode aKeyCode, KeyState aValue)
    {
        m_Keys.put(aKeyCode, aValue);
        
    }
    
    private void initKeysHashMap()
    {
        m_Keys.clear();
        
        KeyCode[] values = KeyCode.values();
        
        for (int i = 0; i < values.length;i++)
            m_Keys.put(values[i], KeyState.Up);
        
    }
    
}
