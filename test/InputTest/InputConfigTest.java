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
    private InputConfig m_Player1Controls;
    private InputConfig m_Player2Controls;
    
    private final String s_Up    = "Up";
    private final String s_Down  = "Down";
    private final String s_Left  = "Left";
    private final String s_Right = "Right";
    
    //
    @Override
    protected void initialize() 
    { 
        m_Player1Controls = new InputConfig
        (
            new InputEvent(s_Up   ),
            new InputEvent(s_Down ),
            new InputEvent(s_Left ),
            new InputEvent(s_Right)
                
        );
        
        m_Player1Controls.addKeysToAEvent(s_Up, KeyCode.W);
        m_Player1Controls.addGamepadButtonsToAEvent(s_Up,"Button 1");
        m_Player1Controls.addGamepadHatToAEvent(s_Up,"Hat Switch",Hat.Direction.Up);
        
        m_Player2Controls = new InputConfig
        (
            new InputEvent(s_Up   ),
            new InputEvent(s_Down ),
            new InputEvent(s_Left ),
            new InputEvent(s_Right)
                
        );
        
        m_Player2Controls.addKeysToAEvent(s_Up, KeyCode.I);
        m_Player2Controls.addGamepadButtonsToAEvent(s_Up,"Button 1");
        m_Player2Controls.addGamepadHatToAEvent(s_Up,"Hat Switch",Hat.Direction.Up);
                
    }

    @Override
    protected void update() 
    {
        if (m_Player1Controls.getDown(s_Up))
            Debug.log("P1: "+s_Up);
        
        if (m_Player2Controls.getDown(s_Up))
            Debug.log("P2: "+s_Up);
        
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
