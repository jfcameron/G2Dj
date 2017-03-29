package Adhoc;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.CameraClearMode;
import grimhaus.com.G2Dj.Imp.Physics2D.BodyType;
import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.BoxCollider;
import grimhaus.com.G2Dj.Type.Physics2D.CircleCollider;
import grimhaus.com.G2Dj.Type.Physics2D.CompositeCollider;
import grimhaus.com.G2Dj.Type.Physics2D.PolygonCollider;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
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
            Graphics.loadFromResource("Adhoc/grass.png");
            Graphics.loadFromResource("Adhoc/name.png");
            Graphics.loadFromResource("Adhoc/Sprites.png");

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
                //aPC.getTransform().get().setRotation(0,0,45);
                aPC.getTransform().get().setPosition(0,0,1f);
                
                PolygonCollider pc = (PolygonCollider)aGameObject.get().addComponent(PolygonCollider.class);
                pc.setOffset(0, 0);
                pc.setVerticies(new Vector2[]
                {
                    new Vector2(0,0),
                    new Vector2(1,0),
                    new Vector2(1,2),
                    new Vector2(0,1),                    
                
                });
                
                CircleCollider cc = (CircleCollider)aGameObject.get().addComponent(CircleCollider.class);
                cc.setOffset(2, 0);
                
                Collider c = (Collider)aGameObject.get().addComponent(BoxCollider.class);
                c.setOffset(-2, 0);
                
                c = (Collider)aGameObject.get().addComponent(CompositeCollider.class);
                c.setOffset(0, +2);
                
                Rigidbody rb = (Rigidbody)aGameObject.get().addComponent(Rigidbody.class);
                rb.setType(BodyType.Static);
                 
                aPC.getTransform().get().setScale(7,1,7);
            
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
                CameraController cc = (CameraController)theCamera.get().addComponent(CameraController.class);
                
            }
            
            //Create the TopCamera
            {
                WeakReference<GameObject> gameObject = mainScene.get().addGameObject();
                gameObject.get().setName("TopCamera");
                gameObject.get().getTransform().get().setPosition(-1,5,2);
                gameObject.get().getTransform().get().setRotation(-90,0,0);
                Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
                camera.setViewportPixelSize(new Vector2(0.5f,0.5f));
                camera.setClearMode(CameraClearMode.DepthOnly);
                
                gameObject.get().addComponent(TopCamera.class);

            }
        
        }
        
        
        
        

        //.if DESKTOP
        Engine.mainLoop();//TODO: this should not exist.
        //.endif

    }
    
}
