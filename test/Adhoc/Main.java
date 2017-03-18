package Adhoc;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
import grimhaus.com.G2Dj.Type.Math.Vector2;
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
            Graphics.loadFromResource("/Adhoc/Sprites.png");
            
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
                aPC.getTransform().get().setRotation(0,0,45);
                aPC.getTransform().get().setPosition(0,0,0.5f);
            
            }
            
            //Create the BackWall
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("BackWall");
                aGameObject.get().getTransform().get().setPosition(0,-1f,0);
                aGameObject.get().getTransform().get().setScale   (10,4,0);
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "brick.png"); //give the mesh a texture
                
            }
            
            //Create the Sprites
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Sprites");
                aGameObject.get().getTransform().get().setPosition(0,0.0f,0.1f);
                aGameObject.get().getTransform().get().setScale   (10,1f,0);
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "Sprites.png"); //give the mesh a texture
                
            }
            
            //Create the other Sprites
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Sprites");
                aGameObject.get().getTransform().get().setPosition(2,0.0f,0.75f);
                aGameObject.get().getTransform().get().setScale   (10,1f,0);
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "Sprites.png"); //give the mesh a texture
                
            }
            
            //Create the close wall
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("CloseWall");
                aGameObject.get().getTransform().get().setPosition(0,-0.5f,+2.4f);
                aGameObject.get().getTransform().get().setScale   (10,2,0);
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "brick.png"); //give the mesh a texture
                
            }
            
            //Create the name
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Name");
                aGameObject.get().getTransform().get().setPosition(0,1f,0.001f);
                aGameObject.get().getTransform().get().setScale   (10,10.5f,0);
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "name.png"); //give the mesh a texture
                
            }
            
            //Create the floor
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Floor");
                aGameObject.get().getTransform().get().setPosition(0,-0.5f,0);
                aGameObject.get().getTransform().get().setRotation(90,0,0);
                aGameObject.get().getTransform().get().setScale   (10,5,0);
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "grass.png"); //give the mesh a texture
                
            }
            
            //Create the clouds
            {
                WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
                aGameObject.get().setName("Floor");
                aGameObject.get().getTransform().get().setPosition(0,+0.5f,0);
                aGameObject.get().getTransform().get().setRotation(90,0,0);
                aGameObject.get().getTransform().get().setScale   (10,5,0);
                
                Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
                aMesh.setTexture("_Texture", "Cloud.png"); //give the mesh a texture
                
            }
            
            //Create the camera
            {
                WeakReference<GameObject> theCamera = mainScene.get().addGameObject();
                theCamera.get().setName("Camera");
                theCamera.get().getTransform().get().setPosition(-1,0,2);
                theCamera.get().getTransform().get().setRotation(0,45,0);
                theCamera.get().addComponent(CameraController.class);
                
                Camera aCamera = (Camera)theCamera.get().addComponent(Camera.class);
                aCamera.setViewportPixelSize(new Vector2(0.499f,1.0f));
            
            }
            
            //Create the second camera
            {
                WeakReference<GameObject> theCamera = mainScene.get().addGameObject();
                theCamera.get().setName("Camera");
                theCamera.get().getTransform().get().setPosition(+1.5f,0,2);
                theCamera.get().getTransform().get().setRotation(0,-45,0);
                //theCamera.get().addComponent(CameraController.class);
                
                Camera aCamera = (Camera)theCamera.get().addComponent(Camera.class);
                aCamera.setViewportPixelSize(new Vector2(0.499f,1.0f));
                aCamera.setViewportPixelPosition(new Vector2(0.501f,0.0f));
            
            }            
            
        }
        
        Engine.mainLoop();
        
    }
    
}
