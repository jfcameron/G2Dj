/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.CameraClearMode;
import grimhaus.com.G2Dj.Imp.Graphics.CameraProjectionMode;
import grimhaus.com.G2Dj.Imp.Graphics.Color;
import grimhaus.com.G2Dj.Imp.Physics2D.BodyType;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
import grimhaus.com.G2Dj.Type.Graphics.TextMesh;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector4;
import grimhaus.com.G2Dj.Type.Physics2D.CompositeCollider;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class PongGame 
{
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){PongGame.init();}});}
    
    private static void init()
    {        
        createBackgroundScene();
        createGameScene();
        createGUIScene();
    
    }
    
    //
    // Scenes
    //
    private static void createBackgroundScene()
    {
        WeakReference<Scene> scene = Engine.createScene(Constants.BackgroundSceneName);
        
        createBackgroundCamera(scene);
        createBackgroundQuad(scene);
        createGameBackgroundPicture(scene);
        
        
    }
    
    private static void createGameScene()
    {
        Graphics.loadFromResource("Pong/Paddle.png");
        
        WeakReference<Scene> scene = Engine.createScene(Constants.MainSceneName);
        
        createMainCamera(scene);
        createBoundaries(scene);
        createPlayer1Paddle(scene);
        createPlayer2Paddle(scene);
        createBall(scene);
        
    }
    
    private static void createGUIScene()
    {        
        WeakReference<Scene> scene = Engine.createScene(Constants.GUISceneName);
        
        createGUIControllerObject(scene);
        createGUICamera(scene);        
        createScoreCounterLabel(scene);
        createMatchTimerLabel(scene);
        
        createScoreCounter(scene);
        createMatchTimer(scene);
        
        GUIBlackbars(scene);
        
        createAttributionLabels(scene);
        
    }
    
    //
    // GameObjects
    //
    private static void GUIBlackbars(final WeakReference<Scene> aScene)
    {
        Vector4 barColor = new Vector4(0,0,0,1);
        
        //Top
        {
            WeakReference<GameObject> gameObject = aScene.get().addGameObject();
            gameObject.get().getTransform().get().setPosition(0,-1,9.5f);
            gameObject.get().getTransform().get().setRotation(-90,180,0);
            gameObject.get().getTransform().get().setScale(40,1.5f,0);
            
            Mesh mesh = (Mesh)gameObject.get().addComponent(Mesh.class);
            mesh.setShader("SimpleColor");
            mesh.setVector4("_Color", barColor);
        
        }
        
        //Bottom
        {
            WeakReference<GameObject> gameObject = aScene.get().addGameObject();
            gameObject.get().getTransform().get().setPosition(0,-1,-9.5f);
            gameObject.get().getTransform().get().setRotation(-90,180,0);
            gameObject.get().getTransform().get().setScale(40,1.5f,0);
            
            Mesh mesh = (Mesh)gameObject.get().addComponent(Mesh.class);
            mesh.setShader("SimpleColor");
            mesh.setVector4("_Color", barColor);
            
        }
        
    }
    
    private static void createGUIControllerObject(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName(Constants.GUIControllerName);
        
        gameObject.get().addComponent(GUIController.class);
        
    }
    
    private static void createGUICamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName(Constants.MainCameraName);
        
        gameObject.get().getTransform().get().setPosition(0,10,0);
        gameObject.get().getTransform().get().setRotation(-90,180,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        
        camera.setProjectionMode(CameraProjectionMode.Orthographic);
        camera.setFarClippingPlane(15);
        camera.setOrthoSize(20, 20);
        camera.setClearMode(CameraClearMode.DepthOnly);
        
        camera.setClearColor(new Color(0,0,0,1));
        
    }
    
    private static void createBackgroundCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName(Constants.MainCameraName);
        
        gameObject.get().getTransform().get().setPosition(0,10,0);
        gameObject.get().getTransform().get().setRotation(-90,180,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        
        camera.setProjectionMode(CameraProjectionMode.Orthographic);
        camera.setFarClippingPlane(15);
        camera.setOrthoSize(20, 20);
        
        camera.setClearColor(new Color(0,0,0,1));
        
    }
    
    private static void createGameBackgroundPicture(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("Pong/BackgroundHills.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().getTransform().get().setRotation(-90,0,180);
        gameObject.get().getTransform().get().setPosition(0,-1,-0.1f);
        gameObject.get().getTransform().get().setScale(20,20,0);
        
        Mesh mesh = (Mesh)gameObject.get().addComponent(Mesh.class);
        mesh.setTexture("_Texture", "BackgroundHills.png");
        
    }
    
    private static void createBackgroundQuad(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("Pong/Background.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().getTransform().get().setRotation(-90,180,0);
        gameObject.get().getTransform().get().setPosition(0,-2,-0.1f);
        gameObject.get().getTransform().get().setScale(40,40,0);
        
        Mesh mesh = (Mesh)gameObject.get().addComponent(Mesh.class);
        mesh.setTexture("_Texture", "Background.png");
        mesh.setVector2("_UVScale", new Vector2(5E1f,5E1f));
        
        gameObject.get().addComponent(BackgroundQuad.class);
        
    }
    
    private static void createMainCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName(Constants.MainCameraName);
        gameObject.get().getTransform().get().setPosition(0,10,0);
        gameObject.get().getTransform().get().setRotation(-90,0,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        camera.setProjectionMode(CameraProjectionMode.Orthographic);
        camera.setFarClippingPlane(15);
        camera.setOrthoSize(40, 40);
        camera.setClearMode(CameraClearMode.DepthOnly);
        
    }
    
    private static void createBoundaries(final WeakReference<Scene> aScene)
    {
        {
            WeakReference<GameObject> gameObject = aScene.get().addGameObject();
            gameObject.get().setName(Constants.BoundariesName);
            
            Vector2 extents = new Vector2(20,17);
            float thickness = 2.0f;
            
            CompositeCollider compositeCollider = (CompositeCollider)gameObject.get().addComponent(CompositeCollider.class);
            compositeCollider.setVerticies(new Vector2[][]
            { 
                new Vector2[]
                {
                    new Vector2(-extents.x          ,-extents.y),
                    new Vector2(-extents.x+thickness,-extents.y),
                    new Vector2(-extents.x+thickness,+extents.y),
                    new Vector2(-extents.x          ,+extents.y),
                }, 
                
                new Vector2[]
                {
                    new Vector2(+extents.x          ,+extents.y),
                    new Vector2(+extents.x-thickness,+extents.y),
                    new Vector2(+extents.x-thickness,-extents.y),
                    new Vector2(+extents.x          ,-extents.y),
                },
                
                /*new Vector2[]
                {
                    new Vector2(+extents.x,+extents.y          ),
                    new Vector2(+extents.x,+extents.y-thickness),
                    new Vector2(-extents.x,+extents.y-thickness),
                    new Vector2(-extents.x,+extents.y),
                },*/
                
            });
            compositeCollider.setRestitution(1.0f);
            Debug.log("SETTING DEBUGLINES");
            compositeCollider.setDrawDebugLines(true);
            
            Rigidbody rb = (Rigidbody)gameObject.get().addComponent(Rigidbody.class);
            rb.setType(BodyType.Static);
        
        }
        
        Graphics.loadFromResource("Pong/Wall.png");
                 
        //Wall graphic1
        {
            
            
            WeakReference<GameObject> gameObject = aScene.get().addGameObject();
            gameObject.get().getTransform().get().setRotation(-90,180,0);
            gameObject.get().getTransform().get().setScale(37,38f,1);
            gameObject.get().getTransform().get().setPosition(-20.5f,-1f,0);
        
            Mesh mesh = (Mesh)gameObject.get().addComponent(Mesh.class);
            mesh.setTexture("_Texture","Wall.png");
            
        }
        
        //Wall graphic2
        {
            
            
            WeakReference<GameObject> gameObject = aScene.get().addGameObject();
            gameObject.get().getTransform().get().setRotation(-90,180,0);
            gameObject.get().getTransform().get().setScale(37,38f,1);
            gameObject.get().getTransform().get().setPosition(+21f,-1f,0);
        
            Mesh mesh = (Mesh)gameObject.get().addComponent(Mesh.class);
            mesh.setTexture("_Texture","Wall.png");
            
        }
        
    }
    
    private static WeakReference<GameObject> createPlayer1Paddle(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName(Constants.Player1Name);
        gameObject.get().getTransform().get().setPosition(0,0,+15);
        gameObject.get().getTransform().get().setScale(6,1,1);
        
        gameObject.get().addComponent(PlayerPaddleController.class);
        
        return gameObject;
        
    }
    
    private static WeakReference<GameObject> createPlayer2Paddle(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName(Constants.Player2Name);
        gameObject.get().getTransform().get().setPosition(0,0,-15);
        gameObject.get().getTransform().get().setScale(6,1,1);
        
        gameObject.get().addComponent(AIPaddleController.class);
        
        return gameObject;
        
    }
    
    private static void createBall(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("Pong/BallGuy.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName(Constants.BallName);
        gameObject.get().getTransform().get().setScale(1.5f,1.5f,1.5f);
        
        gameObject.get().addComponent(Ball.class);
        
    }
    
    private static void createAttributionLabels(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName(Constants.MatchTimerLabelName);
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(-5.5f,0,+9.5f);
        gameObject.get().getTransform().get().setScale(0.4f);
        
        TextMesh mesh = (TextMesh)gameObject.get().addComponent(TextMesh.class);
        
        String creditString ="";
        
        for(int i=0,s=Constants.Credits.length;i<s;i++)
            creditString += Constants.Credits[i];
        
        mesh.setText("Credits:"+creditString);
        
    }
    
    private static void createScoreCounterLabel(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName(Constants.ScoreCounterLabelName);
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(+9.5f,0,-9.5f);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        TextMesh mesh = (TextMesh)gameObject.get().addComponent(TextMesh.class);
        mesh.setText("Score: ");
        
    }
    
    private static void createMatchTimerLabel(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName(Constants.MatchTimerLabelName);
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(+9.5f,0,+9.5f);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        TextMesh mesh = (TextMesh)gameObject.get().addComponent(TextMesh.class);
        mesh.setText("Time: ");
        
    }
    
    private static void createScoreCounter(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName(Constants.ScoreCounterName);
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(+9.5f-6f,1,-9.5f);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        gameObject.get().addComponent(ScoreCounter.class);
        
    }
    
    private static void createMatchTimer(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName(Constants.MatchTimerName);
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(+9.5f-5f,1,+9.5f);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        gameObject.get().addComponent(MatchTimer.class);
        
    }
    
}
