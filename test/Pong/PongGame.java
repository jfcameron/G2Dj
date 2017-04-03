/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.PointVisualizer;
import grimhaus.com.G2Dj.Type.Physics2D.BoxCollider;
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
        createPaddle(mainScene);
        
    }
    
    private static void createMainCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("TopCamera");
        
        gameObject.get().getTransform().get().setPosition(0,10,0);
        gameObject.get().getTransform().get().setRotation(-90,0,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        
    }
    
    private static WeakReference<GameObject> createPaddle(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().getTransform().get().setPosition(0,0,0);
        gameObject.get().getTransform().get().setScale(3,3,3);
        
        gameObject.get().addComponent(BoxCollider.class);
        gameObject.get().addComponent(Rigidbody.class);
        gameObject.get().addComponent(PointVisualizer.class);
        
        return gameObject;
        
    }
    
}
