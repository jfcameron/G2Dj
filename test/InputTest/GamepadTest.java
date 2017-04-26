/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package InputTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Input.Gamepad;
import grimhaus.com.G2Dj.Imp.Input.Gamepad.Button;
import grimhaus.com.G2Dj.Imp.Input.Gamepad.Hat;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import java.lang.ref.WeakReference;
import grimhaus.com.G2Dj.Type.Engine.Component;

/**
 *
 * @author Joseph Cameron
 */
public class GamepadTest extends Component 
{
    private Gamepad m_Gamepad      = null;
    private Hat     m_DirectionHat = null;
    private Button  m_ActionButton = null;
    
 
    //
    @Override
    protected void initialize() 
    { 
        Gamepad[] gamepads = Input.getGamepads();
        
        if (gamepads.length <= 0)
            return;
        
        m_Gamepad = gamepads[0]; 
        Debug.log(m_Gamepad);
        
        m_ActionButton = m_Gamepad.getButton("Button 0");
        m_DirectionHat = m_Gamepad.getHat("Hat Switch");
        
    }

    @Override
    protected void update() 
    {
        if (m_ActionButton.getDown())
            Debug.log("Button getdown");
        
        //if (m_DirectionHat.get(Hat.HatDirection.Down))
        //    Debug.log("Hat get");
        
        if (m_DirectionHat.getDown(Hat.Direction.Down))
            Debug.log("Hat getDown");
       
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
