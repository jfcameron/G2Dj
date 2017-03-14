/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;
import G2Dj.Dev.SceneGraph;
import G2Dj.Graphics;
import G2Dj.Math.Vector2;
import G2Dj.Type.Graphics.Camera;
import G2Dj.Type.Graphics.Mesh;
import java.util.ArrayList;

/**
 *
 * @author Joe
 */
public class GraphicsScene implements SceneGraph
{
    //*************
    // Data members
    //*************
    private final ArrayList<Camera> m_Cameras = new ArrayList<>();
    private final ArrayList<Mesh>   m_Meshes  = new ArrayList<>();
    
    private Window m_Window = Graphics.getWindow();
    
    //
    // 
    //
    @Override
    public void update() 
    {
        m_Cameras.forEach((currentCamera)->
        {
            currentCamera.draw();
            
            m_Meshes.forEach((currentMesh)->currentMesh.draw());
                    
        });
        
        m_Window.draw();
        
    }
    
    //
    //
    //
    public GraphicsScene()
    {
        //TEST AREA
        m_Cameras.add(new Camera(new Vector2(0.0f,0.0f), new Vector2(0.5f,0.5f),Color.CornflowerBlue(),CameraClearMode.Color));
        m_Cameras.add(new Camera(new Vector2(0.0f,0.5f), new Vector2(0.5f,0.5f),Color.DeathlyPink()   ,CameraClearMode.Color));
        m_Cameras.add(new Camera(new Vector2(0.5f,0.0f), new Vector2(0.5f,0.5f),Color.Red()           ,CameraClearMode.Color));
        m_Cameras.add(new Camera(new Vector2(0.5f,0.5f), new Vector2(0.5f,0.5f),Color.Green()         ,CameraClearMode.Color));
        
        m_Meshes.add(new Mesh("Quad", "AlphaCutOff"));
        
        
    }
    
    
}
