/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Input;

import grimhaus.com.G2Dj.Debug;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

/**
 *
 * @author Joseph Cameron
 */
public class Gamepad 
{    
    //
    //
    //
    private final String m_Name;
    
    private final HashMap<String,Component> m_Axes    = new HashMap<>();
    private final HashMap<String,Component> m_Hats    = new HashMap<>();
    private final HashMap<String,Component> m_Buttons = new HashMap<>();
    
    //
    //
    //
    public String getName(){return m_Name;}
    
    public final String[] getAxisNames()
    {
        String[] rvalue = new String[m_Axes.size()];
        
        int i =0;
        for(Map.Entry<String,Component> entry : m_Axes.entrySet())
            rvalue[i++] = entry.getKey();
        
        return rvalue;
        
    }
    
    public final String[] getHatNames()
    {
        String[] rvalue = new String[m_Hats.size()];
        
        int i =0;
        for(Map.Entry<String,Component> entry : m_Hats.entrySet())
            rvalue[i++] = entry.getKey();
        
        return rvalue;
        
    }
    
    public final String[] getButtonNames()
    {
        String[] rvalue = new String[m_Buttons.size()];
        
        int i =0;
        for(Map.Entry<String,Component> entry : m_Buttons.entrySet())
            rvalue[i++] = entry.getKey();
        
        return rvalue;
        
    }
    
    public boolean getButton(final String aButtonName)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        
    }
    
    public boolean getButtonDown(final String aButtonName)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        
    }
    
    public float getAxis(final String aAxisName)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        
    }
    
    //public HatEnum getHat(){}
    
    //
    //
    //
    public Gamepad(final net.java.games.input.Controller aJInputControllerHandle)
    {
        m_Name = aJInputControllerHandle.getName();
        
        Component[] components = aJInputControllerHandle.getComponents();        
        Component currentComponent;
        
        for(int i=0,s=components.length;i<s;i++)
        {
            currentComponent = components[i];
            
            if (currentComponent.getIdentifier() instanceof Identifier.Button)
                m_Buttons.put(currentComponent.getName(),currentComponent);
            else if (currentComponent.getIdentifier() instanceof Identifier.Axis)
            {
                if (currentComponent.getIdentifier() == Identifier.Axis.POV)
                    m_Hats.put(currentComponent.getName(),currentComponent);
                else
                    m_Axes.put(currentComponent.getName(),currentComponent);
                                
            }
                
        }
        
    }
    
    //
    // inner classes
    //
    private class Button
    {
        
        
    }
    
    private class Axis
    {
        
        
    }
    
    private class Hat
    {
        
        
    }
    
}
