/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Engine;

import grimhaus.com.G2Dj.Type.Graphics.GraphicsScene;
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
    //
    //
    public String getName(){return m_Name;}
    
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
        m_GameObjects.forEach(i->i.update());
        m_SceneGraphs.forEach(i->i.update());
        
    }
    
    public WeakReference<GameObject> addGameObject()
    {
        GameObject newGameObject = new GameObject(new WeakReference<>(this));
        
        m_GameObjects.add(newGameObject);
        return new WeakReference<>(newGameObject);        
        
    }
    
    //*************
    // Constructors
    //*************
    public Scene(final String aName, SceneGraph[] aSceneGraphs)
    {
        m_Name = aName;
        
    }
    public Scene(final String aName)
    {
        m_Name = aName;
        m_SceneGraphs.add(new GraphicsScene(new WeakReference<>(this)));
        
    }
    
}
