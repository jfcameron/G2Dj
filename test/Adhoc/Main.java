package Adhoc;

import G2Dj.Engine;
import G2Dj.Graphics;
import G2Dj.Type.Engine.GameObject;
import G2Dj.Type.Engine.Scene;
import G2Dj.Type.Graphics.Camera;
import G2Dj.Type.Graphics.Mesh;
import G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class Main 
{    
    public static void main(String[] args) 
    {
        Engine.init();
        {
            //Load a texture
            Graphics.loadFromResource("/Adhoc/Cloud.png");
            Graphics.loadFromResource("/Adhoc/brick.png");
            Graphics.loadFromResource("/Adhoc/grass.png");
            Graphics.loadFromResource("/Adhoc/name.png");
            
            //Create a scene
            WeakReference<Scene> mainScene = Engine.createScene("Main");
            
            //Create a the player game object
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Player1");
            
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "awesome.png"); //give the mesh a texture
                
                //aGameObject.get().removeComponent(Mesh.class);
                
                PlayerController aPC = (PlayerController)aGameObject.get().addComponent(PlayerController.class);
                aPC.getTransform().get().setRotation(new Vector3(0,0,90));
            
            }
            
            //Create the wall
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Wall");
                aGameObject.get().getTransform().get().setPosition(new Vector3(0,-1f,0));
                aGameObject.get().getTransform().get().setScale   (new Vector3(10,4,0));
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "brick.png"); //give the mesh a texture
                
            }
            
            //Create the wall
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Wall");
                aGameObject.get().getTransform().get().setPosition(new Vector3(0,-0.5f,+2.4f));
                aGameObject.get().getTransform().get().setScale   (new Vector3(10,2,0));
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "brick.png"); //give the mesh a texture
                
            }
            
            //Create the floor
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Floor");
                aGameObject.get().getTransform().get().setPosition(new Vector3(0,-0.5f,0));
                aGameObject.get().getTransform().get().setRotation(new Vector3(90,0,0));
                aGameObject.get().getTransform().get().setScale   (new Vector3(10,5,0));
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "grass.png"); //give the mesh a texture
                
            }
            
            //Create the clouds
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Floor");
                aGameObject.get().getTransform().get().setPosition(new Vector3(0,+0.5f,0));
                aGameObject.get().getTransform().get().setRotation(new Vector3(90,0,0));
                aGameObject.get().getTransform().get().setScale   (new Vector3(10,5,0));
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "Cloud.png"); //give the mesh a texture
                
            }
            
            //Create the camera
            {
                WeakReference<GameObject> theCamera = mainScene.get().addGameObject();
                theCamera.get().setName("Camera");
                theCamera.get().getTransform().get().setPosition(new Vector3(0,0,3));
                theCamera.get().getTransform().get().setRotation(new Vector3(0,45,0));
                theCamera.get().addComponent(CameraController.class);
                Camera aCamera = (Camera)theCamera.get().addComponent(Camera.class);
                
            
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
