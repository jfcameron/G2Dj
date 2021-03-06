/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Engine;

import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public abstract class SceneGraph
{
    final WeakReference<Scene> m_Scene;
            
    public abstract void fixedUpdate();
    public abstract void update();
    public abstract void draw();
    
    protected SceneGraph(final WeakReference<Scene> aScene){m_Scene = aScene;}
    
    //
    // GameObject events
    //
    protected abstract void OnComponentAdded  (final Component aComponent);
    protected abstract void OnComponentRemoved(final Component aComponent);
    
}
