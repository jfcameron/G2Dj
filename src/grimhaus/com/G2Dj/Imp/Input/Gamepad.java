/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Input;

import grimhaus.com.G2Dj.Debug;
import java.util.ArrayList;
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
  //  private final Component[] m_Axes;
  //  private final Component[] m_Hats;
  //  private final Component[] m_Buttons;
    
    //
    //
    //
    public boolean getButton(final String aButtonName)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        
    }
    
    public boolean getButtonDown(final String aButtonName)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        
    }
    
    //
    //
    //
    public Gamepad(final net.java.games.input.Controller aJInputControllerHandle)
    {
        Component[] components = aJInputControllerHandle.getComponents();
        ArrayList<Component> axes    = new ArrayList<>();
        ArrayList<Component> hats    = new ArrayList<>();
        ArrayList<Component> buttons = new ArrayList<>();
        
        Debug.log("**********Gamepad******************");
        
        for(int i=0,s=components.length;i<s;i++)
            if (components[i].getIdentifier() instanceof Identifier.Button)
                buttons.add(components[i]);
            else if (components[i].getIdentifier() == Identifier.Axis.POV)
            {
                Debug.log("SUCCESS @ "+components[i].getName());
                
            }   
                
            
        
    }
    
}
