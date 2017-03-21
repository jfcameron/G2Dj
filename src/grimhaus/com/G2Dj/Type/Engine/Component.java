/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Engine;

import grimhaus.com.G2Dj.Imp.Engine.Transform;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public abstract class Component 
{
    //
    //
    //
    private WeakReference<GameObject> m_GameObject;
    private WeakReference<Transform>  m_Transform;
    
    //
    //
    //
    public WeakReference<GameObject> getGameObject(){return m_GameObject;}
    public WeakReference<Transform> getTransform() {return m_Transform;}
    
    //
    //
    //
    public abstract void update();
    
    //
    // GameObject events
    //
    protected final void OnAddedToGameObjectSuper(final WeakReference<GameObject> aGameObject)
    {
        m_GameObject = aGameObject;
        m_Transform  = aGameObject.get().getTransform();
    
    }
    
    protected final void OnRemovedFromGameObjectSuper(){m_GameObject = null;}
    
    //Whenever I am added or removed
    protected abstract void OnAddedToGameObject    (final WeakReference<GameObject> aGameObject);
    protected abstract void OnRemovedFromGameObject();
    
    //Whenever any component is added or removed
    protected abstract void OnComponentAdded(final Component aComponent);
    protected abstract void OnComponentRemoved(final Component aComponent);
    
}
