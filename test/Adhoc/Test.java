package Adhoc;

import G2Dj.Engine;
import G2Dj.Graphics;
import G2Dj.Math.Vector2;
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
            
            //Create a the player game object
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Player1");
            
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "awesome.png"); //give the mesh a texture
                
                //aGameObject.get().removeComponent(Mesh.class);
            
            }
            
            //Create the camera
            {
                WeakReference<GameObject> theCamera = mainScene.get().addGameObject();
                theCamera.get().setName("Camera");
            
                Camera aCamera = (Camera)theCamera.get().addComponent(Camera.class);
                //aCamera.setViewportPixelSize(new Vector2(0.5f,0.5f));
            
            }
            
            /*//Create the camera
            {
                WeakReference<GameObject> theCamera = mainScene.get().addGameObject();
                theCamera.get().setName("Camera");
            
                Camera aCamera = (Camera)theCamera.get().addComponent(Camera.class);
                aCamera.setViewportPixelSize(new Vector2(0.5f,0.5f));
                aCamera.setViewportPixelPosition(new Vector2(0.5f,0.5f));
            
            }*/
            
            
        }
        
        Engine.update();
        
    }
    
}
