package Adhoc;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.CameraClearMode;
import static grimhaus.com.G2Dj.Imp.Graphics.CameraClearMode.Color;
import grimhaus.com.G2Dj.Imp.Graphics.CameraProjectionMode;
import grimhaus.com.G2Dj.Imp.Graphics.Color;
import grimhaus.com.G2Dj.Imp.Physics2D.BodyType;
import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Imp.Physics2D.ColliderType;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
import grimhaus.com.G2Dj.Type.Graphics.PointVisualizer;
import grimhaus.com.G2Dj.Type.Graphics.TextMesh;
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
    public static void main(String[] args){ Engine.init(new Game(){@Override public void init(){Main.init();}});}
    
    private static void init()
    {
        //Create a scene
        WeakReference<Scene> mainScene = Engine.createScene("Main");
        
        createVisibleGeometry(mainScene);
        createCollisionGeometry(mainScene);
        createTextMeshTest(mainScene);
        createSomeDynamicBoxes(mainScene);
        
        createPlayerCamera(mainScene);
        createMapCamera(mainScene);
        
    }
    
    //
    // GameObjects
    //
    private static void createSomeDynamicBoxes(final WeakReference<Scene> aScene)
    {
        //Create a dynamic box
        {
            WeakReference<GameObject> go = aScene.get().addGameObject();
            go.get().getTransform().get().setPosition(0,0,10);
            go.get().getTransform().get().setScale(3,3,3);
                
            go.get().addComponent(BoxCollider.class);
            go.get().addComponent(Rigidbody.class);
            go.get().addComponent(PointVisualizer.class);
                
        }
            
        //Create a dynamic box trigger
        {
            WeakReference<GameObject> go = aScene.get().addGameObject();
            go.get().getTransform().get().setPosition(-8,0,10);
            go.get().getTransform().get().setScale(3,3,3);
                
            BoxCollider bc = (BoxCollider)go.get().addComponent(BoxCollider.class);
            bc.setType(ColliderType.Trigger);
                
            go.get().addComponent(Rigidbody.class);
            go.get().addComponent(PointVisualizer.class);
                
        }
        
    }
    
    private static void createMapCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("TopCamera");
        gameObject.get().getTransform().get().setPosition(-1,5,2);
        gameObject.get().getTransform().get().setRotation(-90,0,0);
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        camera.setViewportPixelSize(new Vector2(0.5f,0.5f));
        camera.setClearMode(CameraClearMode.DepthOnly);
                
        gameObject.get().addComponent(TopCamera.class);

    }
    
    //Create the camera
    private static void createPlayerCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        //theCamera.get().getTransform().get().getRotation().y = 45f;
                
        Collider c = (Collider)gameObject.get().addComponent(BoxCollider.class);
        //c.setType(ColliderType.Collidable);
        //theCamera.get().addComponent(CircleCollider.class);
        gameObject.get().addComponent(Camera.class);
        CameraController cc = (CameraController)gameObject.get().addComponent(CameraController.class);
        gameObject.get().addComponent(PointVisualizer.class);
                
    }
    
    private static void createTextMeshTest(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("TextMesh");
        gameObject.get().getTransform().get().setRotation(0,180,0);
        gameObject.get().getTransform().get().setPosition(-5,0,-5);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        TextMesh mesh = (TextMesh)gameObject.get().addComponent(TextMesh.class);
        mesh.setText("123！お早う,What's good,שלום,Здравствуйте,안녕하세요");
        
    }
    
    private static void createTestCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("MainCamera");
        
        gameObject.get().getTransform().get().setPosition(0,10,0);
        gameObject.get().getTransform().get().setRotation(-90,180,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        
        camera.setProjectionMode(CameraProjectionMode.Orthographic);
        camera.setFarClippingPlane(15);
        camera.setOrthoSize(30, 30);
        
        camera.setClearColor(new Color(1,0,0,1));
        
    }
    
    private static void createCollisionGeometry(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> aGameObject = aScene.get().addGameObject();
        aGameObject.get().setName("Colliders");
        //aGameObject.get().getTransform().get().getRotation().y = 45f;
            
        //Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
        //aMesh.setTexture("_Texture", "awesome.png"); //give the mesh a texture
                
        //aGameObject.get().removeComponent(Mesh.class);
                
        PlayerController aPC = (PlayerController)aGameObject.get().addComponent(PlayerController.class);
        //aPC.getTransform().get().setRotation(0,0,45);
        aPC.getTransform().get().setPosition(0,0,1f);
                
        PolygonCollider pc = (PolygonCollider)aGameObject.get().addComponent(PolygonCollider.class);
        pc.setOffset(0, 0);
        pc.setVerticies(new Vector2[]
        {
            new Vector2(2,2),
            new Vector2(4,2),
            new Vector2(3,4),
            new Vector2(2,3),                    
                
        });
                
        CircleCollider cc = (CircleCollider)aGameObject.get().addComponent(CircleCollider.class);
        cc.setOffset(2, 0);
                
        Collider c = (Collider)aGameObject.get().addComponent(BoxCollider.class);
        c.setOffset(-2, 0);
        
        CompositeCollider compositeCollider = (CompositeCollider)aGameObject.get().addComponent(CompositeCollider.class);
        compositeCollider.setVerticies(new Vector2[][]
        { 
            new Vector2[]
            {
                new Vector2(0,0),
                new Vector2(1,0),
                new Vector2(1,2),
                new Vector2(0,1),
            },    
            new Vector2[]
            {
                new Vector2(1,1),
                new Vector2(2,1),
                new Vector2(2,3),
                new Vector2(1,2),
            },
            new Vector2[]
            {
                new Vector2(0,0),
                new Vector2(-1,0),
                new Vector2(-1,-2),
                new Vector2(0,-1),
            }, 
                
        });
                
        Rigidbody rb = (Rigidbody)aGameObject.get().addComponent(Rigidbody.class);
        rb.setType(BodyType.Static);
                
        aPC.getTransform().get().setScale(7,1,7);
                            
    }
    
    private static void createVisibleGeometry(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("/Adhoc/Cloud.png");
        Graphics.loadFromResource("/Adhoc/brick.png");
        Graphics.loadFromResource("Adhoc/grass.png");
        Graphics.loadFromResource("Adhoc/name.png");
        Graphics.loadFromResource("Adhoc/Sprites.png");
        
        //Create the BackWall
        {
            WeakReference<GameObject> aGameObject = aScene.get().addGameObject();
            aGameObject.get().setName("BackWall");
            aGameObject.get().getTransform().get().setPosition(0,-1f,0);
            aGameObject.get().getTransform().get().setScale   (10,4,0);
                
            Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
            aMesh.setTexture("_Texture", "brick.png"); //give the mesh a texture
                
        }
            
        //Create the Sprites
        {
            WeakReference<GameObject> aGameObject = aScene.get().addGameObject();
            aGameObject.get().setName("Sprites");
            aGameObject.get().getTransform().get().setPosition(0,0.0f,0.1f);
            aGameObject.get().getTransform().get().setScale   (10,1f,0);
                
            Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
            aMesh.setTexture("_Texture", "Sprites.png"); //give the mesh a texture
                
                
        }
            
        //Create the other Sprites
        {
            WeakReference<GameObject> aGameObject = aScene.get().addGameObject();
            aGameObject.get().setName("Sprites");
            aGameObject.get().getTransform().get().setPosition(2,0.0f,0.75f);
            aGameObject.get().getTransform().get().setScale   (10,1f,0);
                
            Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
            aMesh.setTexture("_Texture", "Sprites.png"); //give the mesh a texture
                
        }
            
        //Create the close wall
        {
            WeakReference<GameObject> aGameObject = aScene.get().addGameObject();
            aGameObject.get().setName("CloseWall");
            aGameObject.get().getTransform().get().setPosition(0,-0.5f,+2.4f);
            aGameObject.get().getTransform().get().setScale   (10,2,0);
                
            Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
            aMesh.setTexture("_Texture", "brick.png"); //give the mesh a texture
                
        }
            
        //Create the name
        {
            WeakReference<GameObject> aGameObject = aScene.get().addGameObject();
            aGameObject.get().setName("Name");
            aGameObject.get().getTransform().get().setPosition(0,1f,0.001f);
            aGameObject.get().getTransform().get().setScale   (10,10.5f,0);
                
            Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
            aMesh.setTexture("_Texture", "name.png"); //give the mesh a texture
                
        }
            
        //Create the floor
        {
            WeakReference<GameObject> aGameObject = aScene.get().addGameObject();
            aGameObject.get().setName("Floor");
            aGameObject.get().getTransform().get().setPosition(0,-0.5f,0);
            aGameObject.get().getTransform().get().setRotation(90,0,0);
            aGameObject.get().getTransform().get().setScale   (10,5,0);
            
            Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
            aMesh.setTexture("_Texture", "grass.png"); //give the mesh a texture
                
        }
            
        //Create the clouds
        {
            WeakReference<GameObject> aGameObject = aScene.get().addGameObject();
            aGameObject.get().setName("Floor");
            aGameObject.get().getTransform().get().setPosition(0,+0.5f,0);
            aGameObject.get().getTransform().get().setRotation(90,0,0);
            aGameObject.get().getTransform().get().setScale   (10,5,0);
                
            Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
            aMesh.setTexture("_Texture", "Cloud.png"); //give the mesh a texture
                
        }
        
    }
    
}
