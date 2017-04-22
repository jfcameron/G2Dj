/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package InputTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import java.lang.ref.WeakReference;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import grimhaus.com.G2Dj.Type.Engine.Component;

/**
 *
 * @author Joseph Cameron
 */
public class GamepadTest extends Component 
{
    //
    // First you need to create controller.
    //private JInputJoystick joystick;// = new JInputJoystick(Controller.Type.STICK, Controller.Type.GAMEPAD);
    Controller[] ca;

    Controller m_ControllerHandle = null;
    net.java.games.input.Component button1 = null;
    net.java.games.input.Component axis1 = null;
    net.java.games.input.Component hat1 = null;
    
    //
    @Override
    protected void initialize() 
    {
        ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        Controller current = null; 
                    
        for(int i=0,s=ca.length;i<s;i++)
        {
            current = ca[i];
            
            Debug.log(current.getType(),current.getName());
            
            if (current.getType() == Type.GAMEPAD || current.getType() == Type.STICK)
            {
                //Debug.log("Gamepad found!");
                
                net.java.games.input.Component[] components = current.getComponents();
                
                for(int j=0,t=components.length;j<t;j++)
                    Debug.log(components[j].getName(),components[j].getIdentifier());
                
                m_ControllerHandle = current;
                button1 = components[5];
                axis1   = components[3];
                hat1    = components[4];
                
            }
            
        }
        
    }

    boolean asdf = true;
    @Override
    protected void update() 
    {
        Debug.log(button1.getName(),hat1.getPollData());
       
    }

    @Override
    protected void fixedUpdate() 
    {
    
    
    }

    //
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}

}
