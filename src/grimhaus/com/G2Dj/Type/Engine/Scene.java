/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Engine;

import grimhaus.com.G2Dj.Imp.Engine.RequireSceneGraphs;
import grimhaus.com.G2Dj.Imp.Engine.SceneEventCallbacks;
import grimhaus.com.G2Dj.Imp.Engine.SceneState;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    //private final boolean m
    private SceneState m_SceneState = SceneState.Active;
        
    //
    //
    //
    public String getName(){return m_Name;}

    public SceneState getSceneState(){return m_SceneState;}
    public void setSceneState(final SceneState aSceneState){m_SceneState=aSceneState;}
    
    public WeakReference<SceneGraph> getSceneGraph(Class<? extends SceneGraph> aSceneGraphType)
    {
        for(int i=0,s=m_SceneGraphs.size();i<s;i++)
            if(m_SceneGraphs.get(i).getClass() == aSceneGraphType)
                return new WeakReference<>(m_SceneGraphs.get(i));
        
        return addSceneGraph(aSceneGraphType);
        
    }
    
    public WeakReference<SceneGraph> addSceneGraph(Class<? extends SceneGraph> aSceneGraphType)
    {
        for(int i=0,s=m_SceneGraphs.size();i<s;i++) //Check if a graph of the same type already exists
            if(m_SceneGraphs.get(i).getClass() == aSceneGraphType)
                return new WeakReference<>(m_SceneGraphs.get(i)); //if yes, return this copy
        
        WeakReference<SceneGraph> rvalue = null;
        
        try //Otherwise add an instance of aSceneGraphType to the list and return a weak reference to it
        {
            Class[] args = new Class[]{WeakReference.class/*,SceneEventCallbacks.class*/};
            
            rvalue = new WeakReference<>(aSceneGraphType.getDeclaredConstructor(args).newInstance(new WeakReference<>(this)/*,new SceneEventCallbacks()*/));
            m_SceneGraphs.add(rvalue.get());
            return rvalue;
            
        } 
        catch (InstantiationException ex) {Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);} 
        catch (IllegalAccessException ex) {Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);}
        catch (SecurityException ex) {Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);}
        catch (NoSuchMethodException ex) {Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);} 
        catch (InvocationTargetException ex) {Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);}
        catch (IllegalArgumentException ex) {Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);} 
        
        return rvalue;
        
    }
    
    public WeakReference<GameObject> getGameObject(final String aGameObjectName)
    {
        for(int i=0,s=m_GameObjects.size();i<s;i++)
            if (m_GameObjects.get(i).getName().equals(aGameObjectName))
                return new WeakReference<>(m_GameObjects.get(i));
        
        return new WeakReference<>(null);
        
    }
    
    private void parseRequireSceneGraphsAnnotation(final Component aComponent)
    {
        RequireSceneGraphs rcg = aComponent.getClass().getAnnotation(RequireSceneGraphs.class);
        
        if (rcg == null) //If the component does not require any special scenegraphs, bail now
            return;
        
        for(int i=0,s=rcg.value().length;i<s;i++) //otherwise iterate required graphs...
            if (getSceneGraph(rcg.value()[i]) == null)//and if the required graph does not exist in this scene, add it
                addSceneGraph(rcg.value()[i]);
            
    }
    
    //
    // GameObject callbacks
    //
    protected void OnComponentAdded(final Component aComponent)
    {
       parseRequireSceneGraphsAnnotation(aComponent); 
        
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
        switch(m_SceneState)
        {
            case Active:
            {
                for(int i=0,s=m_GameObjects.size();i<s;i++)
                    m_GameObjects.get(i).update();

                for(int i=0,s=m_SceneGraphs.size();i<s;i++)
                    m_SceneGraphs.get(i).update();
                
            } break;
            
            case Paused: 
            default:
            break;
                
        }

    }
    
    public void fixedUpdate()
    {
        switch(m_SceneState)
        {
            case Active:
            {
                for(int i=0,s=m_GameObjects.size();i<s;i++)
                    m_GameObjects.get(i).fixedUpdate();

                for(int i=0,s=m_SceneGraphs.size();i<s;i++)
                    m_SceneGraphs.get(i).fixedUpdate();
        
            } break;
            
            case Paused: 
            default:
            break;
            
        }
        
    }
    
    public void draw()
    {
        for(int i=0,s=m_SceneGraphs.size();i<s;i++)
            m_SceneGraphs.get(i).draw();

    }
    
    public WeakReference<GameObject> addGameObject()
    {
        GameObject newGameObject = new GameObject(new WeakReference<>(this));
        
        m_GameObjects.add(newGameObject);
        return new WeakReference<>(newGameObject);        
        
    }
    
    protected void OnCollisionEnter()
    {
        
        
    }
    
    //*************
    // Constructors
    //*************
    public Scene(final String aName)
    {
        m_Name = aName;
        
        SceneEventCallbacks onCollisionEnterFunctionPointer = new SceneEventCallbacks()
        {
            @Override public void onCollisionEnter(){OnCollisionEnter();}
            
        };
        
    }
    
}
