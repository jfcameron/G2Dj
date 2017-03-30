/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Engine;

import grimhaus.com.G2Dj.Type.Graphics.GraphicsScene;
import grimhaus.com.G2Dj.Type.Physics2D.Physics2DScene;
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

    public SceneGraph getSceneGraph(Class<? extends SceneGraph> aSceneGraphType)
    {
        SceneGraph rValue = null;
        
        for(int i=0,s=m_SceneGraphs.size();i<s;i++)
            if(m_SceneGraphs.get(i).getClass() == aSceneGraphType)
            {
                rValue = m_SceneGraphs.get(i);
                break;
                
            }
        
        return rValue;
        
    }
    
    public WeakReference<GameObject> getGameObject(final String aGameObjectName)
    {
        for(int i=0,s=m_GameObjects.size();i<s;i++)
            if (m_GameObjects.get(i).getName().equals(aGameObjectName))
                return new WeakReference<>(m_GameObjects.get(i));
        
        return new WeakReference<>(null);
        
    }
    
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
        for(int i=0,s=m_GameObjects.size();i<s;i++)//m_GameObjects.forEach(i->i.update());
            m_GameObjects.get(i).update();

        for(int i=0,s=m_SceneGraphs.size();i<s;i++)//m_SceneGraphs.forEach(i->i.update());
            m_SceneGraphs.get(i).update();

    }
    
    public void fixedUpdate()
    {
        for(int i=0,s=m_GameObjects.size();i<s;i++)//m_GameObjects.forEach(i->i.update());
            m_GameObjects.get(i).fixedUpdate();

        for(int i=0,s=m_SceneGraphs.size();i<s;i++)//m_SceneGraphs.forEach(i->i.update());
            m_SceneGraphs.get(i).fixedUpdate();
        
    }
    
    public void draw()
    {
        for(int i=0,s=m_SceneGraphs.size();i<s;i++)//m_SceneGraphs.forEach(i->i.update());
            m_SceneGraphs.get(i).draw();

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
        m_SceneGraphs.add(new GraphicsScene (new WeakReference<>(this)));
        m_SceneGraphs.add(new Physics2DScene(new WeakReference<>(this)));
        
    }
    
}
