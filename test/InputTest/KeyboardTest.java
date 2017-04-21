/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package InputTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class KeyboardTest extends Component 
{
    //
    private KeyCode m_TestKeyCode = KeyCode.Space;
    
    //
    @Override
    protected void initialize() 
    {
        
    
    }

    @Override
    protected void update() 
    {
        if (Input.getKeyDown(m_TestKeyCode))
            Debug.log(m_TestKeyCode+" was just pressed");
        
        if (Input.getKey(m_TestKeyCode))
            Debug.log(m_TestKeyCode+" is being held");
    
    }

    @Override
    protected void fixedUpdate() 
    {
    
    
    }

    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
