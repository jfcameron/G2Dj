/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Imp.Graphics.CameraProjectionMode;
import grimhaus.com.G2Dj.Imp.Graphics.Color;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.GameObject.Transform;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class PlayerCamera extends Component
{
    WeakReference<Transform> m_Player = null;
    private Camera m_Camera = null;
    private float m_CameraSize = 7.5f;

    @Override
    protected void initialize() 
    {
        m_Camera = (Camera)getGameObject().get().addComponent(Camera.class);
        m_Camera.setClearColor(Color.Black());
        m_Camera.setProjectionMode(CameraProjectionMode.Orthographic);
        m_Camera.setFarClippingPlane(15);
        
        m_Camera.setOrthoSize(m_CameraSize, m_CameraSize);
        
        
        m_Player = getGameObject().get().getScene().get().getGameObject("Player").get().getTransform();
        
    }

    @Override
    protected void update() 
    {
        Vector3 pos = getTransform().get().getPosition();
        
        pos.x = m_Player.get().getPosition().x;
        pos.z = m_CameraSize/2;
        
    }

    @Override
    protected void fixedUpdate() 
    {
        
        
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {
    }

    @Override
    protected void OnRemovedFromGameObject() {
    }

    @Override
    protected void OnComponentAdded(Component aComponent) {
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {
    }
    
}
