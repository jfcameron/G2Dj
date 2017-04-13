/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class BackgroundQuad extends Component
{
    private Mesh m_Mesh = null;
    private final Vector2 m_UVOffset = new Vector2();

    @Override protected void initialize() 
    {
        m_Mesh = (Mesh)getGameObject().get().getComponent(Mesh.class);
    
    }

    @Override protected void update() 
    {
        m_UVOffset.x = -1.0f*(float)Time.getCurrentTime();
        m_UVOffset.y =  1.0f*(float)Time.getCurrentTime();
        
        m_Mesh.setVector2("_UVOffset", m_UVOffset);
    
    }

    @Override protected void fixedUpdate() {}

    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}

    @Override protected void OnRemovedFromGameObject() {}

    @Override protected void OnComponentAdded(Component aComponent) {}

    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
