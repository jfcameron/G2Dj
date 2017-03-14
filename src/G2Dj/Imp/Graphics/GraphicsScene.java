/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Type.Engine.SceneGraph;
import G2Dj.Graphics;
import G2Dj.Type.Engine.Component;
import G2Dj.Type.Engine.Scene;
import G2Dj.Type.Graphics.Camera;
import G2Dj.Type.Graphics.Mesh;

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
    private final ArrayList<WeakReference<Mesh>>   m_Meshes  = new ArrayList<>();
    
    private final Window m_Window = Graphics.getWindow();
    
    //
    // 
    //
    @Override
    public void update() 
    {
        m_Cameras.forEach((currentCamera)->
        {
            currentCamera.get().draw();
            m_Meshes.forEach((currentMesh)->currentMesh.get().draw());
                    
        });
        
        m_Window.draw();
        
    }
    
    //
    // GameObject events
    //
    @Override
    protected void OnComponentAdded(Component aComponent) 
    {
        if (aComponent instanceof Mesh)
        {
            Mesh mesh = (Mesh)aComponent;
            m_Meshes.add(new WeakReference<>(mesh));
        
        }
        else if (aComponent instanceof Camera)
        {
            Camera camera = (Camera)aComponent;
            m_Cameras.add(new WeakReference<>(camera));
            
        }
        
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) 
    {
        if (aComponent instanceof Mesh)
        {        
            Mesh mesh = (Mesh)aComponent;
        
            for(int i = 0, s = m_Meshes.size(); i < s; i++)
                if (m_Meshes.get(i).get() == mesh)
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
