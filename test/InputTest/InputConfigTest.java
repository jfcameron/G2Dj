/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package InputTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Input.Gamepad;
import grimhaus.com.G2Dj.Imp.Input.Gamepad.Hat;
import grimhaus.com.G2Dj.Imp.Input.InputConfig;
import grimhaus.com.G2Dj.Imp.Input.InputConfig.InputEvent;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class InputConfigTest extends Component 
{
    private InputConfig m_PlayerControls;
    
    private final String s_Up    = "Up";
    private final String s_Down  = "Down";
    private final String s_Left  = "Left";
    private final String s_Right = "Right";
    
    //
    @Override
    protected void initialize() 
    { 
        m_PlayerControls = new InputConfig
        (
            new InputEvent(s_Up   ),
            new InputEvent(s_Down ),
            new InputEvent(s_Left ),
            new InputEvent(s_Right)
                
        );
        
        m_PlayerControls.addKeysToAEvent(s_Up, KeyCode.W);
        m_PlayerControls.addGamepadButtonsToAEvent(s_Up,"Button 1");
        m_PlayerControls.addGamepadHatToAEvent(s_Up,"Hat Switch",Hat.Direction.Up);
                
    }

    @Override
    protected void update() 
    {
        if (m_PlayerControls.getDown(s_Up))
            Debug.log(s_Up);
        
        if (m_PlayerControls.getDown(s_Down))
            Debug.log(s_Down);
        
        if (m_PlayerControls.getDown(s_Left))
            Debug.log(s_Left);
        
        if (m_PlayerControls.getDown(s_Right))
            Debug.log(s_Right);
        
        
        
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
