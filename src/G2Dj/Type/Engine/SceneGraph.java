/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Engine;

import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public abstract class SceneGraph
{
    final WeakReference<Scene> m_Scene;
    
    public abstract void update();
    
    protected SceneGraph(final WeakReference<Scene> aScene){m_Scene = aScene;}
    
    //
    // GameObject events
    //
    protected abstract void OnComponentAdded  (final Component aComponent);
    protected abstract void OnComponentRemoved(final Component aComponent);
    
}
