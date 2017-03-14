/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Engine;

import G2Dj.Imp.Graphics.GraphicsScene;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 *
 * @author Joe
 */
public class Scene
{
    //
    //
    //
    private final String m_Name;
    private final ArrayList<SceneGraph> m_SceneGraphs = new ArrayList<>();
    private final ArrayList<GameObject> m_GameObjects = new ArrayList<>();
    
    //
    // GameObject callbacks
    //
    protected void OnComponentAdded(final Component aComponent)
    {
       for(int i = 0, s = m_SceneGraphs.size(); i < s; i++)
           m_SceneGraphs.get(i).OnComponentAdded(aComponent);
       
    }
    
    protected void OnComponentRemoved(final Component aComponent)
    {
       for(int i = 0, s = m_SceneGraphs.size(); i < s; i++)
           m_SceneGraphs.get(i).OnComponentRemoved(aComponent);
       
    }
    
    //
    // Scenegraph callbacks
    //
    protected void OnSceneGraphAdded(final SceneGraph aSceneGraph)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        
    }
    
    protected void OnSceneGraphRemoved(final SceneGraph aSceneGraphRemoved)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
        
    }
    
    //
    //
    //
    public void update()
    {
        m_SceneGraphs.forEach(currentGraph->currentGraph.update());
        
    }
    
    public WeakReference<GameObject> addGameObject()
    {
        GameObject newGameObject = new GameObject(new WeakReference<>(this));
        
        m_GameObjects.add(newGameObject);
        return new WeakReference<GameObject>(newGameObject);        
        
    }
    /*public void addGameObject(final GameObject aGameObject)
    {
        
        
    }*/
    
    //
    //
    //
    //standard scene constructor
    public Scene(final String aName)
    {
        m_Name = aName;
        m_SceneGraphs.add(new GraphicsScene(new WeakReference<>(this)));
        
        
    }
    
}
