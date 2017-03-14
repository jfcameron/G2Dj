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
public abstract class Component 
{
    //
    //
    //
    private WeakReference<GameObject> m_GameObject;
    
    //
    //
    //
    public WeakReference<GameObject> get(){return m_GameObject;}
    
    //
    //
    //
    public abstract void update();
    
    //
    // GameObject events
    //
    protected final void OnAddedToGameObjectSuper(final GameObject aGameObject){m_GameObject = new WeakReference<>(aGameObject);}
    protected final void OnRemovedFromGameObjectSuper(){m_GameObject = null;}
    
    protected abstract void OnAddedToGameObject    (final GameObject aGameObject);
    protected abstract void OnRemovedFromGameObject();
    
}
