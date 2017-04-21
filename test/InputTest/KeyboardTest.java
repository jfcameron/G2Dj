/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package InputTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class KeyboardTest extends Component 
{
    //
    private KeyCode m_TestKeyCode = KeyCode.Space;
    private Camera m_SceneCamera = null;
    
    //
    @Override
    protected void initialize() 
    {
        m_SceneCamera = (Camera)getGameObject().get().getScene().get().getGameObject("MainCamera").get().getComponent(Camera.class);
    
    }

    @Override
    protected void update() 
    {
        if (Input.getKeyDown(m_TestKeyCode))
            Debug.log(m_TestKeyCode+" was just pressed");
        
        if (Input.getKey(m_TestKeyCode))
            Debug.log(m_TestKeyCode+" is being held");
        
        if (Input.getKey(KeyCode.LeftControl))
            m_SceneCamera.getTransform().get().translate(Vector3.Down().multiply((float)Time.getDeltaTime()));
        
        if (Input.getKey(KeyCode.Space))
            m_SceneCamera.getTransform().get().translate(Vector3.Up().multiply((float)Time.getDeltaTime()));
        
        if (Input.getKey(KeyCode.A))
            m_SceneCamera.getTransform().get().translate(Vector3.Left().multiply((float)Time.getDeltaTime()));
        
        if (Input.getKey(KeyCode.D))
            m_SceneCamera.getTransform().get().translate(Vector3.Right().multiply((float)Time.getDeltaTime()));
        
        if (Input.getKey(KeyCode.W))
            m_SceneCamera.getTransform().get().translate(Vector3.Backward().multiply((float)Time.getDeltaTime()));
        
        if (Input.getKey(KeyCode.S))
            m_SceneCamera.getTransform().get().translate(Vector3.Forward().multiply((float)Time.getDeltaTime()));
        
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
