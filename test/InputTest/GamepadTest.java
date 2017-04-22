/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package InputTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import java.lang.ref.WeakReference;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;


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
                Debug.log("Gamepad found!");
                
                
            }
            
        }
        
    }

    @Override
    protected void update() 
    {
        
        
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
