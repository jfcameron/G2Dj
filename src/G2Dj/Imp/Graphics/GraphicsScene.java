/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Type.Engine.SceneGraph;
import G2Dj.Graphics;
import G2Dj.Math.Vector2;
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
    //
    //
    public GraphicsScene(WeakReference<Scene> aScene)
    {
        super(aScene);
        
        //TEST AREA
        m_Cameras.add(new WeakReference<>(new Camera(new Vector2(0.0f,0.0f), new Vector2(0.5f,0.5f),Color.CornflowerBlue(),CameraClearMode.Color)));
        m_Cameras.add(new WeakReference<>(new Camera(new Vector2(0.0f,0.5f), new Vector2(0.5f,0.5f),Color.DeathlyPink()   ,CameraClearMode.Color)));
        m_Cameras.add(new WeakReference<>(new Camera(new Vector2(0.5f,0.0f), new Vector2(0.5f,0.5f),Color.Red()           ,CameraClearMode.Color)));
        m_Cameras.add(new WeakReference<>(new Camera(new Vector2(0.5f,0.5f), new Vector2(0.5f,0.5f),Color.Green()         ,CameraClearMode.Color)));
        
        //m_Meshes.add(new Mesh("Quad", "AlphaCutOff"));
        //m_Meshes.get(0).setTexture("_Texture","default.png");
        
        
    }

    //
    // GameObject events
    //
    @Override
    protected void OnComponentAdded(Component aComponent) 
    {
        if (!(aComponent instanceof Mesh))
            return;
        
        Mesh mesh = (Mesh)aComponent;
        
        m_Meshes.add(new WeakReference<>(mesh));
        
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    
    
}
