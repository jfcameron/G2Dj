package Adhoc;

import G2Dj.Debug;
import G2Dj.Engine;
import G2Dj.Graphics;
import G2Dj.Type.Engine.Component;
import G2Dj.Type.Engine.GameObject;
import G2Dj.Type.Engine.Scene;
import G2Dj.Type.Graphics.Camera;
import G2Dj.Type.Graphics.Mesh;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class Test 
{    
    public static void main(String[] args) 
    {
        Engine.init();
        {
            //Load a texture
            Graphics.loadFromResource("/Adhoc/Water.png");
            
            //Create a scene
            WeakReference<Scene> mainScene = Engine.createScene("Main");
            
            //Create a game object
            WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
            
            Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
            
            
            Camera aCamera;
            try{
            aCamera= (Camera)(Component)aMesh;
            }
            catch (java.lang.ClassCastException e) //it works!
            {
                
                
            }
            
            aMesh.setTexture("_Texture", "Water.png"); //give the mesh a texture
        
        }
        
        Engine.update();
        
    }
    
}
