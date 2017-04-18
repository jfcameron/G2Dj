/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.GameObject.Transform;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class PlayerCamera extends Component
{
    WeakReference<Transform> m_Player = null;

    @Override
    protected void initialize() 
    {
        m_Player = getGameObject().get().getScene().get().getGameObject("Player").get().getTransform();
        
    }

    @Override
    protected void update() 
    {
        Vector3 pos = getTransform().get().getPosition();
        
        pos.x = m_Player.get().getPosition().x;
        //pos.z = m_Player.get().getPosition().z;
        
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
