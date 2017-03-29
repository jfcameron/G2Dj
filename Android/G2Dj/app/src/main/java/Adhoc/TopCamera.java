/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Adhoc;

import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.GameObject.Transform;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class TopCamera extends Component
{
    private WeakReference<Transform> m_PlayerTransform = null;
    private WeakReference<Transform> m_Tranform;

    private final Vector3 m_Position = new Vector3();
    
    @Override
    public void update() 
    {
        m_Position.setInPlace(m_PlayerTransform.get().getPosition());
        
        m_Tranform.get().setPosition(m_Position.x,m_Position.y+10,m_Position.z);
    
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        m_PlayerTransform = getGameObject().get().getScene().get().getGameObject("PlayerCamera").get().getTransform();
        m_Tranform = getGameObject().get().getTransform();
        
    }

    @Override
    protected void OnRemovedFromGameObject() {}

    @Override
    protected void OnComponentAdded(Component aComponent) {   }

    @Override
    protected void OnComponentRemoved(Component aComponent) {    }

    @Override
    protected void initialize() {
    }
    
}
