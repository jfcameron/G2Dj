/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Type.Engine.SceneGraph;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.Scene;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 *
 * @author Joe
 */
public class GraphicsScene extends SceneGraph
{
    //*************
    // Data members
    //*************
    private final ArrayList<WeakReference<Camera>> m_Cameras = new ArrayList<>();//make use of these
    private final ArrayList<WeakReference<Drawable>>   m_Meshes  = new ArrayList<>();
    
    //
    // 
    //
    @Override
    public void update(){}
    
    @Override
    public void draw() 
    {
        for(int i=0,s=m_Cameras.size();i<s;i++)
        {
            if (m_Cameras.get(i) != null)
                m_Cameras.get(i).get().draw();
            else
                m_Cameras.remove(i);
            
            for(int j=0,t=m_Meshes.size();j<t;j++)
                if (m_Meshes.get(j) != null)
                    m_Meshes.get(j).get().draw(m_Cameras.get(i));
                else
                    m_Meshes.remove(j);

        }
                
    }
    
    //
    // GameObject events
    //
    @Override
    protected void OnComponentAdded(Component aComponent) 
    {
        if (aComponent instanceof Drawable)
            m_Meshes.add(new WeakReference<>((Drawable)aComponent));

        else if (aComponent instanceof Camera)
            m_Cameras.add(new WeakReference<>((Camera)aComponent));
        
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) 
    {
        if (aComponent instanceof Drawable  /*instanceof Mesh*/)
        {
            Drawable drawable = (Drawable)aComponent;
            
            for(int i = 0, s = m_Meshes.size(); i < s; i++)
                if (m_Meshes.get(i).get() == drawable)
                {
                    m_Meshes.remove(i);
                    return;
                    
                }
            
        }
        else if (aComponent instanceof Camera)
        {
            Camera camera = (Camera)aComponent;
            
            for(int i = 0, s = m_Meshes.size(); i < s; i++)
                if (m_Cameras.get(i).get() == camera)
                {
                    m_Cameras.remove(i);
                    return;
                
                }
            
        }
        
    }

    //************
    // Constructor
    //************
    public GraphicsScene(WeakReference<Scene> aScene){super(aScene);}
    
}
