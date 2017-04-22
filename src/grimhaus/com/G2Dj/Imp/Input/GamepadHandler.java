/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Input;

import java.util.ArrayList;
import java.util.Arrays;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 *
 * @author Joseph Cameron
 */
public final class GamepadHandler /*implements ControllerListener*/ //not supported by jInput unfortunately
{
    //
    // Data members
    //
    private final ArrayList<Gamepad> m_GamePads = new ArrayList<>();
    private final net.java.games.input.Controller[] m_JInputControllerHandles;
    
    //
    // Public interface
    //
    public Gamepad[] getGamepads(){return m_GamePads.toArray(new Gamepad[m_GamePads.size()]);}
    
    public void update()
    {
        for (int i=0,s=m_JInputControllerHandles.length;i<s;i++)
            m_JInputControllerHandles[i].poll();
        
    }
    
    //
    // Implementation
    //
    
    
    //
    // Constructors
    //
    public GamepadHandler()
    {
        ArrayList<Controller> controllers = new ArrayList<Controller>(Arrays.asList(ControllerEnvironment.getDefaultEnvironment().getControllers()));
        Controller current;
        
        for (int i=0;i<controllers.size();i++)
        {
            current = controllers.get(i);
            
            if (current.getType() == Controller.Type.GAMEPAD || current.getType() == Controller.Type.STICK)
                m_GamePads.add(new Gamepad(current));
            else
                controllers.remove(i--);
            
        }
        
        m_JInputControllerHandles = controllers.toArray(new net.java.games.input.Controller[controllers.size()]);
        
    }
    
}
