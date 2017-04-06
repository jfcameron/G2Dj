/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Physics2D.BodyType;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.PointVisualizer;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.BoxCollider;
import grimhaus.com.G2Dj.Type.Physics2D.CircleCollider;
import grimhaus.com.G2Dj.Type.Physics2D.CompositeCollider;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class PongGame 
{
    public static void main(String[] args) 
    {
        Engine.init(new Game()
        {
            @Override
            public void init()
            {
                PongGame.init();
                
            }
        
        });
    }
    
    private static void init()
    {
        WeakReference<Scene> mainScene = Engine.createScene("Main");
        
        Graphics.loadFromResource("Adhoc/Sprites.png");
        
        createMainCamera(mainScene);
        createBoundaries(mainScene);
        createPlayer1Paddle(mainScene);
        //createPlayer2Paddle(mainScene);
        createBall(mainScene);
        
    }
    
    private static void createMainCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("MainCamera");
        
        gameObject.get().getTransform().get().setPosition(0,10,0);
        gameObject.get().getTransform().get().setRotation(-90,180,0);
        
        //gameObject.get().getTransform().get().setPosition(0,10,-5);
        //gameObject.get().getTransform().get().setRotation(-80,180,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        camera.setFarClippingPlane(50);
        
    }
    
    private static void createBoundaries(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("Boundaries");
        
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
            
            new Vector2[]
            {
                new Vector2(+extents.x,+extents.y          ),
                new Vector2(+extents.x,+extents.y-thickness),
                new Vector2(-extents.x,+extents.y-thickness),
                new Vector2(-extents.x,+extents.y),
            },
                
        });
        
        compositeCollider.setRestitution(0.0f);
        
        Rigidbody rb = (Rigidbody)gameObject.get().addComponent(Rigidbody.class);
        rb.setType(BodyType.Static);
        
    }
    
    private static WeakReference<GameObject> createPlayer1Paddle(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("Player1");
        
        gameObject.get().getTransform().get().setPosition(0,0,-15);
        gameObject.get().getTransform().get().setScale(6,1,1);
        
        gameObject.get().addComponent(BoxCollider.class);
        gameObject.get().addComponent(Rigidbody.class);
        gameObject.get().addComponent(PointVisualizer.class);
        gameObject.get().addComponent(PlayerPaddleController.class);
        
        return gameObject;
        
    }
    
    private static WeakReference<GameObject> createPlayer2Paddle(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("Player2");
        
        gameObject.get().getTransform().get().setPosition(0,0,+15);
        gameObject.get().getTransform().get().setScale(6,1,1);
        
        gameObject.get().addComponent(PointVisualizer.class);
        gameObject.get().addComponent(AIPaddleController.class);
        
        return gameObject;
        
    }
    
    private static void createBall(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("Ball");
        
        gameObject.get().getTransform().get().setPosition(0,0,0);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        gameObject.get().addComponent(Ball.class);
        
    }
    
}
