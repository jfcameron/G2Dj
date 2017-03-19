package grimhaus.com.G2Dj.Android;

//.if ANDROID
//|
//|import java.lang.ref.WeakReference;
//|
//|import grimhaus.com.G2Dj.Debug;
//|import grimhaus.com.G2Dj.Engine;
//|import grimhaus.com.G2Dj.Imp.Graphics.Color;
//|import grimhaus.com.G2Dj.Type.Engine.GameObject;
//|import grimhaus.com.G2Dj.Type.Engine.Scene;
//|import grimhaus.com.G2Dj.Type.Graphics.Camera;
//|import grimhaus.com.G2Dj.Type.Graphics.Mesh;
//|import grimhaus.com.G2Dj.Type.Math.Vector2;
//|
//|/**
//| * Created by Joe on 3/18/2017.
//| */
//|
//|public class AndroidTest
//|{
//|    public static void doTest()
//|    {
//|        Debug.log("The Android test is beginning");
//|
//|        WeakReference<Scene> mainScene = Engine.createScene("main");
//|
//|        //Player
//|        {
//|            WeakReference<GameObject> gameObject = mainScene.get().addGameObject();
//|            gameObject.get().setName("Player");
//|            gameObject.get().getTransform().get().setPosition(0,0,2);
//|
//|            Mesh aMesh = (Mesh)gameObject.get().addComponent(Mesh.class); //add a mesh
//|            //aMesh.setTexture("_Texture", "awesome.png"); //give the mesh a texture
//|
//|            Debug.log("******************************\n"+aMesh);
//|
//|        }
//|
//|        /*//Create the BackWall
//|        {
//|            WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
//|            aGameObject.get().setName("BackWall");
//|            aGameObject.get().getTransform().get().setPosition(0,-1f,0);
//|            aGameObject.get().getTransform().get().setScale   (10,4,0);
//|
//|            Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
//|            aMesh.setTexture("_Texture", "default.png"); //give the mesh a texture
//|            //aMesh.setShader("123123123123");
//|
//|        }*/
//|
//|        //camera1
//|        {
//|            WeakReference<GameObject> gameObject = mainScene.get().addGameObject();
//|            gameObject.get().setName("Player Camera");
//|            Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
//|            camera.setViewportPixelSize(new Vector2(0.5f,0.5f));
//|            camera.setClearColor(Color.DeathlyPink());
//|
//|        }
//|
//|        //camera2
//|        {
//|            WeakReference<GameObject> gameObject = mainScene.get().addGameObject();
//|            gameObject.get().setName("Player Camera");
//|            Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
//|            camera.setViewportPixelSize(new Vector2(0.5f,0.5f));
//|            camera.setViewportPixelPosition(new Vector2(0.0f,0.5f));
//|            camera.setClearColor(Color.CornflowerBlue());
//|
//|        }
//|
//|        //camera3
//|        {
//|            WeakReference<GameObject> gameObject = mainScene.get().addGameObject();
//|            gameObject.get().setName("Player Camera");
//|            Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
//|            camera.setViewportPixelSize(new Vector2(0.5f,0.5f));
//|            camera.setViewportPixelPosition(new Vector2(0.5f,0.0f));
//|            camera.setClearColor(Color.Red());
//|
//|        }
//|
//|        //camera4
//|        {
//|            WeakReference<GameObject> gameObject = mainScene.get().addGameObject();
//|            gameObject.get().setName("Player Camera");
//|            Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
//|            camera.setViewportPixelSize(new Vector2(0.5f,0.5f));
//|            camera.setViewportPixelPosition(new Vector2(0.5f,0.5f));
//|            camera.setClearColor(Color.Green());
//|
//|        }
//|
//|    }
//|
//|}
//|
//.endif
